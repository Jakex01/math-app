package com.alibou.security.service;

import com.alibou.security.entity.UserEntity;
import com.alibou.security.mapstruct.dto.UserDto;
import com.alibou.security.mapstruct.mapper.MapStructMapper;
import com.alibou.security.dao.UserRepository;
import com.alibou.security.request.ChangePasswordRequest;
import com.alibou.security.validators.ObjectsValidator;
import jakarta.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final ObjectsValidator<ChangePasswordRequest> validator;


    public ResponseEntity<?> changePassword(ChangePasswordRequest request, Principal connectedUser) {

         validator.validate(request);


        var user = ((UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());

        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new IllegalStateException("Wrong password");
        }

        if(!request.getConfirmationPassword().equals(request.getNewPassword())){
            throw new IllegalStateException("Passwords don't match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);


        return new ResponseEntity<>(HttpStatus.ACCEPTED);





    }


    public UserDto getUserData(Principal connectedUser) {

         UserEntity user = ((UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());

         UserDto userDto = MapStructMapper.INSTANCE.userToUserDto(user);

        if(userDto.getLastModified()==null){
            userDto.setLastModified(LocalDateTime.now());
        }


        return userDto;

    }

}
