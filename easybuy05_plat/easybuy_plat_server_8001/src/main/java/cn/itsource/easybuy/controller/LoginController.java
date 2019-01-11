package cn.itsource.easybuy.controller;

import cn.itsource.easybuy.domain.Employee;
import cn.itsource.easybuy.util.AjaxResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public AjaxResult login(@RequestBody Employee employee){
        if(!("admin".equals(employee.getUsername())&&"123".equals(employee.getPassword()))){
            return AjaxResult.me().setSuccess(false).setMessage("操作失败！！！");
        }
        return AjaxResult.me();
    }
}
