package com.example.pintuan.service;

import com.example.pintuan.VO.FeeVO;
import com.example.pintuan.VO.PieVO;
import com.example.pintuan.entity.Fee;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  费用服务类接口
 * </p>
 */
public interface FeeService extends IService<Fee> {

    List<PieVO> selectCostFee();

    List<Fee> selectListPage(FeeVO feeParam);

    long selectListCount(FeeVO feeParam);

}
