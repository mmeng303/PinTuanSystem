package com.example.pintuan.VO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor(force = true)
public class ActivityFeeExportVO {
    private String mainContent;

    private String activityName;

    private BigDecimal realTimeAaFee;

    private BigDecimal activityFee;

    private BigDecimal realTimeCostFee;
}
