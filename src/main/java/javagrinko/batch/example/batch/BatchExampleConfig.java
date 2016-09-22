package javagrinko.batch.example.batch;

import javagrinko.batch.example.model.Product;
import lombok.extern.log4j.Log4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j
public class BatchExampleConfig {

    public static final int CHUNK_SIZE = 2;
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemWriter<Product> customWriter() {
        return new JdbcItemWriter();
    }

    @Bean
    public ItemReader<Product> customReader() {
        return new CsvItemReader<>(Product.class, "import.csv");
    }

    @Bean
    public SimpleProcessor processor() {
        return new SimpleProcessor();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Product, Product>chunk(CHUNK_SIZE)
                .reader(customReader())
                .processor(processor())
                .writer(customWriter())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importProductsJob")
                .start(step1())
                .build();
    }

}
