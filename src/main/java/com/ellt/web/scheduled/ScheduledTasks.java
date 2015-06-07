package com.ellt.web.scheduled;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by irving on 2014/7/23.
 * http://spring.io/guides/gs/scheduling-tasks/
 */
@Component
@EnableScheduling
public class ScheduledTasks {
    private static Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        logger.info("ScheduledTask Time: " + DateTime.now().toString());
    }
}