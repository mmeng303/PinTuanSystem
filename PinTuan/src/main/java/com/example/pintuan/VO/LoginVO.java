package com.example.pintuan.VO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@Data
@NoArgsConstructor(force = true)
public class LoginVO {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
