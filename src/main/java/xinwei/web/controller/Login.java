package xinwei.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import xinwei.web.bean.User;
import xinwei.web.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by xinwei on 1/25/2017.
 */
@Controller
//@SessionAttributes(names = {"userAccount"},types = {xinwei.web.bean.User.class})
public class Login {


    private String accesskey;

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session) {
        //System.out.println(2+user.getUserEmail());
        User userAccount = userService.checkUser(user);
        if (userAccount == null) {
            return "forward:/";
        }
        //System.out.println(user.getUserEmail());
        session.setAttribute("user", userAccount);
        return "redirect:/content/first";
    }

    @RequestMapping("/createAccount")
    public String createAccount(@ModelAttribute User user, @RequestParam String access, @RequestParam(required = false) MultipartFile file, HttpSession session) {
        //System.out.println(email);
        //System.out.println(user.getUserEmail());
        if (!access.equals(accesskey)) {
            return "redirect:/signUp";
        }
        if (file == null || file.isEmpty()) {
            if (userService.saveUser(user)) {
                session.setAttribute("user", user);
                return "redirect:/content/first";
            }
            return "redirect:/signUp";
        } else {
            if (userService.saveUser(user, file)) {
                session.setAttribute("user", user);
                return "redirect:/content/first";
            }
            return "redirect:/signUp";
        }
    }

    @RequestMapping("/updateAccount")
    public String updateAccount(@ModelAttribute User user, @RequestParam(required = false) MultipartFile file, HttpSession session) {
        User userAccount = userService.getUser(user.getUserEmail());
        userAccount.setPassword(user.getPassword());
        if (file == null || file.isEmpty()) {
            userService.updateUser(userAccount);

        } else {
            userService.updateUser(userAccount, file);
        }
        session.setAttribute("user", userAccount);
        return "redirect:/content/first";
    }


    @ModelAttribute
    public User getUser(@RequestParam String email, @RequestParam String psw, @RequestParam(required = false) String name) {
        //System.out.println(1+email);
        User user = new User(email, psw);
        if (name == null || name.equals("")) {
            return user;
        }
        user.setDisplayName(name);
        return user;
    }

    public String getAccesskey() {
        return accesskey;
    }
    @Resource(name = "accesskey")
    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }
}
