package net.gvsun.job.config;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.gvsun.job.GvsunJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@Component
public class JobInfoConfig {
    @Value("${xxl.job.packageName}")
    private String packageName;
    @Autowired
    private ApplicationContext applicationContext;

    @XxlJob("datashare")
    @SneakyThrows
    public void datashareJob() {
        //获取xxljob参数
        String param = XxlJobHelper.getJobParam();
        //分两层参数，第一层为冒号隔开，冒号之前为执行的方法，冒号之后为方法的参数
        String[] params = param.split(":");
        //执行的方法名
        String methodIndex = params[0];
        //第二层用逗号隔开方法的参数
        String methodArgs = "";
        if (params.length > 1){
            methodArgs = params[1];
        }
        //扫描包
        String path = "classpath*:"+packageName+"/*.class";
        extracted(methodIndex, methodArgs, path);
    }

    @XxlJob("usercenter")
    @SneakyThrows
    public void usercenterJob() {
        //获取xxljob参数
        String param = XxlJobHelper.getJobParam();
        //分两层参数，第一层为冒号隔开，冒号之前为执行的方法，冒号之后为方法的参数
        String[] params = param.split(":");
        //执行的方法名
        String methodIndex = params[0];
        //第二层用逗号隔开方法的参数
        String methodArgs = "";
        if (params.length > 1){
            methodArgs = params[1];
        }
        //扫描包
        String path = "classpath*:"+packageName+"/*.class";
        extracted(methodIndex, methodArgs, path);
    }

    @XxlJob("instruments")
    @SneakyThrows
    public void instrumentsJob() {
        //获取xxljob参数
        String param = XxlJobHelper.getJobParam();
        //分两层参数，第一层为冒号隔开，冒号之前为执行的方法，冒号之后为方法的参数
        String[] params = param.split(":");
        //执行的方法名
        String methodIndex = params[0];
        //第二层用逗号隔开方法的参数
        String methodArgs = "";
        if (params.length > 1){
            methodArgs = params[1];
        }
        //扫描包
        String path = "classpath*:"+packageName+"/*.class";
        extracted(methodIndex, methodArgs, path);
    }

    @XxlJob("limsproduct")
    @SneakyThrows
    public void limsproductJob() {
        //获取xxljob参数
        String param = XxlJobHelper.getJobParam();
        //分两层参数，第一层为冒号隔开，冒号之前为执行的方法，冒号之后为方法的参数
        String[] params = param.split(":");
        //执行的方法名
        String methodIndex = params[0];
        //第二层用逗号隔开方法的参数
        String methodArgs = "";
        if (params.length > 1){
            methodArgs = params[1];
        }
        //扫描包
        String path = "classpath*:"+packageName+"/*.class";
        extracted(methodIndex, methodArgs, path);
    }

    @XxlJob("proteach")
    @SneakyThrows
    public void proteachJob() {
        //获取xxljob参数
        String param = XxlJobHelper.getJobParam();
        //分两层参数，第一层为冒号隔开，冒号之前为执行的方法，冒号之后为方法的参数
        String[] params = param.split(":");
        //执行的方法名
        String methodIndex = params[0];
        //第二层用逗号隔开方法的参数
        String methodArgs = "";
        if (params.length > 1){
            methodArgs = params[1];
        }
        //扫描包
        String path = "classpath*:"+packageName+"/*.class";
        extracted(methodIndex, methodArgs, path);
    }

    @XxlJob("xmgl")
    @SneakyThrows
    public void xmglJob() {
        //获取xxljob参数
        String param = XxlJobHelper.getJobParam();
        //分两层参数，第一层为冒号隔开，冒号之前为执行的方法，冒号之后为方法的参数
        String[] params = param.split(":");
        //执行的方法名
        String methodIndex = params[0];
        //第二层用逗号隔开方法的参数
        String methodArgs = "";
        if (params.length > 1){
            methodArgs = params[1];
        }
        //扫描包
        String path = "classpath*:"+packageName+"/*.class";
        extracted(methodIndex, methodArgs, path);
    }

    @XxlJob("financial")
    @SneakyThrows
    public void financialJob() {
        //获取xxljob参数
        String param = XxlJobHelper.getJobParam();
        //分两层参数，第一层为冒号隔开，冒号之前为执行的方法，冒号之后为方法的参数
        String[] params = param.split(":");
        //执行的方法名
        String methodIndex = params[0];
        //第二层用逗号隔开方法的参数
        String methodArgs = "";
        if (params.length > 1){
            methodArgs = params[1];
        }
        //扫描包
        String path = "classpath*:"+packageName+"/*.class";
        extracted(methodIndex, methodArgs, path);
    }

    @XxlJob("practicetimetable")
    @SneakyThrows
    public void practicetimetableJob() {
        //获取xxljob参数
        String param = XxlJobHelper.getJobParam();
        //分两层参数，第一层为冒号隔开，冒号之前为执行的方法，冒号之后为方法的参数
        String[] params = param.split(":");
        //执行的方法名
        String methodIndex = params[0];
        //第二层用逗号隔开方法的参数
        String methodArgs = "";
        if (params.length > 1){
            methodArgs = params[1];
        }
        //扫描包
        String path = "classpath*:"+packageName+"/*.class";
        extracted(methodIndex, methodArgs, path);
    }

