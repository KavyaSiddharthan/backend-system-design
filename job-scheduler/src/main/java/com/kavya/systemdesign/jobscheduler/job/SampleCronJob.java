package com.kavya.systemdesign.jobscheduler.job;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Slf4j
public class SampleCronJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("Executing Sample Cron Job at: {}", LocalDateTime.now());
        try {
            // Simulate heavy background work like generating reports
            Thread.sleep(3000);
            log.info("Sample Cron Job finished successfully.");
        } catch (InterruptedException e) {
            log.error("Job interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
}
