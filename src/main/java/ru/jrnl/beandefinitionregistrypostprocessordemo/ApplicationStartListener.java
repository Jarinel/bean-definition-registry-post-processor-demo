package ru.jrnl.beandefinitionregistrypostprocessordemo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;
import ru.jrnl.beandefinitionregistrypostprocessordemo.service.raw.CustomComponent;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationStartListener implements ApplicationListener<ApplicationReadyEvent> {
    private final List<CustomComponent> customComponents;

    @PostConstruct
    public void init() {
        System.out.println("inited");
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        customComponents.forEach(CustomComponent::invoke);
    }
}
