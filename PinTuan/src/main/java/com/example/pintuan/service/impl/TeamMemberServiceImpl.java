package com.example.pintuan.service.impl;

import com.example.pintuan.VO.InviteVO;
import com.example.pintuan.entity.TeamMember;
import com.example.pintuan.mapper.TeamMemberMapper;
import com.example.pintuan.service.TeamMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  成员服务实现类
 * </p>
 */
@Service
public class TeamMemberServiceImpl extends ServiceImpl<TeamMemberMapper, TeamMember> implements TeamMemberService {


    @Resource
    private TeamMemberMapper teamMemberMapper;
    @Override
    public void updateByJoin(InviteVO inviteParam) {
        teamMemberMapper.updateByJoin(inviteParam);
    }
}
