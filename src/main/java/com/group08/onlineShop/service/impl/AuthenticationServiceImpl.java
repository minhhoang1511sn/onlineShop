package com.group08.onlineShop.service.impl;

import com.group08.onlineShop.dto.requestDTO.AuthenticationRequest;
import com.group08.onlineShop.dto.requestDTO.PasswordDto;
import com.group08.onlineShop.dto.requestDTO.RegisterRequest;
import com.group08.onlineShop.dto.responseDTO.AuthenticationResponse;
import com.group08.onlineShop.exception.AppException;
import com.group08.onlineShop.exception.UserNotFoundException;
import com.group08.onlineShop.model.Account;
import com.group08.onlineShop.model.Role;
import com.group08.onlineShop.model.Token;
import com.group08.onlineShop.repository.AccountRepo;
import com.group08.onlineShop.repository.TokenRepo;
import com.group08.onlineShop.service.AuthenticationService;
import com.group08.onlineShop.service.JwtService;
import com.group08.onlineShop.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service @Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    // 30 min
    private static final int EXPIRATION = 30;

    private final MailService mailService;

    private final TokenRepo tokenRepo;
    private final AccountRepo accountRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        // add check for email exists in DB
        if(accountRepo.existsByEmail(request.getEmail())){
            throw new AppException(400,"Email already exits");
        }
        var user = Account.builder().firstName(request.getFirstName()).
                lastName(request.getLastName()).email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .active(true)
                .build();
        accountRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request)
            throws UserNotFoundException, AuthenticationException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = accountRepo.findAccountByEmail(request.getEmail())
                .orElseThrow();
        if(!user.getActive())
        {
            throw new AppException(400,"User not authenticate");
        }
        else {
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken).build();
        }
    }

    @Override
    public void forgotPasswordForAccount(String username, HttpServletRequest request) throws UserNotFoundException {

        Account acc = accountRepo.findAccountByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        // generate token
        Token myToken = generateTokenResetPasswordForAccount(acc);
//        log.info("HTTP context: {}", request.getRequestURI());
        // send mail
        constructResetTokenEmail(request.getRequestURL().toString(),
                myToken.getCode(), acc);
    }

    private Token generateTokenResetPasswordForAccount(Account account) {

        String token = UUID.randomUUID().toString();

        Date current = new Date();

        // expire date is 30 min
        Date expiryDate = new Date(current.getTime() + (EXPIRATION * 60000));
        //
        log.info("Get current time: {}", current);
        log.info("Get expiry date: {}", expiryDate);

        Token myToken = Token.builder()
                .code(token)
                .account(account)
                .expiryDate(expiryDate)
                .build();

        log.info("Toke Forgot password: {}", myToken);

        // Saving Toke in db
        tokenRepo.saveAndFlush(myToken);

        return myToken;
    }

    private void constructResetTokenEmail(String contextPath, String token,
                                          Account account) {
        String subject = "Verify Forgot Password Your Account!";

        String url = contextPath + "?token=" + token;

        // TODO: Edit content in htmlBody, for reset password
        String htmlBody = "<html><body>" +
                "<h1>Welcome to Our Website!</h1>" +
                "<p>Thank you for registering with us. We are excited to have you join our community!</p>" +
                "<p>Please verify your email address by clicking on the following link:</p>" +
                "<a href='" + url + "'>Verify Now</a>" +
                "<p>Thank you,</p>" +
                "<p>The Team</p>" +
                "</body></html>";

        log.info("Body: {}", url);

        try {

            mailService.sendMailForgotPassword(account.getEmail(), subject, htmlBody);

        } catch (MessagingException e) {

            // TODO: Handle Exception this
            log.error("Handle error when sending Email: {}", e.getMessage());
        }
    }

    @Override
    public String validatePasswordResetToken(String token) {

        // Check, is token found
        Token getToken = tokenRepo.findByTokenString(token);

        return ! isTokenFound(getToken) ? "InvalidToken"
                : isTokenExpired(getToken) ? "Expired"
                : null;
    }

    private boolean isTokenFound(Token passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(Token passToken) {

        final Calendar cal = Calendar.getInstance();

        return passToken.getExpiryDate().before(cal.getTime());
    }

    @Override
    public void changeAccountPassword(PasswordDto passwordDto) {

        // get account
        Account account = new Account();

        // Checking password
        String result = validatePasswordResetToken(passwordDto.getToken());

        // Valid token
        if (result == null) {

            Token myToken = tokenRepo.findByTokenString(passwordDto.getToken());
            account = myToken.getAccount();

            log.info("Account: {},{}", account.getEmail(), account.getPassword());

            savingChangePassword(account, passwordDto.getNewPassword());

            tokenRepo.delete(myToken);
        }
        // Invalid token
        else {
            log.info("Notify: {}", result);
        }
    }

    private void savingChangePassword( Account account, String newPassword) {

        account.setPassword(passwordEncoder.encode(newPassword));

        accountRepo.saveAndFlush(account);
    }
}
