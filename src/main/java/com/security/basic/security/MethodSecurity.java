package com.security.basic.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

//securedEnabled @Secured 어노테이션을 사용하여 인가를 처리하고 싶을 때 사용하는 옵션
//prePostEnabled @PreAuthorize, @PostAuthorize 어노테이션을 사용하여 인가를 처리하고 싶을 때 사용하는 옵션
//jsr250Enabled @RolesAllowed 어노테이션을 사용하여 인가를 처리하고 싶을 때 사용하는 옵션
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class MethodSecurity {

}
