package xinwei.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by xinwei on 1/26/2017.
 */
@Controller
public class Welcome {

    @RequestMapping("/")
    public String welcome() {
        return "hello";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("user", null);
        return "redirect:/";
    }

    @RequestMapping("/signUp")
    public String signUp() {
        return "signUp";
    }


    @RequestMapping("/myAccount")
    public String myAccount() {
        return "myAccount";
    }

}
