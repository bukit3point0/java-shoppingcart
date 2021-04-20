package com.lambdaschool.shoppingcart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "resource_id";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        super.configure(resources);
        resources.resourceId(RESOURCE_ID)
                .stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.authorizeRequests()
                // ant stands for "another neat tool"
                // make sure this shit is spelled correctly because it won't throw errors, it'll just return blank
                .antMatchers("/", "/h2-console/**",
                        "/swagger-resources/**",
                        "/swagger-resource/**",
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/createnewuser")
                .permitAll()
                .antMatchers("/roles/**", "/products/**", "/users/**")
//                .hasAnyRole("ADMIN")
                .authenticated()
                .antMatchers("/carts/**", "logout", "/oauth/token")
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());

        // cross site request forgery
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.logout().disable();
    }
}

/*

Admin
/roles/**
/products/**
/users/user
/users/user/{id}
/users/user/{id}
/users/user/name/{likeusername}
/users/user/name/like/{username}
/users/user
/users/user/{id}
/users/user/{userid}

authenticated
/carts/**
remove users/user/{userid}
 */
