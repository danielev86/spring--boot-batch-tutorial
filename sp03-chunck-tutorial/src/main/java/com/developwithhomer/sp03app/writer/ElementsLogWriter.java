package com.developwithhomer.sp03app.writer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class ElementsLogWriter implements ItemWriter<String> {
    @Override
    public void write(List<? extends String> list) throws Exception {
        log.info("Element list size: "+ list.size());
        list.forEach(element -> log.info(element));
    }
}
