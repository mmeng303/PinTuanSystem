package com.example.pintuan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pintuan.VO.ActivityVO;
import com.example.pintuan.entity.Activity;
import com.example.pintuan.service.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
@RequestMapping("/activity")
@Api(tags = "活动管理")
public class ActivityController {
    @Resource
    private ActivityService activityService;

    @ApiOperation(value = "活动发布")
    @PostMapping("/release")
    public R getActivity(@RequestBody Activity activity){
        if(Objects.nonNull(activity.getId()) && activity.getId()!=0){
            activityService.updateByActivityId(activity);
            return R.ok("发布成功");
        }else {
            activity.setStartTime(new Date());
            activityService.save(activity);
            return R.ok("发布成功");
        }

    }

    @ApiOperation(value = "根据不同角色查看活动")
    @PostMapping("/list")
    public R getLIist(@RequestBody ActivityVO activityParam){

        HashMap<String, Object> map=new HashMap<>();
        if (activityParam.getUserId()!=2){
            activityParam.setPage((activityParam.getPage()-1)*activityParam.getLimit());
            List<Activity> activities =activityService.selectByTeamId(activityParam);
            long total=activityService.selectByTeamCounts(activityParam);
            map.put("total",total);
            map.put("data",activities);
            return R.ok(activities);
        }
        QueryWrapper<Activity> activityQueryWrapper=new QueryWrapper<>();
        if (!StringUtils.isEmpty(activityParam.getActivityName())){
            activityQueryWrapper.like("activity_name",activityParam.getActivityName());
        }
        Page<Activity> page = new Page<>(activityParam.getPage(),activityParam.getLimit());
        IPage<Activity> teamIPage = activityService.page(page, activityQueryWrapper);
        map.put("total",teamIPage.getTotal());
        map.put("pages",teamIPage.getPages());
        map.put("data",teamIPage.getRecords());
        return R.ok(map);
    }
    @GetMapping("/completed/{id}")
    @ApiOperation(value = "完结活动（团长）")
    public R getCompleted(@PathVariable(name = "id")Integer id){
        UpdateWrapper<Activity> activityUpdateWrapper = new UpdateWrapper<>();
        activityUpdateWrapper.eq("id",id);
        activityUpdateWrapper.set("is_completed",1);
        Activity activity = new Activity();
        activityService.update(activity,activityUpdateWrapper);
        return R.ok("完结成功");
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id获取活动详情")
    public R getActivityDetails(@PathVariable(name = "id")Integer id){
        Activity byId = activityService.getById(id);
        return R.ok(byId);
    }
}

