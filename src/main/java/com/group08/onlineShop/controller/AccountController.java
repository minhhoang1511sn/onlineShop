package com.group08.onlineShop.controller;

import com.group08.onlineShop.dto.requestDTO.AccountRequestDTO;
import com.group08.onlineShop.dto.requestDTO.CustomerInfoRequest;
import com.group08.onlineShop.dto.requestDTO.PasswordDto;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.exception.ResourceNotFoundException;
import com.group08.onlineShop.exception.UserNotFoundException;
import com.group08.onlineShop.model.Account;
import com.group08.onlineShop.service.AuthenticationService;
import com.group08.onlineShop.service.CustomerInfoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @Slf4j
@RequestMapping("api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:3000")
public class AccountController {

    private final AuthenticationService authenticationService;
    private final CustomerInfoService customerInfoService;

    @PostMapping("/account/password/reset")
    public ResponseEntity<?> resetPassword(@RequestParam(name = "username") String username,
                                           HttpServletRequest request) throws UserNotFoundException {

        log.info("Reset password");

        authenticationService.forgotPasswordForAccount(username, request);
        // UI must notify user checking email, after forward to HomePage
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/currentUser")
    public ResponseEntity<?> getCurrentUser(){
        Account result = customerInfoService.getCurrentUser();
        return ResponseEntity.ok(result);
    }
    @PutMapping("/update-account")
    public ResponseEntity<?> updateAccount (
    @RequestBody
    AccountRequestDTO accountRequestDTO
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(),
                customerInfoService.updateAccount(accountRequestDTO)));
    }
    @PostMapping("/account/update/password")
    public ResponseEntity<?> updatingNewPassword(@RequestBody PasswordDto passwordDto) {

        log.info("Password DTO: {}", passwordDto.getNewPassword());
        authenticationService.changeAccountPassword(passwordDto);

        return ResponseEntity.ok("OK");
    }

}
