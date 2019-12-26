package alankzh.blog.account.rest.controller;

import alankzh.blog.account.core.jdbc.AccountDao;
import alankzh.blog.account.core.mybatisplus.AccountPlusMapper;
import alankzh.blog.base.api.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Repository
public class HelloController {

    @Autowired
    private AccountPlusMapper accountPlusMapper;


    @GetMapping("/hello")
    public String hello(){
        log.info("hello");
        return  "hello";
    }
}
