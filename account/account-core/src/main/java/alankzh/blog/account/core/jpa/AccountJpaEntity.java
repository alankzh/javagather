package alankzh.blog.account.core.jpa;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "t_account")
public class AccountJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键自增策略
    private Long id;
    private String pwd;
    private String loginAccount;
    private String email;
    private String mobile;
    private Date createTime;
    private Date updateTime;
    private Boolean isDelete;

    // 指明并非映射到数据库的字段
    @Transient
    private String notMappingToDb;
}
