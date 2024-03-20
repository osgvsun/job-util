package net.gvsun.job.config;

import com.alibaba.fastjson.JSONArray;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.gvsun.job.GvsunJob;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 初始化任务信息（自动注册任务）
 */
@Component
@Slf4j
public class InitJobInfoConfig {
    private static String jobInfoKey = "xxljob:jobInfo";
    private static String groupInfoKey = "xxljob:groupInfo";

    private final RestTemplate restTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private String loginUser;               //登录账号
    private String password;                //登录密码
    private String author;                  //负责人
    private String email;                   //负责人邮箱
    private String packageName;             //扫描包路径
    private String address;                 //xxl-job地址
    private String projectName;             //微服务名称
    private boolean enable;                 //是否开启xxl-job

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public InitJobInfoConfig(RestTemplate restTemplate, StringRedisTemplate stringRedisTemplate
                             ) {
        this.restTemplate = restTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public InitJobInfoConfig initJob() {

        //未启用xxl-job功能则直接返回
        if (!enable){
            return null;
        }
        try {
            //从redis中获取执行器id
            Object groupInfo = stringRedisTemplate.opsForHash().get(groupInfoKey,projectName);
            if (Objects.isNull(groupInfo)){
                log.error("当前服务在xxl-job未创建执行器，无法同步任务数据");
                return null;
            }
            //执行器ID
            Integer groupId = Integer.valueOf(groupInfo.toString());

            //从redis中获取定时任务留存数据
            Object jobTitle = stringRedisTemplate.opsForHash().get(jobInfoKey,projectName);
            List<String> jobList = new ArrayList<>();
            if (Objects.nonNull(jobTitle)){
                jobList = JSONArray.parseArray(jobTitle.toString(),String.class);
            }
            //扫描包
            String path = "classpath*:"+packageName+"/*.class";
            PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
            CachingMetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory();
            Resource[] resources = pathMatchingResourcePatternResolver.getResources(path);

            //获取登录后的cookie
            MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
            params.set("userName",loginUser);
            params.set("password",password);
            ResponseEntity<String> entity = restTemplate.postForEntity(address + "/login", params, String.class);
            List<String> cookies = entity.getHeaders().get("Set-Cookie");
            //遍历扫描到的类
            for (Resource resource : resources){
                //获取包里的类
                MetadataReader reader = cachingMetadataReaderFactory.getMetadataReader(resource);
                String className = reader.getClassMetadata().getClassName();
                Class<?> eClass = Class.forName(className);
                //获取方法
                Method[] methods = eClass.getDeclaredMethods();
                for (Method method : methods){
                    //判断是否有使用注解
                    if (method.isAnnotationPresent(GvsunJob.class)){
                        GvsunJob childAnnotation = method.getAnnotation(GvsunJob.class);

                        if (!jobList.contains(childAnnotation.value())){
                            if (childAnnotation.value().contains(":")){
                                log.error("任务:"+childAnnotation+"注册失败，原因：注解value不允许包含冒号");
                                continue;
                            }
                            if (childAnnotation.value().equals("")){
                                continue;
                            }

                            //执行自动生成定时任务数据插入方法，并更新redis
                            //参数
                            MultiValueMap<String,Object> requestMap = new LinkedMultiValueMap<>();
                            requestMap.set("id",0);
                            requestMap.set("jobGroup",groupId);//执行器id 变 可读但是id
                            requestMap.set("jobDesc",childAnnotation.desc());//变
                            requestMap.set("author",author);//变  可固定
                            requestMap.set("alarmEmail",email);//变？ 可固定
                            requestMap.set("scheduleType","CRON");
                            requestMap.set("scheduleConf",childAnnotation.cron());//变
                            requestMap.set("misfireStrategy","DO_NOTHING");
                            requestMap.set("executorRouteStrategy","FIRST");
                            requestMap.set("executorHandler",projectName);//变  可读
                            //如果有参数
                            if (!"".equals(childAnnotation.param())){
                                requestMap.set("executorParam",childAnnotation.value() + ":" + childAnnotation.param());
                            }else {
                                requestMap.set("executorParam",childAnnotation.value());
                            }
                            requestMap.set("executorBlockStrategy","SERIAL_EXECUTION");
                            requestMap.set("executorTimeout",0);
                            requestMap.set("executorFailRetryCount",0);
                            requestMap.set("glueType","BEAN");
                            requestMap.set("glueSource","");
                            requestMap.set("glueRemark","GLUE代码初始化");
                            requestMap.set("childJobId","");
                            requestMap.set("triggerStatus",0);
                            requestMap.set("triggerLastTime",0);
                            requestMap.set("triggerNextTime",0);

                            //请求头
                            HttpHeaders headers = new HttpHeaders();
                            headers.put(HttpHeaders.COOKIE,cookies);
                            headers.set("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
                            HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<MultiValueMap<String,Object>>(requestMap,headers);
                            ResponseEntity<String> responseEntity = restTemplate.exchange(address + "/jobinfo/add", HttpMethod.POST,httpEntity,String.class);

                            //集合添加数据
                            jobList.add(childAnnotation.value());
                        }
                    }
                }
            }
            //最后更新redis
            if (jobList.size() > 0){
                stringRedisTemplate.opsForHash().put(jobInfoKey,projectName,JSONArray.toJSONString(jobList));
            }
        }catch (Exception e){
            log.error("自动注册定时任务出错");
            e.printStackTrace();
        }
        return this;
    }
}
