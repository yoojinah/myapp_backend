package com.yja.myapp.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor


public class AuthProfile {
    private long id; // 프로필 id
    private String username; // 로그인 사용자이름
}
