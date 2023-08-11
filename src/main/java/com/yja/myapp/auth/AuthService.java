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
    private  HashUtil hash;

    @Autowired
    public AuthService(LoginRepository repo, ProfileRepository profileRepo){
        this.repo = repo;
        this.profileRepo = profileRepo;
    }


    // profile 정보 생성과 login정보생성은 1개의 처리로 실행
    // 둘중 하나라도 fail이 나면 둘다 생성 안됨
    // insert, update, delete DML(데이터조작)
    // 트랜잭션(TRANSACTION) : 거래
    // CONTROLLER에서는 TRANCACTION처리가 안됨

    // @Controller : 요청값 검증, 간단한 데이터 조작, 적절한 응답값 반환
    // @Service : 트랜잭션처리, 외부시스템 연동

    // 프로파일 정보 생성 : insert
    // ok


    //@Transactional
    // JPA 처리에 대해서 메서드 수준으로 트랜잭션을 처리해줌
    // 메서드안에 인서트, 업테이트, 딜리트  :1개의 트랜잭션(tx)으로 묶어줌
    // 메서드에서 예외처리가 발생하면 rollback(원상태로 복구)을 함
    // 메서드가 정상 처리 되면 commit이 실행되는 순간 --> db파일에 반영

    // insert는 table rock이 걸리고    delete, update는 row rock이 걸림
    // 아이솔레이션 레벨(commit, uncommit)


    @Transactional
    public long createIdentity(SignUpRequest req) {


        // 로그인 정보 insert 객체 생성
        Login toSaveLogin = Login.builder()
                .username(req.getUsername())
                .secret(hash.createHash(req.getPassword()))
                .build();

        // 실질적인 insertgn 저장
        Login savedLogin = repo.save(toSaveLogin);


        // id값을 가져옴
        Profile toSaveProfile = Profile.builder()
                .nickname(req.getNickname())
                .email(req.getEmail())
                .login(savedLogin)
                .build();

        long profileId = profileRepo.save(toSaveProfile).getId();

        // 로그인 정보에는 프로필 아이디 값만 저장
        savedLogin.setProfileId(profileId);
        repo.save(savedLogin);

        // 프로필 아이디 반환
        return profileId;



    }
}
