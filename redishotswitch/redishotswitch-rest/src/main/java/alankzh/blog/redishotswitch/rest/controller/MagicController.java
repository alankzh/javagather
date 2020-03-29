package alankzh.blog.redishotswitch.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sss.config.aaa.MagicManager;

@RestController
@RequestMapping("/magic")
public class MagicController {

    @Autowired
    private MagicManager magicManager;

    @PostMapping(value = "/setPassword")
    public void setPassword(@RequestParam("password") String password){
        magicManager.setPassword(password);
    }

    @GetMapping(value = "/getPassword")
    public String getPassword(){
        return magicManager.getPassword();
    }
}
