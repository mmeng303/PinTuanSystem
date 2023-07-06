package com.example.pintuan.service.impl;

import com.example.pintuan.VO.InviteVO;
import com.example.pintuan.entity.Team;
import com.example.pintuan.entity.TeamMember;
import com.example.pintuan.mapper.TeamMapper;
import com.example.pintuan.mapper.TeamMemberMapper;
import com.example.pintuan.service.TeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  团队服务实现类
 * </p>
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {


    @Resource
    private TeamMapper teamMapper;
    @Override
    public List<Team> selectOfNoJoin(InviteVO inviteParam) {
        return teamMapper.selectOfNoJoin(inviteParam);
    }

    @Override
    public long selectOFnOJoinCount(InviteVO inviteParam) {
       return teamMapper.selectOfNoJoinCount(inviteParam);
    }
}
