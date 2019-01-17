package cn.itsource.easybuy.controller;

import cn.itsource.easybuy.domain.Employee;
import cn.itsource.easybuy.util.AjaxResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public AjaxResult login(@RequestBody Employee employee){
        if(!("admin".equals(employee.getUsername())&&"123".equals(employee.getPassword()))){
            return AjaxResult.me().setSuccess(false).setMessage("登录失败，账号或密码错误！！！");
        }
        return AjaxResult.me();
    }
}
