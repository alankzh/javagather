package alankzh.blog.comment.rest;

import alankzh.blog.account.api.AccountApi;
import alankzh.blog.account.api.dto.AccountDto;
import alankzh.blog.base.api.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CommentHelloController {

    @Autowired
    private AccountApi accountApi;

    @GetMapping("/chello")
    public String chello(@RequestParam("id") Long id){
        BaseResponse<AccountDto> baseResponse = accountApi.get(id);

        log.info(baseResponse.toString());
        return "cccccccccccccccccccccccccc";
    }
}
