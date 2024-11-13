package net.devgrr.mydemo;

import java.util.Date;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleJobController {

  @Autowired private Job job;

  @Autowired private JobLauncher jobLauncher;

  @Scheduled(cron = "0 10 15 * * *")
  @GetMapping("/batch/sampleJob")
  public String launchSampleJob()
      throws JobInstanceAlreadyCompleteException,
          JobExecutionAlreadyRunningException,
          JobParametersInvalidException,
          JobRestartException {
    JobParameters jobParameters =
        new JobParametersBuilder().addDate("time", new Date()).toJobParameters();

    jobLauncher.run(job, jobParameters);

    return "OK";
  }
}
