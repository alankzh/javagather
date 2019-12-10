package alankzh.blog.account.core.mybatisplus;

import alankzh.blog.account.core.AccountCoreConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AccountCoreConfig.class})
public class AccountPlusMapperTest {
    @Autowired
    private AccountPlusMapper accountPlusMapper;

    @Test
    public void find(){
        AccountPlusEntity accountPlusEntity = accountPlusMapper.selectById(1L);
        System.out.println(accountPlusEntity);
    }
}