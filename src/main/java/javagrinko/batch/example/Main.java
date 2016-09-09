package javagrinko.batch.example;

import javagrinko.batch.example.service.ImportService;
import lombok.extern.log4j.Log4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@SpringBootApplication
@EnableBatchProcessing
@Log4j
public class Main {

    @Bean
    @Autowired
    public JobLauncher jobLauncher(ApplicationContext context) throws Exception {
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(context.getBean(JobRepository.class));
        launcher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return launcher;
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        context.getBean(ImportService.class).start();
    }
}
