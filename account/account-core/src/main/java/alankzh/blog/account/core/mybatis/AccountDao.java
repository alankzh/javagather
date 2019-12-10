package alankzh.blog.account.core.mybatis;

import alankzh.blog.account.core.entity.AccountEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface AccountDao {

    // 若为pg, oracle, 以及用函数生成序列的mysql, 可以插入前用@SelectKey 获取序列的值
    @Insert("insert into t_account(pwd, login_account, email, mobile) values(#{pwd}, #{loginAccount}, #{email}, #{mobile})")
    int insertAccount(AccountEntity accountEntity);

    @Select("select * from t_account")
    List<AccountEntity> findAll();

    @Select("select * from t_account where id=#{id}")
    AccountEntity findById(@Param("id") Long id);

    @Update("update t_account set pwd=#{pwd} where id=#{id}")
    int updatePwdById(@Param("pwd") String pwd, @Param("id") Long id);

    @UpdateProvider(type=AccountProvider.class, method = "updateProvider")
    int update(AccountEntity accountEntity);


    // options 插入后将key赋值, keyProperty执行entity对象中key对应的feilds, keyColumn执行key对应数据库中字段
    @InsertProvider(type = AccountProvider.class, method = "insertProvider")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int save(AccountEntity accountEntity);
}
