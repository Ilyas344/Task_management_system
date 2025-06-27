package taskmanagementsystem.config;


import lombok.RequiredArgsConstructor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import taskmanagementsystem.mappers.JwtMapper;
import taskmanagementsystem.mappers.UserMapper;
import taskmanagementsystem.model.security.JwtProperties;
import taskmanagementsystem.repository.UserRepository;
import taskmanagementsystem.security.JwtTokenProvider;
import taskmanagementsystem.security.JwtUserDetailsService;
import taskmanagementsystem.service.impl.AuthServiceImpl;
import taskmanagementsystem.service.impl.UserServiceImpl;


@TestConfiguration
@RequiredArgsConstructor
public class TestConfig {

    @Bean
    @Primary
    public BCryptPasswordEncoder testPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtProperties jwtProperties() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecret(
                "dmdqYmhqbmttYmNhamNjZWhxa25hd2puY2xhZWtic3ZlaGtzYmJ1dg=="
        );
        return jwtProperties;
    }




    @Bean
    public Configuration configuration() {
        return Mockito.mock(Configuration.class);
    }


    @Bean
    public JwtTokenProvider tokenProvider(JwtProperties jwtProperties,
                                          UserDetailsService userDetailsService,
                                          UserServiceImpl userService) {
        return new JwtTokenProvider(jwtProperties,
                userService);
    }






    @Bean
    public UserMapper userMapper() {
        return Mockito.mock(UserMapper.class);
    }

    @Bean
    public JwtMapper jwtMapper() {
        return Mockito.mock(JwtMapper.class);
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return Mockito.mock(AuthenticationManager.class);
    }

}