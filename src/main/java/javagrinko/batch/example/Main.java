package javagrinko.batch.example;

import javagrinko.batch.example.service.ImportServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@SpringBootApplication
@EnableBatchProcessing
@Log4j
public class Main {

    @Bean
    public JobRegistry jobRegistry() {
        return new MapJobRegistry();
    }

    @Bean
    public ConversionServiceFactoryBean conversionService() {
        return new ConversionServiceFactoryBean();
    }

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
        JobRegistryBeanPostProcessor bpp = new JobRegistryBeanPostProcessor();
        bpp.setJobRegistry(jobRegistry());
        return bpp;
    }

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
        ImportServiceImpl bean = context.getBean(ImportServiceImpl.class);
        bean.start();
    }
}
