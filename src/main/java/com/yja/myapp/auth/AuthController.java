package com.yja.myapp.auth;



import com.yja.myapp.auth.entity.Login;
import com.yja.myapp.auth.entity.LoginRepository;
import com.yja.myapp.auth.entity.Profile;
import com.yja.myapp.auth.entity.ProfileRepository;
import com.yja.myapp.auth.reqeust.SignUpRequest;
import com.yja.myapp.auth.utill.HashUtil;
import com.yja.myapp.auth.utill.JwUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
// 바디에서 json객체를 내보낼 수 있게 하는 것
public class AuthController {


    @Autowired
    private LoginRepository repo;

    @Autowired
    private ProfileRepository profileRep;

    @Autowired
    private ProfileRepository profileRepo;


    @Autowired
    private AuthService service;
    @Autowired
    private HashUtil hash;

    @Autowired
    private JwUtil jwt;



    @PostMapping(value = "/signup")
    public ResponseEntity signUp(@RequestBody SignUpRequest req){

        long profileId = service.createIdentity(req);

        return  ResponseEntity.status(HttpStatus.CREATED).body(profileId);
    }

    @PostMapping(value = "/signin")
    public ResponseEntity singIn(@RequestParam String username,
                                 @RequestParam String password,
                                 HttpServletResponse res) {
        System.out.println(username);
        System.out.println(password);

        Optional<Login> login = repo.findByUsername(username);
        if(!login.isPresent()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean isVerified = hash.verifyHash(password, login.get().getSecret());

        if(!isVerified){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }



        Login l = login.get();


        Optional<Profile> profile = profileRep.findByLogin_Id(l.getId());

        if(!profile.isPresent()){
            return null;
        }
        jwt.createToken(l.getId(), l.getUsername(), l.getSecret());

        String token = jwt.createToken(l.getId(), l.getUsername(), l.getSecret());

        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge((int) (jwt.TOKEN_TIMEOUT / 1000L));
        cookie.setDomain("localhost");  // 쿠키를 사용할 수 있는 도메인
        // 응답헤더
        res.addCookie(cookie);
        System.out.println(token);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(ServletUriComponentsBuilder
                        .fromHttpUrl("http://localhost:5500")
                        .build().toUri()).build();
    }
}
