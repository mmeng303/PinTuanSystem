package com.example.pintuan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.example.pintuan.VO.LoginVO;
import com.example.pintuan.VO.UserVO;
import com.example.pintuan.entity.User;
import com.example.pintuan.service.UserService;
import com.example.pintuan.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public R login(@RequestBody @Validated LoginVO loginParam){
        User one = userService.getOne(new QueryWrapper<User>().like("username", loginParam.getUsername()).eq("password", loginParam.getPassword()));
        if (Objects.nonNull(one)){
            //将userId存入token中
            String token = JwtUtils.createJWT(String.valueOf(one.getId()),one.getName(),120L);
            Map<String,Object> map = new HashMap<>();
            map.put("user",one);
            map.put("token",token);
            return R.ok(map);
        }
        return R.failed("账号密码错误");
    }
    @PostMapping("/allUsers")
    @ApiOperation(value = "所有用户信息")
    public R getAllUser(@RequestBody UserVO userParam) {
        Page<User> page = new Page<>(userParam.getPage(), userParam.getLimit());
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(userParam.getName())) {
            queryWrapper.like(User::getName, userParam.getName());
        }
        IPage<User> userIPage = userService.page(page, queryWrapper);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", userIPage.getTotal());
        map.put("pages", userIPage.getPages());
        map.put("data", userIPage.getRecords());
        return R.ok(map);
    }
    @ApiOperation(value = "修改或添加用户（统一接口）")
    @PostMapping("/saveOrUpdateUser")
    public R saveOrUpdateUser(@RequestBody User user){
        if (Objects.nonNull(user.getId())){
            userService.updateById(user);
            return R.ok("修改成功");
        }else {
            return R.ok(userService.save(user));
        }
    }

    @ApiOperation(value = "查询所有团员用户信息")
    @PostMapping("/listTeamMember")
    public R getTeamMember(){
        List<User> list = userService.list(new QueryWrapper<User>().eq("authority", 2));
        return R.ok(list);
    }


}


