package com.example.pintuan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.pintuan.VO.InviteVO;
import com.example.pintuan.entity.Team;
import com.example.pintuan.entity.TeamMember;
import com.example.pintuan.service.TeamMemberService;
import com.example.pintuan.service.TeamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/team-member")
@ApiOperation("团员管理")
public class TeamMemberController {

    @Resource
    private TeamMemberService teamMemberService;
    @Resource
    private TeamService teamService;

    @PostMapping("/invite")
    @ApiOperation(value = "邀请（团长）")
    public R getRelease(@RequestBody InviteVO inviteParam){
        TeamMember one = teamMemberService.getOne(new QueryWrapper<TeamMember>().eq("team_id", inviteParam.getTeamId()).eq("user_id", inviteParam.getUserId()));
        if (Objects.isNull(one)){
            TeamMember teamMember = new TeamMember();
            BeanUtils.copyProperties(inviteParam,teamMember);
            teamMember.setJoinTime(new Date());
            teamMember.setIsJoin(0);
            teamMemberService.save(teamMember);
            return R.ok("邀请成功");
        }
        return R.failed("已对该成员发送邀请，无需重复发送");
    }

    @PostMapping("/getListOfNoJoin")
    @ApiOperation(value = "查询被邀请的团队（团员）")
    public R getNoJoin(@RequestBody InviteVO inviteParam){
        inviteParam.setPage((inviteParam.getPage()-1) *inviteParam.getPage());
        inviteParam.setIsJoin(0);
        HashMap<String, Object> map = new HashMap<>();
        List<Team> list=teamService.selectOfNoJoin(inviteParam);
        long count=teamService.selectOFnOJoinCount(inviteParam);
        map.put("data",list);
        map.put("total",count);
        return R.ok(map);
    }

    @PostMapping("/join")
    @ApiOperation(value = "接受邀请团队")
    public R onJoin(@RequestBody InviteVO inviteParam){
        teamMemberService.updateByJoin(inviteParam);
        return R.ok("接受成功");
    }

    @PostMapping("/getListOfJoin")
    @ApiOperation(value = "查询接受（已加入）的团队（团员）")
    public R getJoin(@RequestBody InviteVO inviteParam){
        inviteParam.setPage((inviteParam.getPage()-1) *inviteParam.getPage());
        inviteParam.setIsJoin(1);
        HashMap<String, Object> map = new HashMap<>();
        List<Team> list=teamService.selectOfNoJoin(inviteParam);
        long count=teamService.selectOFnOJoinCount(inviteParam);
        map.put("data",list);
        map.put("total",count);
        return R.ok(map);
    }
}

