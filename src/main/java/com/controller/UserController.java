package com.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.model.LoginLog;
import com.model.User;
import com.service.LoginLogService;
import com.service.UserService;
import com.utils.MD5Util;
import com.utils.WebUtils;
import com.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userservice;

    @Autowired
    private LoginLogService loginLogService;

    /**
     *
     * @return
     */
    @RequestMapping("/userInfo")
    public String userInfo(){
        return "admin/userInfo";
    }

    /**
     * 管理员用户界面
     * @return
     */
    @RequestMapping("/adminUser")
    public String adminUser(){
        return "admin/userList";
    }
    @RequestMapping("/classAdminUser")
    public String classAdminUser(){
        return "classAdmin/userList";
    }

    /**
     * 学生修改个人信息
     * @return
     */
    @RequestMapping("/studentUpdate")
    public String studentUpdate(){
        return "student/updateManager";
    }


    /**
     * 登录
     * @param user
     * @param session
     * @return
     */
    @RequestMapping("/logindo")
    @ResponseBody
    public Map<String,Object> Logindo(User user, HttpSession session){
        HashMap<String,Object> resMap = new HashMap<String,Object>();
        int number = user.getNumber();
        String password = MD5Util.digest(user.getPassword());
        System.out.println("用户名："+number+"  密码："+password);
        List<User> userlist = userservice.selectByUP(number, password);
        if(userlist.size() >= 1){
            for (User user1 : userlist) {
                session.setAttribute("currentUser", user1);   // 将用户对象添加到session
                //添加日志到loginLog表
                LoginLog loginLog = new LoginLog();
                loginLog.setLogindate(new Date());
                loginLog.setLoginname(user1.getUsername());
                loginLog.setLoginip(WebUtils.getHttpServletRequest().getRemoteAddr());
                int i = loginLogService.addLoginLog(loginLog);
                //System.out.println(i);
                session.setMaxInactiveInterval(1800);   //  设置session的过期时间  单位是秒
                resMap.put("res", "1");
                resMap.put("role",user1.getRole());
            }

        }else{
            resMap.put("res", "0");
            resMap.put("message","用户名或密码错误");
        }
        return resMap;
    }

    /**
     * 修改密码
     * @param id
     * @param oldpsw
     * @param newpsw
     * @param againpsw
     * @return
     */
    @RequestMapping("/editpswdo")
    @ResponseBody
    public Map<String,Object> editpswdo(int id,String oldpsw,String newpsw,String againpsw){
        Map map = new HashMap();
        //先根据id查询出来原密码
        User user1 = userservice.selectUserById(id);
        if ((user1.getPassword()).equals(MD5Util.digest(oldpsw))) {

            User user = new User();
            if (newpsw.equals(againpsw)) {//后端校验两次输入的密码是否相同
                user.setId(id);
                user.setPassword(MD5Util.digest(newpsw));
            }
            int i = userservice.updateByPrimaryKeySelective(user);
            if (i >= 1) {
                map.put("res", 1);
                map.put("message","【"+user1.getUsername()+"】密码已修改");
            } else {
                map.put("message", "更新失败");
            }
        }else{
            map.put("message","原密码不正确");
        }
        return map;
    }

    /**
     * 用户列表
     * @param userVo
     * @return
     */
    @RequestMapping("/getUserList")
    @ResponseBody
    public Map getUserList(UserVo userVo) {
        Page<Object> p = PageHelper.startPage(userVo.getPage(),userVo.getLimit());
        p.setOrderBy("id");
        HashMap<String, Object> map = new HashMap<String,Object>();
        User user = (User) WebUtils.getHttpSession().getAttribute("currentUser");
        int role = user.getRole();
        List<User> userList = userservice.selectByExample(userVo,role);
        PageInfo pageInfo = new PageInfo(userList,5);
        long count = pageInfo.getTotal();
        map.put("code", 0);
        map.put("count", count);
        map.put("data", pageInfo.getList());
        return map;
    }
    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping("/deleteUser")
    @ResponseBody
    public Map<String,Object> deleteUser(int id){
        HashMap<String,Object> resMap = new HashMap<String,Object>();
        int res = userservice.deleteUsers(id);
        if(res >=1 ){
            resMap.put("res", "ok");
            resMap.put("message", "删除成功");
        }else{
            resMap.put("res", "fail");
            resMap.put("message", "删除失败");
        }
        return resMap;
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @RequestMapping("/editUser")
    @ResponseBody
    public Map<String,Object> editUser(User user){
        HashMap<String,Object> resMap = new HashMap<String,Object>();

        if(user.getPassword()!=null){
            user.setPassword(MD5Util.digest(user.getPassword()));
        }
        int res = userservice.updateUser(user);
        if(res >=1 ){
            resMap.put("msg", "修改成功");
        }else{
            resMap.put("msg", "修改失败");
        }
        return resMap;
    }

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    @RequestMapping("/deleteBatchUser")
    @ResponseBody
    public Map deleteBatchUser(Integer []ids){
        Map map =  new HashMap();
        int count  = userservice.deleteBatchUser(ids);
        if(count == ids.length){
            map.put("code",0);
            map.put("msg","删除成功！！！");
        }else {
            map.put("msg","删除失败");
        }
        return map;
    }

    /**
     * 添加用户
     * @param userVo
     * @return
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public Map<String,Object> addUser(UserVo userVo){
        HashMap<String,Object> resMap = new HashMap<String,Object>();
        System.out.println(userVo.getNumber());
        User user = new User();
        user.setUsername(userVo.getUsername());
        user.setPassword(MD5Util.digest("123"));
        user.setNumber(userVo.getNumber());
        user.setEmail(userVo.getEmail());
        if( userVo.getRole()==null){
            user.setRole(3);
        }

        user.setSex(userVo.getSex());
        user.setStatus(userVo.getStatus());
        if(userVo.getClasses().equals("无")){

        }else{
            user.setClasses(userVo.getClasses());
        }
        user.setRole(userVo.getRole());
        int res = userservice.addUser(user);
        if(res >=1 ){
            resMap.put("code", 0);
            resMap.put("code",1);
            resMap.put("msg", "添加成功");
        }else{
            resMap.put("code", "0");
            resMap.put("msg", "添加失败");
        }
        return resMap;
    }

    /**
     * 班级管理员查找班级学生
     * @param userVo
     * @param session
     * @return
     */
    @RequestMapping("/getClassUserList")
    @ResponseBody
    public Map getClassUserlist(UserVo userVo,HttpSession session) {
        Page<Object> p = PageHelper.startPage(userVo.getPage(),userVo.getLimit());
        p.setOrderBy("id");
        HashMap<String, Object> map = new HashMap<String,Object>();
        User user =(User) WebUtils.getHttpSession().getAttribute("currentUser");
        userVo.setClasses(user.getClasses());
        List<User> userlist = userservice.selectByClassExample(userVo);

        PageInfo pageInfo = new PageInfo(userlist,5);
        long count = pageInfo.getTotal();
        map.put("code", 0);
        map.put("count", count);
        map.put("data", pageInfo.getList());
        return map;

    }

    /**
     * 添加用户
     * @param userVo
     * @return
     */
    @RequestMapping("/addClassUser")
    @ResponseBody
    public Map<String,Object> addClassUser(UserVo userVo){
        Map<String,Object> resMap = new HashMap<String,Object>();
        User user = new User();
        user.setUsername(userVo.getUsername());
        if(userVo.getPassword()==null||userVo.getPassword().equals("")){
            user.setPassword(MD5Util.digest("123"));
        }else {
            user.setPassword(userVo.getPassword());
        }
        user.setNumber(userVo.getNumber());
        user.setEmail(userVo.getEmail());
        user.setRole(3);
        user.setSex(userVo.getSex());
        user.setStatus(0);
        user.setClasses(userVo.getClasses());
        int res = userservice.addUser(user);
        if(res >=1 ){
            resMap.put("code", 0);
            resMap.put("code",1);
            resMap.put("msg", "添加成功");
        }else{
            resMap.put("code", "0");
            resMap.put("msg", "添加失败");
        }
        return resMap;
    }

    /**
     * 学生得到自己的信息
     * @return
     */
    @RequestMapping("/getMe")
    @ResponseBody
    public Map getMe(UserVo userVo){
        User user =(User) WebUtils.getHttpSession().getAttribute("currentUser");
        userVo.setId(user.getId());
        Page<Object> p = PageHelper.startPage(userVo.getPage(),userVo.getLimit());
        p.setOrderBy("id");
        HashMap<String, Object> map = new HashMap<String,Object>();
        List<User> userlist = userservice.selectMeByExample(userVo);
        PageInfo pageInfo = new PageInfo(userlist,5);
        long count = pageInfo.getTotal();
        map.put("code", 0);
        map.put("count", count);
        map.put("data", pageInfo.getList());
        return map;
    }

    /**
     * 学生自己修改信息
     * @param user
     * @return
     */
    @RequestMapping("/editStudent")
    @ResponseBody
    public Map<String,Object> editStudent(User user){
        System.out.println(user.getWorking());
        HashMap<String,Object> resMap = new HashMap<String,Object>();
        if(user.getPassword()!=null){
            user.setPassword(MD5Util.digest(user.getPassword()));
        }
        int res = userservice.updateUser(user);
        if(res >=1 ){
            resMap.put("msg", "修改成功");
        }else{
            resMap.put("msg", "修改失败");
        }
        return resMap;
    }


}
