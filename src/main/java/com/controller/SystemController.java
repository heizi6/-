package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/sys")
public class SystemController {
    /**
     * 系统管理员页面
     * @return
     */
    @RequestMapping("/main")
    public String toMain(){
        return "sys/main";
    }

    /**
     * 管理员页面
     * @return
     */
    @RequestMapping("/main0")
    public String toMain0(){
        return "sys/main0";
    }

    /**
     *
     * @return
     */
    @RequestMapping("/main1")
    public String toMain1(){
        return "sys/main1";
    }

    /**
     * 学生页面
     * @return
     */
    @RequestMapping("/main2")
    public String toMain2(){
        return "sys/main2";
    }

    @RequestMapping("/welcome")
    public String welcome(){
        return "sys/welcome";
    }

    @RequestMapping("/editpsw")
    public String editpsw(){
        return "sys/editpsw";
    }

    @RequestMapping("/login")
    public String login(){
        return "sys/login";
    }
    @RequestMapping("/logout")
    public String Logout(HttpSession session){
        if(session != null){
            session.invalidate();  // 清除session
        }
        return "sys/login";
    }
}
