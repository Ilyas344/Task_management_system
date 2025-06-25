package taskmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import taskmanagementsystem.dto.auth.JwtRequest;
import taskmanagementsystem.dto.auth.JwtResponse;
import taskmanagementsystem.mappers.JwtMapper;
import taskmanagementsystem.model.user.User;
import taskmanagementsystem.security.JwtTokenProvider;
import taskmanagementsystem.service.AuthService;
import taskmanagementsystem.service.UserService;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtMapper jwtMapper;

    @Override
    public JwtResponse login(final JwtRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword())
        );
        User user = userService.getByEmail(loginRequest.getEmail());
        JwtResponse jwtResponse = jwtMapper.toDto(user);
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(
                user.getId(),
                user.getEmail(),
                user.getRoles()));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(
                user.getId(),
                user.getEmail()));
        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(final String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }

}
