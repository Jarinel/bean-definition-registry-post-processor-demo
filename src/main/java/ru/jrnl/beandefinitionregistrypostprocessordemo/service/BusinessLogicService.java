package ru.jrnl.beandefinitionregistrypostprocessordemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessLogicService {
//    private final ApplicationContext context;

    public void doBusiness(String data) {
        log.info("Performing some data: {}", data);
    }
}
