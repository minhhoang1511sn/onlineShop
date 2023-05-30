package com.group08.onlineShop.controller;

import com.group08.onlineShop.dto.requestDTO.CustomerInfoRequest;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.exception.ResourceNotFoundException;
import com.group08.onlineShop.service.CustomerInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:3000")
public class CustomerInfoController {
    private final CustomerInfoService customerInfoService;
    @GetMapping("/get-all-customer-info")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllCustomerInfo() {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), customerInfoService.getAllCustomerInfo()));
    }

    @GetMapping("/get-customer-info")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getCustomerInfoByAccount(
            @RequestParam(name="accountID") Long accountID
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), customerInfoService.getCustomerInfoByAccount(accountID)));
    }
    @GetMapping("/get-customer-info/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getCustomerInfoByID(
            @PathVariable(name="customerInfoID") Long customerInfoID) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), customerInfoService.getCustomerInfoByID(customerInfoID)));
    }


    @GetMapping("/get-customer-info-default")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getCustomerInfoDefaultByAccount(
            @RequestParam(name="accountID") Long accountID,
            @RequestParam(name = "isDefault") Boolean isDefault
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(),
                customerInfoService.getCustomerInfoDefaultByAccount(accountID, isDefault)));
    }

    @PostMapping("/post-customer-info")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createCustomerInfo(
            @RequestBody CustomerInfoRequest customerInfoRequest
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), customerInfoService.createCustomerInfo(customerInfoRequest)));
    }

    @PutMapping("/update-customer-info")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateCustomerInfo(
            @RequestParam(name = "customerInfoID") Long customerInfoID,
            @RequestBody CustomerInfoRequest customerInfoRequest
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(),
                customerInfoService.updateCustomerInfo(customerInfoID, customerInfoRequest)));
    }

    @DeleteMapping("/delete-customer-info")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteCustomerInfo(
            @RequestParam(name = "customerInfoID") Long customerInfoID
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), customerInfoService.deleteCustomerInfo(customerInfoID)));
    }
}
