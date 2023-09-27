package com.developwithdaniele.sp06.processor;

import com.developwithdaniele.sp06.dto.ProductDTO;
import com.developwithdaniele.sp06.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class ProductManipulateProcessor implements ItemProcessor<ProductDTO, Product> {
    @Override
    public Product process(ProductDTO productDTO) throws Exception {
        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setProductName(productDTO.getProductName());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setManufacturer(productDTO.getManufacturer());
        String date = productDTO.getExpirationDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date expirationDate = null;
        try{
            expirationDate = sdf.parse(date);
        }catch(ParseException e){
            log.error("Error parsing date: {} ", date, e);
        }
        product.setExpirationDate(expirationDate);
        product.setWeight(productDTO.getWeight());
        product.setAvailable(productDTO.isAvailable());

        return product;
    }
}
