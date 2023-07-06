package com.example.pintuan.mapper;

import com.example.pintuan.VO.InviteVO;
import com.example.pintuan.entity.TeamMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  成员Mapper 接口
 * </p>
 */
@Mapper
public interface TeamMemberMapper extends BaseMapper<TeamMember> {
    @Update("update team_member set is_join=1 where team_id=#{teamId} and user_id=#{userId}")
    void updateByJoin(InviteVO inviteParam);
}
