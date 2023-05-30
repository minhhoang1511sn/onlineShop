package com.group08.onlineShop.controller;

import com.group08.onlineShop.dto.requestDTO.AuthenticationRequest;
import com.group08.onlineShop.dto.requestDTO.RegisterRequest;
import com.group08.onlineShop.exception.UserNotFoundException;
import com.group08.onlineShop.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth") @Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:3000")
public class AuthenticationController {
    private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authentication")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) throws UserNotFoundException {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
