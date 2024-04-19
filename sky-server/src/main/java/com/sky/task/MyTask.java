package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 *定时任务类
 */
@Slf4j
@Component
public class MyTask {
//
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void execute() {
//        log.info("定时任务执行,{}",new Date());
//    }
}
