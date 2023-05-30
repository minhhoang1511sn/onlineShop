package com.group08.onlineShop.utils;

import com.group08.onlineShop.model.Account;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Utils {
    private static String pathImg = "Images";


    public static String getBaseURL(HttpServletRequest request) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }

    public static File convertMultiPartToFile(MultipartFile file) throws IOException {
        if (!Files.isDirectory(Path.of(pathImg))){
            File newDir = new File(pathImg);
            newDir.mkdir();
        }
        File convFile = new File(pathImg+"/" +file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public static Long getIdCurrentUser(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        Account u = (Account) authentication.getPrincipal();
        return u.getId();
    }

}
