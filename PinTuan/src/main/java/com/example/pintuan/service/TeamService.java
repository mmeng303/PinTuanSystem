package com.example.pintuan.service;

import com.example.pintuan.VO.InviteVO;
import com.example.pintuan.entity.Team;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  团队服务类接口
 * </p>
 */
public interface TeamService extends IService<Team> {

    List<Team> selectOfNoJoin(InviteVO inviteParam);

    long selectOFnOJoinCount(InviteVO inviteParam);

}
