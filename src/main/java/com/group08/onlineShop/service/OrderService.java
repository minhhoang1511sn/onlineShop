package com.group08.onlineShop.service;

import com.group08.onlineShop.dto.requestDTO.OrderRequest;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.dto.responseDTO.OrderResponse;
import com.group08.onlineShop.exception.ResourceNotFoundException;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getAllOrder();
    List<OrderResponse> getOrderByAccount(Long accountID) throws ResourceNotFoundException;
    List<OrderResponse> getOrderByStatusOfAccount(Long accountID, Long statusID) throws ResourceNotFoundException;
    OrderResponse getOrderByID(Long orderID) throws ResourceNotFoundException;
    OrderResponse creatOrder(OrderRequest orderRequest) throws ResourceNotFoundException;
    OrderResponse updateOrder(Long orderID, OrderRequest orderRequest) throws ResourceNotFoundException;
    ApiResponse deleteOrder(Long orderID) throws ResourceNotFoundException;
}
