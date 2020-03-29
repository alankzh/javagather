package alankzh.blog.redishotswitch.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import sss.config.aaa.MagicManager;

import java.util.Random;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MagicManager magicManager;

    @RequestMapping(value = "/getString", method = RequestMethod.GET)
    public String getString(@RequestParam("key") String key){
        String value = null;
        try {
            value = redisTemplate.opsForValue().get(key);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("get value:" + value);
        return value;
    }

    @RequestMapping(value = "/setString", method = RequestMethod.GET)
    public void setString(@RequestParam("key") String key, @RequestParam("value") String value){
        System.out.println("set key:" + key + ", value:" + value);
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e){
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
            e.printStackTrace();
            boolean switchSuccess = magicManager.hotSwapMagic(redisTemplate);
            if (switchSuccess){
                redisTemplate.opsForValue().set(key, value);
            }
        }
    }

    @RequestMapping(value = "/updateString", method = RequestMethod.GET)
    public String updateString(@RequestParam("key") String key, @RequestParam("value") String value){

        String oldValue = null;
        try {
            oldValue = redisTemplate.opsForValue().getAndSet(key, value);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("update key:" + key + ", oldValue:" + oldValue + ", newValue:" + value);
        return oldValue;
    }

    @RequestMapping(value = "/deleteString", method = RequestMethod.GET)
    public void deleteString(@RequestParam("key") String key){
        System.out.println("del key:" + key);
        redisTemplate.delete(key);
    }

    @GetMapping(value = "/randomOperation")
    public void randomOpe(){
        Random random = new Random();
        int opType = random.nextInt(3) + 1;
        int opKey = random.nextInt(100) + 1;
        int opValue = random.nextInt(100);
        try {
            randomOpe(opType, opKey, opValue);
        } catch (Exception e){
            System.out.println(e.getClass().getSimpleName());
            boolean switchSuccess = magicManager.hotSwapMagic(redisTemplate);
            if (switchSuccess){
                randomOpe(opType, opKey, opValue);
            }
        }
    }

    private void randomOpe(int type, int key, int value){
        switch (type){
            case 1:
                String v = redisTemplate.opsForValue().get(key+"");
                System.out.println("get key:" + key + " value:" + value);
                break;
            case 2:
                redisTemplate.opsForValue().set(key+"", value+"");
                System.out.println("set key:" + key + ", value:" + value);
                break;
            case 3:
                deleteString(key+"");
                System.out.println("delete key:" + key);
                break;
        }
    }


}
