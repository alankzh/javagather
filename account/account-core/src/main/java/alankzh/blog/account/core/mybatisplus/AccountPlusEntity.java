package alankzh.blog.account.core.mybatisplus;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

// 当命令符合驼峰命名且和表的命名一致时,无须使用TableName, TableId, TableField来修饰
@TableName("t_account")
@Data
public class AccountPlusEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField(value = "pwd")
    private String pwd;
    private String loginAccount;
    private String email;
    private String mobile;
    private Date createTime;
    private Date updateTime;
    private Boolean isDelete;

    // 指明并非映射到数据库的字段
    @TableField(exist = false)
    private String notMappingToDb;
}
