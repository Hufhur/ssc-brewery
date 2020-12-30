package guru.sfg.brewery.config;

import guru.sfg.brewery.security.JpaUserDetailService;
import guru.sfg.brewery.security.RestHeadersAuthFilter;
import guru.sfg.brewery.security.RestUriAuthFilter;
import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    JpaUserDetailService jpaUserDetailService;


//    public RestHeadersAuthFilter restHeadersAuthFilter(AuthenticationManager manager) {
//        RestHeadersAuthFilter filter = new RestHeadersAuthFilter(new AntPathRequestMatcher("/api/v1/**"));
//        filter.setAuthenticationManager(manager);
//        return filter;
//    }
//
//    public RestUriAuthFilter restUriAuthFilter(AuthenticationManager manager) {
//        RestUriAuthFilter filter = new RestUriAuthFilter(new AntPathRequestMatcher("/api/v1/**"));
//        filter.setAuthenticationManager(manager);
//        return filter;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

//        http.addFilterBefore(restHeadersAuthFilter(authenticationManager()),
//                  UsernamePasswordAuthenticationFilter.class);
//
//        http.addFilterBefore(restUriAuthFilter(authenticationManager()),
//                UsernamePasswordAuthenticationFilter.class);


        http
                .authorizeRequests(authorize -> {
                    authorize
                            .antMatchers("/h2-console/**").permitAll()
                            .antMatchers("/", "/webjars/**", "/login", "/resources/**", "/beers", "/beers/find").permitAll()
                            .antMatchers("/beers/find", "/beers*").permitAll()
                            .antMatchers("/brewery/breweries").hasRole("CUSTOMER")
                            .antMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasRole("ADMIN")
                            .antMatchers(HttpMethod.GET, "/api/v1/breweries/**").hasRole("CUSTOMER")
                            .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                            .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();

                } )
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();

        http.headers().frameOptions().sameOrigin();
    }


//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("spring")
//                .password("guru")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(this.jpaUserDetailService).passwordEncoder(passwordEncoder());

//        auth.inMemoryAuthentication()
//                .withUser("spring")
//                .password("{bcrypt}$2a$10$BuE.3DUP55mrTHvU.4cfiOh1WPyHsvH6XyqTGsNoF6lpOIn7NEstq")
//                .roles("ADMIN")
//                .and()
//                .withUser("user")
//                .password("{sha256}390530137a662069c39793a9a0323714079a561e1b424cefe2786e661a471d147f1039eebda465c7")
//                .roles("ADMIN")
//                .and()
//                .withUser("scott")
////                .password("{ldap}{SSHA}dW8ATdrI7TWtXKsjL6mQttPL6ZjBLjUlrhmHWg==")
//                .password("{bcrypt15}$2a$15$lEK6yX1E/gCbBUvnDNR1qOyM8KkBgbqF.A6qBfKUo3B26UH0kYo/u")
//                .roles("CUSTOMER");

//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
