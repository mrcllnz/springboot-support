package io.cloudstate.springboot.starter.autoconfigure;

import io.cloudstate.springboot.starter.internal.CloudstateBeanPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.cloudstate.javasupport.CloudState;
import io.cloudstate.springboot.starter.internal.scan.CloudstateEntityScan;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@ConditionalOnClass(CloudstateProperties.class)
@EnableConfigurationProperties(CloudstateProperties.class)
@ComponentScan(basePackages = "io.cloudstate.springboot.starter")
public class CloudstateAutoConfiguration {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private CloudstateProperties cloudstateProperties;

    @Bean
    @ConditionalOnMissingBean
    public CloudstateEntityScan cloudstateEntityScan() {
        return new CloudstateEntityScan(context, cloudstateProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public CloudState cloudState(CloudstateEntityScan entityScan) throws Exception {
        return new CloudState();
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadLocal<Map<Class<?>, Map<String, Object>>> stateController(){
        return ThreadLocal.withInitial(ConcurrentHashMap::new);
    }

//    @Bean
//    public static CloudstateBeanPostProcessor cloudstateBeanPostProcessor(
//            CloudstateEntityScan cloudstateEntityScan, ThreadLocal<Map<Class<?>, Map<String, Object>>> stateController){
//        return new CloudstateBeanPostProcessor(cloudstateEntityScan, stateController);
//    }

}