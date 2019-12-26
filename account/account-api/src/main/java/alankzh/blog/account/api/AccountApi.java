package alankzh.blog.account.api;


import alankzh.blog.account.api.dto.AccountDto;
import alankzh.blog.base.api.BaseResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.Min;

@FeignClient(name = AccountApiConstant.ACCOUNT_SERVICE, path = "/v1/account", url = "${account-service-url}")
public interface AccountApi {

    @GetMapping(path = "/get")
    BaseResponse<AccountDto> get(@RequestParam("id") @Min(0) Long id);

}
