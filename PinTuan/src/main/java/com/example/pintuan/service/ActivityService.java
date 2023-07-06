package com.example.pintuan.service;

import com.example.pintuan.VO.ActivityVO;
import com.example.pintuan.entity.Activity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  团队服务类接口
 * </p>
 */
public interface ActivityService extends IService<Activity> {

    List<Activity> selectByTeamId(ActivityVO activityParam);

    long selectByTeamCounts(ActivityVO activityParam);

    void updateByActivityId(Activity activity);

}
