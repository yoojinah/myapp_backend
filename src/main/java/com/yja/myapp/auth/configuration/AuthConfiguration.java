package com.yja.myapp.auth.configuration;

import com.yja.myapp.auth.utill.HashUtil;
import com.yja.myapp.auth.utill.JwUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfiguration {
    @Bean
    public HashUtil hashUtil(){return  new HashUtil();}
    @Bean
    public JwUtil jwUtil(){return new JwUtil();}
}
