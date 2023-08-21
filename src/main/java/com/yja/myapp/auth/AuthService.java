package com.yja.myapp.auth;

import com.yja.myapp.auth.entity.Login;
import com.yja.myapp.auth.entity.LoginRepository;
import com.yja.myapp.auth.entity.Profile;
import com.yja.myapp.auth.entity.ProfileRepository;
import com.yja.myapp.auth.reqeust.SignUpRequest;
import com.yja.myapp.auth.utill.HashUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private LoginRepository repo;

    private ProfileRepository profileRepo;

    @Autowired
    private HashUtil hash;

    @Autowired
    public AuthService(LoginRepository repo, ProfileRepository profileRepo){
        this.repo = repo;
        this.profileRepo = profileRepo;
    }

    @Transactional
    public long createIdentity(SignUpRequest req) {

        Login toSaveLogin = Login.builder()
                .username(req.getUsername())
                .secret(hash.createHash(req.getPassword()))
                .build();

        Login savedLogin = repo.save(toSaveLogin);

        Profile toSaveProfile = Profile.builder()
                .email(req.getEmail())
                .login(savedLogin) // 로그인 정보와 연결
                .build();

        Profile savedProfile = profileRepo.save(toSaveProfile);

        return savedProfile.getId();
    }
}