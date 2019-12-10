package alankzh.blog.account.core.entity;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class AccountEntity {
    private Long id;
    private String pwd;
    private String loginAccount;
    private String email;
    private String mobile;
    private Date createTime;
    private Date updateTime;
    private Boolean isDelete;
}
