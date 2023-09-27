package com.developwithhomer.sp03app.reader;


import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ElementsReader implements ItemReader<String> {

    private List<String> elementList;

    private Iterator<String> iterator;

    public ElementsReader() {
        elementList = new ArrayList<>();
        for (int i = 1; i<=100;i++){
            elementList.add("Element num: " + i);
        }
        iterator = elementList.iterator();
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return iterator.hasNext() ? iterator.next() :  null;
    }
}
