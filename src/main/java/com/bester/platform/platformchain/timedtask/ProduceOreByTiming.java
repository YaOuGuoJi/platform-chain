package com.bester.platform.platformchain.timedtask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zhangqiang
 */
@Component
public class ProduceOreByTiming {

    @Scheduled(cron = "0 30 * * * ?")
    public void produceOre() {

    }

}
