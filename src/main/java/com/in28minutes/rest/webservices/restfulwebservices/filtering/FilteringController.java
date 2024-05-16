package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.hibernate.sql.ast.tree.predicate.FilterPredicate;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

    @GetMapping("/filtering")
    public MappingJacksonValue filtering() {
        SomeBean someBean = new SomeBean("value1", "value2", "value3");
        return getMappingJacksonValue(someBean, "field1", "field3");
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue filteringList() {
        List<SomeBean> list = Arrays.asList(new SomeBean("value1","value2","value3"),new SomeBean("value4","value5","value6"));
        return getMappingJacksonValue(list, "field2", "field3");
    }

    private MappingJacksonValue getMappingJacksonValue(Object source, String... fields)
    {
        MappingJacksonValue mappingJacksonValue= new MappingJacksonValue(source);
        SimpleBeanPropertyFilter filter=SimpleBeanPropertyFilter.filterOutAllExcept(fields);
        FilterProvider filters= new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }
}


