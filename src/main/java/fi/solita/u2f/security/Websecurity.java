package fi.solita.u2f.security;

import fi.solita.u2f.repository.UserRepository;
import fi.solita.u2f.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuration for spring websecurity. Allows non-authenticated requests
 * for login-pages.
 * @author markkuko
 */
@EnableWebSecurity
public class Websecurity extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new UserService(userRepository);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/css/**","/js/**","/webjars/**","/registration").permitAll()
                .antMatchers("/u2fauth").hasRole("PREAUTH_USER")
                .antMatchers("/u2fregister").hasRole("PREAUTH_USER")
                .antMatchers("/home").hasRole("USER")
                .antMatchers("/").hasRole("USER")
                .anyRequest().authenticated();
        http.formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/u2fauth",true)
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher(
                        "/logout")).logoutSuccessUrl("/login");;

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean());
        //        .inMemoryAuthentication()
        //        .withUser("user").password("{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG").roles("USER");
    }
}
