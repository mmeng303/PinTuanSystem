package com.example.pintuan.VO;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor(force = true)
public class ActivityExportVO {
    @ExcelProperty(value = "id",index = 0)
    private Integer id;
    @ExcelProperty(value = "活动名称",index = 1)
    private String activityName;
    @ExcelProperty(value = "AA费用",index = 2)
    private BigDecimal realTimeAaFee;
    @ExcelProperty(value = "个人费用",index = 3)
    private BigDecimal activityFee;
    @ExcelProperty(value = "总费用",index = 4)
    private BigDecimal realTimeCostFee;
}
