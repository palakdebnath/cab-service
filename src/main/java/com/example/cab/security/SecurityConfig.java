package com.example.cab.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                inMemoryAuthentication()
                .withUser("user").password("{noop}user").roles("USER").and()
                .withUser("driver").password("{noop}driver").roles("DRIVER").and()
                .withUser("admin").password("{noop}admin").roles("USER","ADMIN");
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/contacts/**").hasAnyRole("ADMIN","USER","DRIVER").and()
//                .httpBasic()
//                .and()
//                .csrf().disable();
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/actuator/health").permitAll()
                .antMatchers("/v1/**").hasAnyRole("ADMIN","USER","DRIVER").and()
                .httpBasic();
    }
}
