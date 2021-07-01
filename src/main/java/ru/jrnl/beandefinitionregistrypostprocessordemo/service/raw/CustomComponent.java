package ru.jrnl.beandefinitionregistrypostprocessordemo.service.raw;

import lombok.RequiredArgsConstructor;
import ru.jrnl.beandefinitionregistrypostprocessordemo.service.BusinessLogicService;

@RequiredArgsConstructor
public class CustomComponent {
    private final BusinessLogicService service;
    private final String text;
    private final Integer number;

    public void invoke() {
        service.doBusiness(text + " " + number);
    }
}
