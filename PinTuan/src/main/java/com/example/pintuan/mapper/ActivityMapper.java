package com.example.pintuan.mapper;

import com.example.pintuan.VO.ActivityVO;
import com.example.pintuan.entity.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  活动Mapper 接口
 * </p>
 */
@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {

    List<Activity> selectByTeamId(ActivityVO activityParam);

    long selectByActivityCount(ActivityVO activityParam);

    void updateActivityId(Activity activity);

}