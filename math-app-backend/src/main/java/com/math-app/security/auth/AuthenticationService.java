package com.alibou.security.auth;

import com.alibou.security.config.JwtService;
import com.alibou.security.service.TwoFactorAuthenticationService;
import com.alibou.security.token.Token;
import com.alibou.security.token.TokenRepository;
import com.alibou.security.token.TokenType;
import com.alibou.security.entity.UserEntity;
import com.alibou.security.dao.UserRepository;
import com.alibou.security.validators.ObjectsValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final TwoFactorAuthenticationService twoFactorAuthenticationService;
  private final ObjectsValidator<RegisterRequest> registerRequestObjectsValidator;
  private final ObjectsValidator<AuthenticationRequest> authenticationRequestObjectsValidator;
  private final ObjectsValidator<VerificationRequest> verificationRequestObjectsValidator;
  public AuthenticationResponse register(RegisterRequest request) {

    registerRequestObjectsValidator.validate(request);

    var user = UserEntity.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .mfaEnabled(request.isMfaEnabled())
        .build();

    if(request.isMfaEnabled()){
      user.setSecret(twoFactorAuthenticationService.generateNewSecret());
    }

    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
        .secretImageUri(twoFactorAuthenticationService.generateQrCodeImageUrl(user.getSecret()))
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .mfaEnabled(user.isMfaEnabled())
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request){

    authenticationRequestObjectsValidator.validate(request);


    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );


    UserEntity user = repository.findByEmail(request.getEmail())
        .orElseThrow();

    if(user.isMfaEnabled()){
      return AuthenticationResponse.builder()
              .accessToken("")
              .refreshToken("")
              .mfaEnabled(true)
              .build();
    }

    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .mfaEnabled(false)
        .build();
  }

  private void saveUserToken(UserEntity user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(UserEntity user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .mfaEnabled(false)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }

  public AuthenticationResponse veriftyCode(VerificationRequest verificationRequest) {

    verificationRequestObjectsValidator.validate(verificationRequest);

    UserEntity user = repository.findByEmail(verificationRequest.getEmail())
            .orElseThrow(()-> new EntityNotFoundException("No user found"));

    if(twoFactorAuthenticationService.isOtpNotValid(user.getSecret(), verificationRequest.getCode())){
      throw new BadCredentialsException("Code is not correct");
    }

    var jwtToken = jwtService.generateToken(user);


    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .mfaEnabled(user.isMfaEnabled())
            .build();
  }
}
