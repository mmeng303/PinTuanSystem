package com.example.pintuan.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 团费实体类
 * </p>
 *
 * @author 代写java，就找小杨:1719036792
 * @since 2023-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Fee对象", description="")
public class Fee implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer activityId;

    private Integer memberId;

    private BigDecimal activityFee;

    private BigDecimal aaFee;
    @TableField(exist = false)
    private BigDecimal realTimeAaFee;//实时计算的AA费用

    @TableField(exist = false)
    private BigDecimal realTimeCostFee;//实时计算的总费用

    @TableField(exist = false)
    private String activityName;  //活动名称


}
