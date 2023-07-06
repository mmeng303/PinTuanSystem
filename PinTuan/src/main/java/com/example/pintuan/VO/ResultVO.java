package com.example.pintuan.VO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class ResultVO {
    private String name;
    private long value;

    public ResultVO(String name, long value) {
        this.name = name;
        this.value = value;
    }
}
