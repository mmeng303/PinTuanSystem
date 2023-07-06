package com.example.pintuan.mapper;

import com.example.pintuan.VO.InviteVO;
import com.example.pintuan.entity.Team;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  团队Mapper 接口
 * </p>
 */
@Mapper
public interface TeamMapper extends BaseMapper<Team> {

    List<Team> selectOfNoJoin(InviteVO inviteParam);

    long selectOfNoJoinCount(InviteVO inviteParam);

}
