package alankzh.blog.rediskeys.core.manager;

import alankzh.blog.rediskeys.core.BankcardEntity;
import alankzh.blog.rediskeys.core.config.RediskeysCoreConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RediskeysCoreConfig.class})
public class BankcardManagerPrefectTest {

    @Autowired
    private BankcardManagerPrefect bankcardManagerPrefect;


    @Test
    public void ttt() {
        System.out.println("".equals(null));
        String idxPre = "idx_";
        for (int i = 1; i < 6; i++) {
            String idx = idxPre + i;
            BankcardEntity bankcardEntity = bankcardManagerPrefect.findByIndexCardId(idx);
        }
    }

    @Test
    public void findByIndexCardId() throws Exception {
        String idxPre = "idx_";
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i < 6; i++) {
            String idx = idxPre + i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.countDown();
                        countDownLatch.await();
                        BankcardEntity bankcardEntity = bankcardManagerPrefect.findByIndexCardId(idx);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
        countDownLatch.countDown();
        Thread.sleep(2000);
    }

}