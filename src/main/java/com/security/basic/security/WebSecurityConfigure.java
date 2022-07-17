package com.security.basic.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfigure {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable();

        //요청에 대한 설정
        //permitAll시 해당 url에 대한 인증 정보를 요구하지 않는다.
        //authenticated시 해당 url에는 인증 정보를 요구한다.(로그인 필요)
        //hasAnyRole시 해당 url에는 특정 권한 정보를 요구한다.
//        http
//            .authorizeRequests()
//            .antMatchers("/home").permitAll()
//            .antMatchers("/user").hasAnyRole("USER", "ADMIN")
//            .antMatchers("/admin").hasAnyRole("ADMIN")
//            .anyRequest().authenticated();

        http
                .authorizeHttpRequests()
                .antMatchers("/**").permitAll();


        //로그인폼 관련 설정
        //loginPage 로그인 페이지를 설정합니다. 해당 페이지를 설정하지 않으면 Spring Security에서 구현한 디폴트 화면이 노출됩니다.
        //defaultSuccessUrl 인가된 사용자일 경우 로그인 성공 후 이동할 페이지를 설정합니다.
        //failureUrl 로그인 검증이 실패한 경우 이동할 페이지를 설정합니다.
        //successHandler defaultSuccessUrl에서 이동할 페이지만 설정하였다면 handler를 구현하고 이를 등록하면 좀 더 세부적인 작업을 수행할 수 있습니다.
        //failureHandler successHandler와 마찬가지로 handler 구현을 통해 더 세부적인 작업을 수행할 수 있습니다.
        http
                .formLogin()
                .defaultSuccessUrl("/home")
                .failureUrl("/login?error=true")
//        .successHandler(successHandler())
//        .failureHandler(failureHandler())
                .usernameParameter("email")
                .passwordParameter("password");

        http
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login");

        //동시 세션 관련 설정
        //maximumSessions 최대 허용 가능 세션 수를 설정
        //maxSessionPreventsLogin 위에서 설정한 최대 허용 세션의 수가 되었을 때 추가적인 인증이(세션 생성) 있을 경우 어떻게 처리할지 설정
        //true일 경우 현재 사용자 인증 실패, false인 경우 기존 세션 만료
        //expiredUrl 세션이 만료된 경우 이동할 페이지를 설정정
        http
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/expired");

        return http.build();
    }

    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);

        return roleHierarchy;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomAuthSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new CustomAuthFailureHandler();
    }
}
