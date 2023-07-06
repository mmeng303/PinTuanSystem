package com.example.pintuan.service;

import com.example.pintuan.VO.InviteVO;
import com.example.pintuan.entity.TeamMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  成员服务类接口
 * </p>
 */
public interface TeamMemberService extends IService<TeamMember> {

    void updateByJoin(InviteVO inviteParam);

}
