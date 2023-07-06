package com.example.pintuan.VO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class TeamVO {
    private Integer teamId;
    private String teamName;
    private int page;
    private int limit;
}
