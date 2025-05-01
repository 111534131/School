package com.example.trpg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // 允許公開訪問的路徑（整合舊的和新配置）
                        .requestMatchers("/trpg/api/register", "/trpg/api/login", "/trpg/api/admin/check").permitAll()
                        .requestMatchers("/trpg/api/character/attributes/**").permitAll()
                        .requestMatchers("/trpg/api/admin/**").permitAll() // 確保 admin 端點公開
                        .requestMatchers("/trpg/api/**").permitAll() // 臨時允許所有 /trpg/api/** 路徑（可根據需求調整）
                        .requestMatchers("/audio/**").permitAll()

                        .anyRequest().authenticated() // 其他路徑需要認證
                )
                .csrf(csrf -> csrf.disable()) // 禁用 CSRF 保護
                .httpBasic(httpBasic -> httpBasic.disable()) // 禁用 HTTP Basic 認證
                .formLogin(formLogin -> formLogin.disable()); // 禁用表單登入

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}