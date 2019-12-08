package alankzh.blog.account.core.entity;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class AccountEntity {
    private Long id;
    private String name;
    private String pwd;
    private String loginAccount;
    private Date createTime;
    private Date updateTime;
    private Boolean isDelete;
}