    @XxlJob("appointment")
    @SneakyThrows
    public void appointmentJob() {
        //获取xxljob参数
        String param = XxlJobHelper.getJobParam();
        //分两层参数，第一层为冒号隔开，冒号之前为执行的方法，冒号之后为方法的参数
        String[] params = param.split(":");
        //执行的方法名
        String methodIndex = params[0];
        //第二层用逗号隔开方法的参数
        String methodArgs = "";
        if (params.length > 1){
            methodArgs = params[1];
        }
        //扫描包
        String path = "classpath*:"+packageName+"/*.class";
        extracted(methodIndex, methodArgs, path);
    }

    @XxlJob("iot")
    @SneakyThrows
    public void iotJob() {
        //获取xxljob参数
        String param = XxlJobHelper.getJobParam();
        //分两层参数，第一层为冒号隔开，冒号之前为执行的方法，冒号之后为方法的参数
        String[] params = param.split(":");
        //执行的方法名
        String methodIndex = params[0];
        //第二层用逗号隔开方法的参数
        String methodArgs = "";
        if (params.length > 1){
            methodArgs = params[1];
        }
        //扫描包
        String path = "classpath*:"+packageName+"/*.class";
        extracted(methodIndex, methodArgs, path);
    }

    @XxlJob("attendance")
    @SneakyThrows
    public void attendanceJob() {
        //获取xxljob参数
        String param = XxlJobHelper.getJobParam();
        //分两层参数，第一层为冒号隔开，冒号之前为执行的方法，冒号之后为方法的参数
        String[] params = param.split(":");
        //执行的方法名
        String methodIndex = params[0];
        //第二层用逗号隔开方法的参数
        String methodArgs = "";
        if (params.length > 1){
            methodArgs = params[1];
        }
        //扫描包
        String path = "classpath*:"+packageName+"/*.class";
        extracted(methodIndex, methodArgs, path);
    }

    @XxlJob("configcenter")
    @SneakyThrows
    public void configcenterJob() {
        //获取xxljob参数
        String param = XxlJobHelper.getJobParam();
        //分两层参数，第一层为冒号隔开，冒号之前为执行的方法，冒号之后为方法的参数
        String[] params = param.split(":");
        //执行的方法名
        String methodIndex = params[0];
        //第二层用逗号隔开方法的参数
        String methodArgs = "";
        if (params.length > 1){
            methodArgs = params[1];
        }
        //扫描包
        String path = "classpath*:"+packageName+"/*.class";
        extracted(methodIndex, methodArgs, path);
    }

    @XxlJob("message")
    @SneakyThrows
    public void messageJob() {
        //获取xxljob参数
        String param = XxlJobHelper.getJobParam();
        //分两层参数，第一层为冒号隔开，冒号之前为执行的方法，冒号之后为方法的参数
        String[] params = param.split(":");
        //执行的方法名
        String methodIndex = params[0];
        //第二层用逗号隔开方法的参数
        String methodArgs = "";
        if (params.length > 1){
            methodArgs = params[1];
        }
        //扫描包
        String path = "classpath*:"+packageName+"/*.class";
        extracted(methodIndex, methodArgs, path);
    }

    @XxlJob("timetable")
    @SneakyThrows
    public void timetableJob() {
        //获取xxljob参数
        String param = XxlJobHelper.getJobParam();
        //分两层参数，第一层为冒号隔开，冒号之前为执行的方法，冒号之后为方法的参数
        String[] params = param.split(":");
        //执行的方法名
        String methodIndex = params[0];
        //第二层用逗号隔开方法的参数
        String methodArgs = "";
        if (params.length > 1){
            methodArgs = params[1];
        }
        //扫描包
        String path = "classpath*:"+packageName+"/*.class";
        extracted(methodIndex, methodArgs, path);
    }



    /**
     * 扫描包
     * @param methodIndex 方法名
     * @param methodArgs 方法参数
     * @param path 包路径
     */
    private void extracted(String methodIndex, String methodArgs, String path) throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        CachingMetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory();
        Resource[] resources = pathMatchingResourcePatternResolver.getResources(path);

        //循环调出标记位
        boolean loopOut = false;
        //遍历扫描到的类
        for (Resource resource : resources){
            //获取包里的类
            MetadataReader reader = cachingMetadataReaderFactory.getMetadataReader(resource);
            String className = reader.getClassMetadata().getClassName();
            Class<?> eClass = Class.forName(className);
            Object bean = applicationContext.getBean(eClass);
            //获取方法
            Method[] methods = eClass.getDeclaredMethods();
            for (Method method : methods){
                //判断是否有使用注解
                if (method.isAnnotationPresent(GvsunJob.class)){
                    GvsunJob childAnnotation = method.getAnnotation(GvsunJob.class);
                    //如果与注解的值一致，则执行当前方法
                    if (methodIndex.equals(childAnnotation.value())){
                        //执行方法
                        method.invoke(bean, methodArgs);
                        //外层循环标记为置为true
                        loopOut = true;
                        break;
                    }
                }
            }
            if (loopOut){
                break;
            }
        }
    }
}
