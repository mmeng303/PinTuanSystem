package com.example.pintuan.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pintuan.VO.TeamVO;
import com.example.pintuan.entity.Team;
import com.example.pintuan.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
@RequestMapping("/team")
@Api(tags = "团队管理(团长)")
public class TeamController {
    @Resource
    private TeamService teamService;

    @PostMapping("/saveOrUpdateTeam")
    @ApiOperation(value = "添加或修改团队名称（团长）")
    public R saveOrUpdateTeam(@RequestBody TeamVO teamParam){
        Team team = new Team();
        if (Objects.nonNull(teamParam.getTeamId())){
            BeanUtils.copyProperties(teamParam,team);
            UpdateWrapper<Team> teamUpdateWrapper = new UpdateWrapper<>();
            teamUpdateWrapper.eq("id",team.getId());
            teamUpdateWrapper.set("team_name",team.getTeamName());
            teamUpdateWrapper.set("update_time",new Date());
            teamService.update(team,teamUpdateWrapper);
            return R.ok("修改成功");
        }else {
            team.setTeamName(teamParam.getTeamName());
            team.setCreationTime(LocalDate.now());
            team.setUpdateTime(new Date());
            teamService.save(team);
            return R.ok("添加成功");
        }
    }

    @PostMapping("/list")
    @ApiOperation(value = "查询团队(团长)")
    public R getTeamList(@RequestBody TeamVO teamParam) {
        Page<Team> page = new Page<>(teamParam.getPage(), teamParam.getLimit());
        LambdaQueryWrapper<Team> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(teamParam.getTeamName())) {
            queryWrapper.like(Team::getTeamName, teamParam.getTeamName());
        }
        IPage<Team> teamIPage = teamService.page(page, queryWrapper);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", teamIPage.getTotal());
        map.put("data", teamIPage.getRecords());
        map.put("pages", teamIPage.getPages());
        return R.ok(map);
    }
}

