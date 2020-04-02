package alankzh.blog.redishotswitch.rest.controller;

import alankzh.blog.magic.DarkMagician;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/magic")
public class MagicController {

    @PostMapping(value = "/setPassword")
    public void setPassword(@RequestParam("password") String password){
        DarkMagician.setPassword(password);
    }

    @GetMapping(value = "/getPassword")
    public String getPassword(){
        return DarkMagician.getPassword();
    }

    @GetMapping(value = "/isMagicTime")
    public boolean isMagicTime(){
        return DarkMagician.isMagicTime();
    }
}
