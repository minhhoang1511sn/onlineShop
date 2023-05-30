package com.group08.onlineShop.service;

import com.group08.onlineShop.dto.requestDTO.OrderItemRequest;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.dto.responseDTO.OrderItemResponse;
import com.group08.onlineShop.exception.ResourceNotFoundException;

import java.util.List;

public interface OrderItemService {
    List<OrderItemResponse> getAllOrderItem();
    List<OrderItemResponse> getOrderItemByOrder(Long orderID) throws ResourceNotFoundException;
    OrderItemResponse getOrderItemByID(Long orderItemID) throws ResourceNotFoundException;
    OrderItemResponse createOrderItem(OrderItemRequest orderItemRequest) throws ResourceNotFoundException;
    OrderItemResponse updateOrderItem(Long orderItemID, OrderItemRequest orderItemRequest) throws ResourceNotFoundException;
    ApiResponse deleteOrderItem(Long orderItemID) throws ResourceNotFoundException;
}
