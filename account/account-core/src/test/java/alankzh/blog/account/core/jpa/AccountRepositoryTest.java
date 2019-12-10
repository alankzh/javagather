package alankzh.blog.account.core.jpa;

import alankzh.blog.account.core.AccountCoreConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AccountCoreConfig.class})
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void findAll(){
        List<AccountJpaEntity> accountJpaEntityList = accountRepository.findAll();
        System.out.println(accountJpaEntityList);
    }

}