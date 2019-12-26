package alankzh.blog.account.rest.controller;

import alankzh.blog.account.api.AccountApi;
import alankzh.blog.account.api.dto.AccountDto;
import alankzh.blog.base.api.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@Slf4j
@RestController
@RequestMapping("/v1/account")
public class AccountController implements AccountApi {

    @Override
    @GetMapping("/get")
    public BaseResponse<AccountDto> get(@Min(0) Long id) {
        log.info("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        return new BaseResponse<>();
    }
}
