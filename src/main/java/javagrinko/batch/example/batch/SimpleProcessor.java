package javagrinko.batch.example.batch;

import javagrinko.batch.example.model.Product;
import org.springframework.batch.item.ItemProcessor;

public class SimpleProcessor implements ItemProcessor<Product, Product> {
    @Override
    public Product process(Product item) throws Exception {
        item.setName(item.getName().toUpperCase());
        return item;
    }
}