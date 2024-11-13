package net.devgrr.mydemo;

import java.util.Arrays;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SampleJobConfiguration {

  @Bean
  public Job sampleJob(JobRepository jobRepository, Step sampleStep) {
    return new JobBuilder("sampleJob", jobRepository).start(sampleStep).build();
  }

  @Bean
  public Step sampleStep(
      JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("sampleStep", jobRepository)
        .<String, String>chunk(10, transactionManager)
        .reader(reader())
        .processor(processor())
        .writer(writer())
        .build();
  }

  @Bean
  public ItemReader<String> reader() {
    return new ListItemReader<>(Arrays.asList("Spring", "Batch", "Example"));
  }

  @Bean
  public ItemProcessor<String, String> processor() {
    return item -> item.toUpperCase();
  }

  @Bean
  public ItemWriter<String> writer() {
    return items -> {
      for (String item : items) {
        System.out.println("Processing item: " + item);
      }
    };
  }
}
