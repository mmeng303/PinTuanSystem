package com.example.pintuan.mapper;

import com.example.pintuan.VO.FeeVO;
import com.example.pintuan.VO.PieVO;
import com.example.pintuan.entity.Fee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  费用Mapper 接口
 * </p>
 */
@Mapper
public interface FeeMapper extends BaseMapper<Fee> {

    List<PieVO> selectCost();

    List<Fee> selectListPage(FeeVO feeParam);

    long selectFeeCount(FeeVO feeParam);

}
