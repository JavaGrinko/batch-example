package javagrinko.batch.example.batch;

import javagrinko.batch.example.model.Product;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JdbcItemWriter implements ItemWriter<Product> {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public void write(List<? extends Product> items) throws Exception {
        StringBuilder values = new StringBuilder(" VALUES ");
        for (int i = 0; i < items.size(); i++) {
            values.append(" (?, ?, ?, ?)");
            if (i < items.size() - 1) values.append(", ");
        }
        List<Object> arguments = items.stream()
                .map(p -> new Object[]{p.getId(), p.getName(), p.getDescription(), p.getPrice()})
                .flatMap(objects -> Arrays.asList(objects).stream())
                .collect(Collectors.toList());

        String sql = "INSERT INTO PRODUCTS (PRODUCT_ID, NAME, DESCRIPTION, PRICE)" + values.toString();
        jdbcTemplate.update(sql, arguments.toArray());
    }

}