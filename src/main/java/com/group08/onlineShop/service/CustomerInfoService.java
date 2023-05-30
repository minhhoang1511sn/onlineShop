package com.group08.onlineShop.service;

import com.group08.onlineShop.dto.requestDTO.AccountRequestDTO;
import com.group08.onlineShop.dto.requestDTO.CustomerInfoRequest;
import com.group08.onlineShop.dto.responseDTO.AccountResponseDTO;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.dto.responseDTO.CustomerInfoResponse;
import com.group08.onlineShop.exception.ResourceNotFoundException;
import com.group08.onlineShop.model.Account;
import com.group08.onlineShop.model.CustomerInfo;

import java.util.List;

public interface CustomerInfoService {
    List<CustomerInfoResponse> getAllCustomerInfo();
    List<CustomerInfoResponse> getCustomerInfoByAccount(Long accountID) throws ResourceNotFoundException;
    CustomerInfoResponse getCustomerInfoDefaultByAccount(Long accountID, Boolean isDefault) throws ResourceNotFoundException;
    CustomerInfoResponse getCustomerInfoByID(Long customerInfoID) throws ResourceNotFoundException;
    CustomerInfoResponse createCustomerInfo(CustomerInfoRequest customerInfoRequest) throws ResourceNotFoundException;
    CustomerInfoResponse updateCustomerInfo(Long customerInfoID, CustomerInfoRequest customerInfoRequest) throws ResourceNotFoundException;
    ApiResponse deleteCustomerInfo(Long customerInfoID) throws ResourceNotFoundException;

    Account getCurrentUser();
    Account updateAccount( AccountRequestDTO accountRequestDTO) throws ResourceNotFoundException;
}
