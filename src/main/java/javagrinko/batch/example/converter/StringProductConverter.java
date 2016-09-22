package javagrinko.batch.example.converter;

import javagrinko.batch.example.model.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class StringProductConverter implements Converter<String, Product> {

    @Override
    public Product convert(String source) {
        String[] split = source.split(",");
        Product product = new Product();
        product.setId(Long.parseLong(split[0]));
        product.setName(split[1]);
        product.setDescription(split[2]);
        product.setPrice(Double.parseDouble(split[3]));
        return product;
    }

}