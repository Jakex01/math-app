package com.alibou.security.controller;



import com.alibou.security.request.ChangePasswordRequest;
import com.alibou.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;



@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;


    @PatchMapping("/pass")
    public ResponseEntity<?> changePassword(
          @RequestBody @Valid ChangePasswordRequest request,
          Principal connectedUser
    ) {

       return service.changePassword(request, connectedUser);

    }
    @GetMapping("/data")
    public ResponseEntity<?> getUserData(
           Principal connectedUser
    ){
         return ResponseEntity.ok(service.getUserData(connectedUser));
    }

}
