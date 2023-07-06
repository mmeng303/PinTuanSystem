package com.example.pintuan.VO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class ActivityVO {
    private Integer userId;
    private String  activityName;
    private Integer page;
    private Integer limit;
}
