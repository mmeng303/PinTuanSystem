package com.example.pintuan.VO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class PieVO {
    private Integer activityId;
    private Integer f;

    public PieVO(Integer activityId, Integer f) {
        this.activityId = activityId;
        this.f = f;
    }
}
