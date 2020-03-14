package alankzh.blog.rediskeys.core;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_bankcard")
public class BankcardEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String customerId;
    private String indexCardId;
    private String mobile;
    private String information; // 预留信息
    private Date createTime;
    private Date updateTime;


}
