package javagrinko.batch.example;

import lombok.extern.log4j.Log4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
@Log4j
public class Main {

    /*@Bean
    @Autowired
    public JobLauncher jobLauncher(ApplicationContext context) throws Exception {
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(context.getBean(JobRepository.class));
        launcher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return launcher;
    }*/

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }
}
