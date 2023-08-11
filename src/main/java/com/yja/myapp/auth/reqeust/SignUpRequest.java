package com.yja.myapp.auth.reqeust;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpRequest {
    private  String username;
    private String password;
    private String nickname;
    private String email;
}
