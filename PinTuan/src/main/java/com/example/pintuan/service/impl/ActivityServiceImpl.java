package com.example.pintuan.service.impl;

import com.example.pintuan.VO.ActivityVO;
import com.example.pintuan.entity.Activity;
import com.example.pintuan.mapper.ActivityMapper;
import com.example.pintuan.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  活动服务实现类
 * </p>
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Resource
    private ActivityMapper activityMapper;
    @Override
    public List<Activity> selectByTeamId(ActivityVO activityParam) {
        return   activityMapper.selectByTeamId(activityParam);
    }

    @Override
    public long selectByTeamCounts(ActivityVO activityParam) {
        return activityMapper.selectByActivityCount(activityParam);
    }

    @Override
    public void updateByActivityId(Activity activity) {
        activityMapper.updateActivityId(activity);
    }
}
