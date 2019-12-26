package alankzh.blog.account.biz;

import alankzh.blog.account.core.entity.AccountEntity;
import alankzh.blog.account.core.mybatis.AccountDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    public AccountEntity findById(Long id){
        return accountDao.findById(id);
    }

}
