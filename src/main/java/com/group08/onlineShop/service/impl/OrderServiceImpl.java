package com.group08.onlineShop.service.impl;

import com.group08.onlineShop.dto.requestDTO.OrderRequest;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.dto.responseDTO.OrderResponse;
import com.group08.onlineShop.exception.BadRequestException;
import com.group08.onlineShop.exception.ResourceNotFoundException;
import com.group08.onlineShop.model.Account;
import com.group08.onlineShop.model.Address;
import com.group08.onlineShop.model.Order;
import com.group08.onlineShop.model.Status;
import com.group08.onlineShop.repository.AccountRepo;
import com.group08.onlineShop.repository.OrderRepo;
import com.group08.onlineShop.repository.StatusRepo;
import com.group08.onlineShop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final AccountRepo accountRepo;
    private final StatusRepo statusRepo;
    @Override
    public List<OrderResponse> getAllOrder() {
        List<Order> orders = orderRepo.findAll();
        return addOrderResponse(orders);
    }

    @Override
    public List<OrderResponse> getOrderByAccount(Long accountID) throws ResourceNotFoundException {
        Account account = accountRepo.findById(accountID).orElseThrow(()
                -> new ResourceNotFoundException("Account", "accountID", accountID));
        List<Order> orders = orderRepo.findOrdersByAccount(account);
        return addOrderResponse(orders);
    }

    @Override
    public List<OrderResponse> getOrderByStatusOfAccount(Long accountID, Long statusID) throws ResourceNotFoundException {
        Account account = accountRepo.findById(accountID).orElseThrow(()
                -> new ResourceNotFoundException("Account", "accountID", accountID));
        Status status = statusRepo.findById(statusID).orElseThrow(()
                -> new ResourceNotFoundException("Status", "statusID", statusID));
        List<Order> orders = orderRepo.findOrdersByAccountAndStatus(account, status);
        return addOrderResponse(orders);
    }

    @Override
    public OrderResponse getOrderByID(Long orderID) throws ResourceNotFoundException {
        Order order = orderRepo.findById(orderID).orElseThrow(()
                -> new ResourceNotFoundException("Order", "orderID", orderID));
        return new OrderResponse(order.getId(), order.getAccount(),
                order.getCreateAt(), order.getUpdateAt(),
                order.getReceiverName(), order.getReceiverPhoneNumber(),
                order.getAddress(), order.getDeliveryChargers(), order.getTotalPrice(),
                order.getStatus());
    }

    @Override
    public OrderResponse creatOrder(OrderRequest orderRequest) throws ResourceNotFoundException {
        Account account = accountRepo.findById(orderRequest.getAccount()).orElseThrow(()
                -> new ResourceNotFoundException("Account", "accountID", orderRequest.getAccount()));
        Status status = statusRepo.findById(orderRequest.getStatus()).orElseThrow(()
                -> new ResourceNotFoundException("Status", "statusID", orderRequest.getStatus()));
        var order = Order.builder().account(account)
                .createAt(orderRequest.getCreateAt())
                .updateAt(null)
                .receiverName(orderRequest.getReceiverName())
                .receiverPhoneNumber(orderRequest.getReceiverPhoneNumber())
                .address(orderRequest.getAddress())
                .deliveryChargers(orderRequest.getDeliveryChargers())
                .totalPrice(orderRequest.getTotalPrice())
                .status(status).build();
        orderRepo.save(order);
        return new OrderResponse(order.getId(), order.getAccount(),
                order.getCreateAt(), order.getUpdateAt(),
                order.getReceiverName(), order.getReceiverPhoneNumber(),
                order.getAddress(), order.getDeliveryChargers(), order.getTotalPrice(),
                order.getStatus());
    }

    @Override
    public OrderResponse updateOrder(Long orderID, OrderRequest orderRequest) throws ResourceNotFoundException {
        Account account = accountRepo.findById(orderRequest.getAccount()).orElseThrow(()
                -> new ResourceNotFoundException("Account", "accountID", orderRequest.getAccount()));
        Status status = statusRepo.findById(orderRequest.getStatus()).orElseThrow(()
                -> new ResourceNotFoundException("Status", "statusID", orderRequest.getStatus()));
        Order order = orderRepo.findById(orderID).orElseThrow(()
                -> new ResourceNotFoundException("Order", "orderID", orderID));
        if (order != null) {
            order.setAccount(account);
            order.setUpdateAt(Instant.now());
            order.setReceiverName(orderRequest.getReceiverName());
            order.setReceiverPhoneNumber(order.getReceiverPhoneNumber());
            order.setAddress(orderRequest.getAddress());
            order.setDeliveryChargers(orderRequest.getDeliveryChargers());
            order.setTotalPrice(orderRequest.getTotalPrice());
            order.setStatus(status);
            Order updatedOrder = orderRepo.save(order);
            return new OrderResponse(updatedOrder.getId(), updatedOrder.getAccount(),
                    updatedOrder.getCreateAt(), updatedOrder.getUpdateAt(),
                    updatedOrder.getReceiverName(), updatedOrder.getReceiverPhoneNumber(),
                    updatedOrder.getAddress(), updatedOrder.getDeliveryChargers(),
                    updatedOrder.getTotalPrice(), updatedOrder.getStatus());
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Can not update order", HttpStatus.BAD_REQUEST.value());
        throw new BadRequestException(apiResponse);
    }

    @Override
    public ApiResponse deleteOrder(Long orderID) throws ResourceNotFoundException {
        Order order = orderRepo.findById(orderID).orElseThrow(()
                -> new ResourceNotFoundException("Order", "orderID", orderID));
        if (order != null) {
            orderRepo.deleteById(orderID);
            return new ApiResponse(Boolean.TRUE, "Order deleted successfully");
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Can not delete order", HttpStatus.BAD_REQUEST.value());
        throw new BadRequestException(apiResponse);
    }
    private List<OrderResponse> addOrderResponse(List<Order> orders){
        List<OrderResponse> orderResponses = new ArrayList<>(orders.size());
        for(Order order : orders) {
            orderResponses.add(new OrderResponse(order.getId(),
                    order.getAccount(), order.getCreateAt(), order.getUpdateAt(),
                    order.getReceiverName(), order.getReceiverPhoneNumber(),
                    order.getAddress(), order.getDeliveryChargers(), order.getTotalPrice(),
                    order.getStatus()));
        }
        return orderResponses;
    }
}
