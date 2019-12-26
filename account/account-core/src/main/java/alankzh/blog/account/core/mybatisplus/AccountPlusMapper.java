package alankzh.blog.account.core.mybatisplus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Flush;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountPlusMapper extends BaseMapper<AccountPlusEntity> {

}
