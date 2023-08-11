package com.yja.myapp.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // candidate key(후보키)
    // 시스템에는 PK로 사용적합하지 않음

    @Column(unique = true)
    private  String username;

    @Column(length = 500)
    private String secret;


    // EAGER : JPA 메서드 실행시 바로 관계 테이블을 조회
    // LAZY : 관계 테이블 객체를 엑세스 시점에 SQL 실행해서 조회
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "profile_id")
    // 굳이 쓰지 않아도 Profile클래스에 id 값을 생성했기 때문에 forein key가 생성이 됨
//    @JsonIgnore
    // 특정 필드를 JSON 변환에서 제외시킬 수 있음

    // 관계 테이블에 키값만 저장
    private long profileId;


}
