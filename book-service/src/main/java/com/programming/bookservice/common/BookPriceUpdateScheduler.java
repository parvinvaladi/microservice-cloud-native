package com.programming.bookservice.common;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BookPriceUpdateScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job updateBookPricesJob;

    @Scheduled(fixedRate = 100000)
    public void update() throws Exception{
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(updateBookPricesJob, jobParameters);
    }
}
