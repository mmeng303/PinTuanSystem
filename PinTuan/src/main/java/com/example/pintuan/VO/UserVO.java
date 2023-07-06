package com.example.pintuan.VO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class UserVO {
    private Integer limit;
    private Integer page;
    private String name;
}
