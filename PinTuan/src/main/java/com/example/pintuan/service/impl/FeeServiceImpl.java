package com.example.pintuan.service.impl;

import com.example.pintuan.VO.FeeVO;
import com.example.pintuan.VO.PieVO;
import com.example.pintuan.entity.Fee;
import com.example.pintuan.mapper.FeeMapper;
import com.example.pintuan.service.FeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  费用服务实现类
 * </p>
 */
@Service
public class FeeServiceImpl extends ServiceImpl<FeeMapper, Fee> implements FeeService {


    @Resource
    private FeeMapper feeMapper;
    @Override
    public List<PieVO> selectCostFee() {
        return feeMapper.selectCost();
    }

    @Override
    public List<Fee> selectListPage(FeeVO feeParam) {
        return feeMapper.selectListPage(feeParam);
    }

    @Override
    public long selectListCount(FeeVO feeParam) {
        return  feeMapper.selectFeeCount(feeParam);
    }
}
