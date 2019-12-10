package alankzh.blog.account.core.mybatis;

import alankzh.blog.account.core.AccountCoreConfig;
import alankzh.blog.account.core.entity.AccountEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AccountCoreConfig.class})
public class AccountDaoTest {

    @Autowired
    private AccountDao accountDao;

    @Test
    public void insertAccount() {
        int ef = accountDao.insertAccount(new AccountEntity()
                .setLoginAccount("820").setEmail("820@qq.com").setMobile("110"));
    }

    @Test
    public void findAll(){
        List<AccountEntity> list = accountDao.findAll();
        System.out.println(list);
    }

    @Test
    public void findById(){
        AccountEntity accountEntity = accountDao.findById(1L);
        System.out.println(accountEntity);
    }

    @Test
    public void updatePwdById(){
        int ef = accountDao.updatePwdById("fucker", 1L);
        System.out.println(ef);

        AccountEntity accountEntity = accountDao.findById(1L);
        System.out.println(accountEntity);
    }

    @Test
    public void update(){
        int ef = accountDao.update(new AccountEntity().setId(1L).setEmail("830@qq.com"));
        AccountEntity accountEntity = accountDao.findById(1L);
        System.out.println(accountEntity);
    }

    @Test
    public void save(){
        AccountEntity accountEntity = new AccountEntity()
                .setLoginAccount("3334").setEmail("3334@qq.com").setMobile("119");
        accountDao.save(accountEntity);
        System.out.println(accountEntity);
    }
}