package com.group08.onlineShop.service.impl;

import com.group08.onlineShop.dto.requestDTO.OrderItemRequest;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.dto.responseDTO.OrderItemResponse;
import com.group08.onlineShop.exception.BadRequestException;
import com.group08.onlineShop.exception.ResourceNotFoundException;
import com.group08.onlineShop.model.Order;
import com.group08.onlineShop.model.OrderItem;
import com.group08.onlineShop.model.Product;
import com.group08.onlineShop.repository.OrderItemRepo;
import com.group08.onlineShop.repository.OrderRepo;
import com.group08.onlineShop.repository.ProductRepo;
import com.group08.onlineShop.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepo orderItemRepo;
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    @Override
    public List<OrderItemResponse> getAllOrderItem() {
        List<OrderItem> orderItems = orderItemRepo.findAll();
        return addOrderItemResponse(orderItems);
    }

    @Override
    public List<OrderItemResponse> getOrderItemByOrder(Long orderID) throws ResourceNotFoundException {
        Order order = orderRepo.findById(orderID).orElseThrow(()
        -> new ResourceNotFoundException("Order", "orderID", orderID));
        List<OrderItem> orderItems = orderItemRepo.findOrderItemByOrder(order);
        return addOrderItemResponse(orderItems);
    }

    @Override
    public OrderItemResponse getOrderItemByID(Long orderItemID) throws ResourceNotFoundException {
        OrderItem orderItem = orderItemRepo.findById(orderItemID).orElseThrow(()
                -> new ResourceNotFoundException("OrderItem", "orderItemID", orderItemID));
        return new OrderItemResponse(orderItem.getId(), orderItem.getProduct(),
                orderItem.getQuantity(), orderItem.getTotalPrice(), orderItem.getSize(),
                orderItem.getColor(), orderItem.getOrder());
    }

    @Override
    public OrderItemResponse createOrderItem(OrderItemRequest orderItemRequest) throws ResourceNotFoundException {
        Order order = orderRepo.findById(orderItemRequest.getOrder()).orElseThrow(()
                -> new ResourceNotFoundException("Order", "orderID", orderItemRequest.getOrder()));
        Product product = productRepo.findById(orderItemRequest.getProduct()).orElseThrow(()
                -> new ResourceNotFoundException("Product", "product", orderItemRequest.getProduct()));
        var orderItem = OrderItem.builder().product(product)
                .quantity(orderItemRequest.getQuantity())
                .totalPrice(orderItemRequest.getTotalPrice())
                .size(orderItemRequest.getSize())
                .color(orderItemRequest.getColor())
                .order(order).build();
        orderItemRepo.save(orderItem);
        return new OrderItemResponse(orderItem.getId(), orderItem.getProduct(),
                orderItem.getQuantity(), orderItem.getTotalPrice(), orderItem.getSize(),
                orderItem.getColor(), orderItem.getOrder());
    }

    @Override
    public OrderItemResponse updateOrderItem(Long orderItemID, OrderItemRequest orderItemRequest) throws ResourceNotFoundException {
        Order order = orderRepo.findById(orderItemRequest.getOrder()).orElseThrow(()
                -> new ResourceNotFoundException("Order", "orderID", orderItemRequest.getOrder()));
        Product product = productRepo.findById(orderItemRequest.getProduct()).orElseThrow(()
                -> new ResourceNotFoundException("Product", "product", orderItemRequest.getProduct()));
        OrderItem orderItem = orderItemRepo.findById(orderItemID).orElseThrow(()
                -> new ResourceNotFoundException("OrderItem", "orderItemID", orderItemID));
        if (orderItem != null){
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setTotalPrice(orderItemRequest.getTotalPrice());
            orderItem.setSize(orderItemRequest.getSize());
            orderItem.setColor(orderItemRequest.getColor());
            orderItem.setOrder(order);
            OrderItem updatedOrderItem = orderItemRepo.save(orderItem);
            return new OrderItemResponse(updatedOrderItem.getId(), updatedOrderItem.getProduct(),
                    updatedOrderItem.getQuantity(), updatedOrderItem.getTotalPrice(), updatedOrderItem.getSize(),
                    updatedOrderItem.getColor(), updatedOrderItem.getOrder());
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Can not update order item", HttpStatus.BAD_REQUEST.value());
        throw new BadRequestException(apiResponse);
    }

    @Override
    public ApiResponse deleteOrderItem(Long orderItemID) throws ResourceNotFoundException {
        OrderItem orderItem = orderItemRepo.findById(orderItemID).orElseThrow(()
                -> new ResourceNotFoundException("OrderItem", "orderItemID", orderItemID));
        if (orderItem != null){
            orderItemRepo.deleteById(orderItemID);
            return new ApiResponse(Boolean.TRUE, "Order item deleted successfully");
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Can not delete order item", HttpStatus.BAD_REQUEST.value());
        throw new BadRequestException(apiResponse);
    }

    private List<OrderItemResponse> addOrderItemResponse(List<OrderItem> orderItems){
        List<OrderItemResponse> orderItemResponses = new ArrayList<>(orderItems.size());
        for(OrderItem orderItem : orderItems) {
            orderItemResponses.add(new OrderItemResponse(orderItem.getId(), orderItem.getProduct(),
                    orderItem.getQuantity(), orderItem.getTotalPrice(), orderItem.getSize(),
                    orderItem.getColor(), orderItem.getOrder()));
        }
        return orderItemResponses;
    }
}
