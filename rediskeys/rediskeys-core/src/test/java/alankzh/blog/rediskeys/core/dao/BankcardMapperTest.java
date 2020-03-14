package alankzh.blog.rediskeys.core.dao;

import alankzh.blog.rediskeys.core.BankcardEntity;
import alankzh.blog.rediskeys.core.config.RediskeysCoreConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RediskeysCoreConfig.class})
public class BankcardMapperTest {

    @Autowired
    private BankcardMapper bankcardMapper;

    @Test
    public void findByCustomerId() {
        String cid = "fucker";
        List<BankcardEntity> list = bankcardMapper.findByCustomerId(cid);
        System.out.println(list);
    }

    @Test
    public void findByIndexCardId() {
    }

    @Test
    public void updateInformationByIndexCardId() {
        int ef = bankcardMapper.updateInformationByIndexCardId("aaa", "idx_1");
        System.out.println(ef);
    }

    @Test
    public void updateInformationByCustomerId() {

    }
}