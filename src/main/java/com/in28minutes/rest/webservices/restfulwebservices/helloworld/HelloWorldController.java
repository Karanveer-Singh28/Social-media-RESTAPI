package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

//REST API
// /hello-world -  returns "Hello world back"

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource)
    {
        this.messageSource=messageSource;
    }

    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping(path = "hello-world-bean")
    public helloWorldBean helloWorldBean()
    {
        return new helloWorldBean("Hello World");
    }

    //path parameters

    @GetMapping(path ="/hello-world/path-variable/{name}")
    public helloWorldBean helloWorldPathVariable(@PathVariable String name)
    {
        return new helloWorldBean(String.format("Hello World, %s", name));
    }

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized() {
        Locale local= LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message", null,"Default Message", local);
        //return "Hello World International";
    }


}

