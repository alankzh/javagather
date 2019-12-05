package alankzh.blog.account.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Repository
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        log.info("hello");
        return  "hello";
    }
}
