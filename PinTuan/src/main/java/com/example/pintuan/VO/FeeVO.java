package com.example.pintuan.VO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor(force = true)
public class FeeVO {
    private int isFee;//等于1的时候，代表AA；等于2的时候代表独立缴纳
    private Integer activityId;//活动id
    private Integer memberId;//当前缴纳的用户id
    private BigDecimal fee;//缴纳的费用
    private Integer page;
    private Integer limit;
}
