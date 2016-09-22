package javagrinko.batch.example.batch;

import lombok.Data;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

@Data
public class CsvItemReader<T> implements ItemReader {

    @Autowired
    private ConversionService conversionService;

    private File source;
    private Scanner scanner;
    private Class<T> typeParameterClass;

    public CsvItemReader(Class<T> typeParameterClass, String fileName) {
        try {
            source = new ClassPathResource(fileName).getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.typeParameterClass = typeParameterClass;
    }

    @BeforeStep
    public void open() throws FileNotFoundException {
        scanner = new Scanner(source);
    }

    @Override
    public Object read() throws Exception {
        if (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            return conversionService.convert(s, typeParameterClass);
        } else {
            return null;
        }
    }

    @AfterStep
    public void destroy() {
        scanner.close();
    }

}