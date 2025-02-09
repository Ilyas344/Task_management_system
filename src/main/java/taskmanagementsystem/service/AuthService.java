package java.taskmanagementsystem.service;


import java.taskmanagementsystem.dto.auth.JwtRequest;
import java.taskmanagementsystem.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);

}
