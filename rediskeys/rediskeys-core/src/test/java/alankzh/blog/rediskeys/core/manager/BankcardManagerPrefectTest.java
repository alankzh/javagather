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
    private BankcardManagerMaster bankcardManagerPrefect;


    @Test
    public void ttt() {
        System.out.println("".equals(null));
        String idxPre = "idx_";
        for (int i = 1; i < 6; i++) {
            String idx = idxPre + i;
            BankcardEntity bankcardEntity = bankcardManagerPrefect.findByIndexCardId(idx);
        }
    }

    /**
     * race condition
     * 情景一
     * 最简单的情景
     */
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

    /**
     * race condition
     * 情景二
     * 更复杂的情景
     * updateByCustomerId  和 queryByIndexCardId 并发执行.
     * update操作查询key-keys时,查到1个idx在缓存: idx_1
     * 这时会去删除 key-multi-cid, key-single-idx_1, key-rev-idx_1, key-keys
     * 而同时query操作完成,在key-keys下挂载了新的后缀: idx_1,idx_2
     * 但update操作此时完成,key-keys被删除.
     * 但是孤立的key-single-idx_2 却没有被删除.
     */

}