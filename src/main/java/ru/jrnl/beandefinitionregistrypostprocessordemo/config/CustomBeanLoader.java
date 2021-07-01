package ru.jrnl.beandefinitionregistrypostprocessordemo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.comparator.Comparators;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import ru.jrnl.beandefinitionregistrypostprocessordemo.service.raw.CustomComponent;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomBeanLoader implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        getCustomComponentConfigs().forEach(config -> {
            beanDefinitionRegistry.registerBeanDefinition(config.getBeanName(), buildCustomComponentBeanDefinition(config));
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

    private AbstractBeanDefinition buildCustomComponentBeanDefinition(CustomComponentConfig config) {
//        java.lang.reflect.Constructor<?> constructor = Stream.of(CustomComponent.class.getConstructors())
//                .max(Comparator.comparingInt(java.lang.reflect.Constructor::getParameterCount))
//                .get();
//
//        Stream.of(constructor.getParameters()).forEach(parameter -> {
//            parameter.getName()
//        });

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(CustomComponent.class)
                .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                .addConstructorArgReference("businessLogicService")
                .addConstructorArgValue(config.getText())
                .addConstructorArgValue(config.getNumber())
                .getBeanDefinition();

        return beanDefinition;
    }

//    @Data
//    @AllArgsConstructor
    @Getter
    private static class CustomComponentConfig {
        public String beanName;
        public String text;
        public Integer number;
    }

    private List<CustomComponentConfig> getCustomComponentConfigs() {
        Constructor constructor = new Constructor(CustomComponentConfig.class);
        Yaml yaml = new Yaml(constructor);
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("custom-components.yml");

        ArrayList<CustomComponentConfig> configs = new ArrayList<>();
        for (Object conf : yaml.loadAll(inputStream)) {
            configs.add((CustomComponentConfig) conf);
        }

        return configs;
//        return List.of(
//                new CustomComponentConfig("firstBean", "First bean", 11),
//                new CustomComponentConfig("secondBean", "Second bean", 23),
//                new CustomComponentConfig("thirdBean", "Bacon", 1101)
//        );
    }
}
