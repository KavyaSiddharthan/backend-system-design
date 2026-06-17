package com.kavya.systemdesign.jobscheduler.config;
import com.kavya.systemdesign.jobscheduler.job.SampleCronJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    
    @Bean
    public JobDetail sampleJobDetail() {
        return JobBuilder.newJob(SampleCronJob.class)
                .withIdentity("sampleCronJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger sampleJobTrigger(JobDetail sampleJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(sampleJobDetail)
                .withIdentity("sampleCronTrigger")
                // Run every 15 seconds
                .withSchedule(CronScheduleBuilder.cronSchedule("0/15 * * * * ?"))
                .build();
    }
}
