package taskmanagementsystem.service;


import taskmanagementsystem.dto.auth.JwtRequest;
import taskmanagementsystem.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);

}
