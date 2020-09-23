package io.catalyte.training.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * I believe this is meant to exclude the AuthController from the Auth checks, otherwise you
 * wouldn't be able to pass in a login without logging in first. It's recursive.
 * <p>
 * No idea how to do it though
 */
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().antMatchers("/authenticate").permitAll().anyRequest()
        .authenticated();
  }
}
