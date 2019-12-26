package alankzh.blog.account.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class AccountDto{
    private Long id;
    private String salt;
    private String pwd;
    private String loginAccount;
    private String email;
    private String mobile;
    private Date createTime;
    private Date updateTime;
}
