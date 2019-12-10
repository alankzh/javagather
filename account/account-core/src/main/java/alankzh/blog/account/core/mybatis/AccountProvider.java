package alankzh.blog.account.core.mybatis;

import alankzh.blog.account.core.entity.AccountEntity;
import org.apache.ibatis.jdbc.SQL;

public class AccountProvider {


    public String updateProvider(AccountEntity accountEntity){
        // 匿名内部类 + 实例初始器
        return new SQL(){{
               UPDATE("t_account");
               if (accountEntity.getPwd() != null){
                   SET("pwd = #{pwd}");
               }
               if (accountEntity.getEmail() != null){
                   SET("email = #{email}");
               }
               if (accountEntity.getMobile() != null){
                    SET("mobile = #{mobile}");
               }if (accountEntity.getLoginAccount() != null){
                    SET("login_account = #{loginAccount}");
               }
               WHERE("id = #{id}");
            }}.toString();
    }

    public String insertProvider(AccountEntity accountEntity){
        return new SQL(){{
            INSERT_INTO("t_account");
            if (accountEntity.getPwd() != null){
                VALUES("pwd", "#{pwd}");
            }
            if (accountEntity.getEmail() != null){
                VALUES("email", "#{email}");
            }
            if (accountEntity.getLoginAccount() != null){
                VALUES("login_account", "#{loginAccount}");
            }
            if (accountEntity.getMobile() != null){
                VALUES("mobile", "#{mobile}");
            }
        }}.toString();
    }
}
