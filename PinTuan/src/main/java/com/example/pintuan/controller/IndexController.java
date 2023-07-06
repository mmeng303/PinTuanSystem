package com.example.pintuan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.pintuan.VO.PieVO;
import com.example.pintuan.VO.ResultVO;
import com.example.pintuan.entity.Activity;
import com.example.pintuan.entity.Team;
import com.example.pintuan.service.ActivityService;
import com.example.pintuan.service.FeeService;
import com.example.pintuan.service.TeamService;
import com.example.pintuan.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/index")
@Api(tags = "团长首页")
public class IndexController {
    @Resource
    private UserService userService;
    @Resource
    private ActivityService activityService;
    @Resource
    private FeeService feeService;
    @Resource
    private TeamService teamService;

    @PostMapping("/userCount")
    @ApiOperation(value = "用户个数")
    public R  getUserCount(){
        int count = userService.count(null);
        return R.ok(count);
    }

    @PostMapping("/activityCount")
    @ApiOperation(value = "已完结活动个数")
    public R getActivityCount(){
        int count = activityService.count(new QueryWrapper<Activity>().eq("is_completed", 1));
        return R.ok(count);
    }

    @PostMapping("/pie")
    @ApiOperation(value = "活动饼图")
    public R getPie(){
     List<PieVO> list =feeService.selectCostFee();
        List<PieVO> result = list.stream()
                .collect(Collectors.groupingBy(
                        PieVO::getActivityId,
                        Collectors.summingInt(PieVO::getF)
                ))
                .entrySet().stream().map(entry -> new PieVO(entry.getKey(), entry.getValue()))   //构造方法，重新转成对象
                .collect(Collectors.toList());
        long mid = result.stream().filter(PieVO -> (PieVO.getF() > 100L && PieVO.getF() < 500L)).count();
        long min = result.stream().filter(PieVO -> PieVO.getF() <=100L).count();
        long max = result.stream().filter(PieVO -> PieVO.getF() >= 500L).count();
        ArrayList<ResultVO> resultVOS = new ArrayList<>();
        resultVOS.add(new ResultVO("总计团费>￥500的活动",max));
        resultVOS.add(new ResultVO("总计￥100 团费 <￥500的活动",mid));
        resultVOS.add(new ResultVO("总计团费<￥100的活动",min));

        return R.ok(resultVOS);
    }

    @PostMapping("/column")
    @ApiOperation(value = "获取4个季度的团队个数")
    public R getCloumn(){
        List<Team> creationTime = teamService.list(new QueryWrapper<Team>().select("creation_time"));
        // 创建一个用于统计季度的映射
        Map<Integer, Integer> quarterCount = new HashMap<>();
        // 遍历日期数组，统计季度数量
        for (Team team : creationTime) {
            LocalDate date = team.getCreationTime();
            int quarter = date.get(IsoFields.QUARTER_OF_YEAR);
       quarterCount.put(quarter, quarterCount.getOrDefault(quarter, 0) + 1);
        }
        ArrayList<Integer> integers = new ArrayList<Integer>();
        // 打印季度统计结果
        for (Map.Entry<Integer, Integer> entry : quarterCount.entrySet()) {
           integers.add(entry.getValue());
        }
        return R.ok(integers);
    }
}
