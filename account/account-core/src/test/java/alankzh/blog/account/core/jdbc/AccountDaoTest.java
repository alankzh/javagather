package alankzh.blog.account.core.jdbc;


import alankzh.blog.account.core.entity.AccountEntity;
import org.junit.Test;

public class AccountDaoTest {
    @Test
    public void insertAccount() {
        AccountDao.insertAccount(new AccountEntity()
                .setLoginAccount("357").setEmail("357@qq.com").setMobile("110"));
    }
}