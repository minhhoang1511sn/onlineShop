package com.group08.onlineShop.service;

import com.group08.onlineShop.dto.requestDTO.StockRequest;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.dto.responseDTO.StockResponse;
import com.group08.onlineShop.exception.ResourceNotFoundException;
import com.group08.onlineShop.model.Account;

import java.util.List;

public interface StockService {
    List<StockResponse> getAllStock();

    List<StockResponse>  getStockByProduct(Long productID);

    StockResponse getStockByProductAndColorAndSize(Long productID, String color, String size) throws ResourceNotFoundException;

    StockResponse addProductToStock(StockRequest stockRequest) throws ResourceNotFoundException;

    StockResponse updateStockQuantity(Long stockID, StockRequest stockRequest);

    ApiResponse deleteStockByID(Long stockID);
}
