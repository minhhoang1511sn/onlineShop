package com.group08.onlineShop.service.impl;

import com.group08.onlineShop.dto.requestDTO.CartItemRequest;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.dto.responseDTO.CartItemResponse;
import com.group08.onlineShop.exception.BadRequestException;
import com.group08.onlineShop.exception.ResourceNotFoundException;
import com.group08.onlineShop.model.*;
import com.group08.onlineShop.repository.*;
import com.group08.onlineShop.service.CartItemService;
import com.group08.onlineShop.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepo cartItemRepo;
    private final ProductRepo productRepo;
    private final AccountRepo accountRepo;
    private final StockRepo stockRepo;
    private final ProductImageRepo productImageRepo;
    @Override
    public List<CartItemResponse> getAllCartItems() throws ResourceNotFoundException {
        List<CartItem> cartItems = cartItemRepo.findAll();
        return addCartItemsResponse(cartItems);
    }

    @Override
    public CartItemResponse getCartItemByID(Long cartItemID) throws ResourceNotFoundException {
         CartItem cartItem = cartItemRepo.findById(cartItemID).orElseThrow(()
                -> new ResourceNotFoundException("CartItem", "cartItemID", cartItemID));
         Stock stock = stockRepo.findStockByProductAndColorAndSize(cartItem.getProduct(), cartItem.getColor(),
                 cartItem.getSize()).orElseThrow(() -> new ResourceNotFoundException("Stock", "properties",
                 cartItem.getProduct().getId()));
        ProductImage productImage = productImageRepo.getProductImageByProduct(cartItem.getProduct().getId());
         if (cartItem != null) {
             return new CartItemResponse(cartItem.getId(), cartItem.getProduct().getId(),
                     cartItem.getQuantity(), cartItem.getTotalPrice(),
                     cartItem.getSize(), cartItem.getColor(), cartItem.getAccount(), stock, null);
         }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE,
                "Can not find cart item by accountID = " + cartItemID, HttpStatus.NOT_FOUND.value());
        throw new BadRequestException(apiResponse);
    }

    @Override
    public List<CartItemResponse> getCartItemsByAccount(Long accountID) throws ResourceNotFoundException {
        Account account = accountRepo.findById(accountID).orElseThrow(()
                -> new ResourceNotFoundException("Account", "accountID", accountID));

        if (account != null) {
            List<CartItem> cartItems = cartItemRepo.findCartItemsByAccount(account);
            return addCartItemsResponse(cartItems);
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Can not find cart item by accountID = " + accountID, HttpStatus.NOT_FOUND.value());
        throw new BadRequestException(apiResponse);
    }


    @Override
    public CartItemResponse addCartItem(CartItemRequest cartItemRequest) throws ResourceNotFoundException {
        Account account= accountRepo.findById(cartItemRequest.getAccount()).orElseThrow(()
                -> new ResourceNotFoundException("Account", "accountID", cartItemRequest.getAccount()));;
        Product product = productRepo.findById(cartItemRequest.getProduct()).orElseThrow(()
                -> new ResourceNotFoundException("Product", "productID", cartItemRequest.getProduct()));
        Stock stock = stockRepo.findStockByProductAndColorAndSize(product, cartItemRequest.getColor(),
                cartItemRequest.getSize()).orElseThrow(() -> new ResourceNotFoundException("Stock", "properties",
                cartItemRequest.getProduct()));
        if (account != null && product != null) {
            CartItem cartItem = new CartItem(product,
                    cartItemRequest.getQuantity(), cartItemRequest.getTotalPrice(),
                    cartItemRequest.getSize(), cartItemRequest.getColor(), account);
            CartItem newCartItem = cartItemRepo.save(cartItem);
            return new CartItemResponse(newCartItem.getId(), newCartItem.getProduct().getId(),
                    newCartItem.getQuantity(), newCartItem.getTotalPrice(),
                    newCartItem.getSize(), newCartItem.getColor(), newCartItem.getAccount(), stock, null);
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Can not create cart item", HttpStatus.BAD_REQUEST.value());
        throw new BadRequestException(apiResponse);
    }

    @Override
    public CartItemResponse updateCartItem(Long cartItemID, CartItemRequest cartItemRequest) throws ResourceNotFoundException {
        CartItem cartItem = cartItemRepo.findById(cartItemID).orElseThrow(() ->
                new ResourceNotFoundException("CartItem", "cartItemID", cartItemID));
        Account account =accountRepo.findById(cartItemRequest.getAccount()).orElseThrow(()
                -> new ResourceNotFoundException("CartItems", "cartID", cartItemRequest.getAccount()));
        Product product = productRepo.findById(cartItemRequest.getProduct()).orElseThrow(()
                -> new ResourceNotFoundException("Product", "productID", cartItemRequest.getProduct()));
        Stock stock = stockRepo.findStockByProductAndColorAndSize(cartItem.getProduct(), cartItem.getColor(),
                cartItem.getSize()).orElseThrow(() -> new ResourceNotFoundException("Stock", "properties",
                cartItem.getProduct().getId()));
        if(cartItem != null) {
            cartItem.setAccount(account);
            cartItem.setColor(cartItemRequest.getColor());
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setProduct(product);
            cartItem.setSize(cartItemRequest.getSize());
            cartItem.setTotalPrice(cartItemRequest.getTotalPrice());

            CartItem updatedCartItem = cartItemRepo.save(cartItem);
            return new CartItemResponse(updatedCartItem.getId(), updatedCartItem.getProduct().getId(),
                    updatedCartItem.getQuantity(), updatedCartItem.getTotalPrice(),
                    updatedCartItem.getSize(), updatedCartItem.getColor(), updatedCartItem.getAccount(), stock, null);
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Can not create cart item", HttpStatus.BAD_REQUEST.value());
        throw new BadRequestException(apiResponse);
    }

    @Override
    public ApiResponse deleteCartItemByCartItemID(Long cartItemID) throws ResourceNotFoundException {
        CartItem cartItem = cartItemRepo.findById(cartItemID).orElseThrow(() ->
                new ResourceNotFoundException("CartItem", "cartItemID", cartItemID));
        if (cartItem != null) {
            cartItemRepo.deleteById(cartItemID);
            return new ApiResponse(Boolean.TRUE, "CartItem deleted successfully");
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Can not delete cart item", HttpStatus.BAD_REQUEST.value());
        throw new BadRequestException(apiResponse);
    }

    private List<CartItemResponse> addCartItemsResponse(List<CartItem> cartItems) throws ResourceNotFoundException {
        List<CartItemResponse> cartItemResponses = new ArrayList<>(cartItems.size());
        for(CartItem cartItem : cartItems) {
            Stock stock = stockRepo.findStockByProductAndColorAndSize(cartItem.getProduct(), cartItem.getColor(),
                    cartItem.getSize()).orElseThrow(() -> new ResourceNotFoundException("Stock", "properties",
                    cartItem.getProduct().getId()));
            cartItemResponses.add(new CartItemResponse(cartItem.getId(), cartItem.getProduct().getId(),
                    cartItem.getQuantity(), cartItem.getTotalPrice(), cartItem.getSize(),
                    cartItem.getColor(), cartItem.getAccount(), stock, null));
        }
        return cartItemResponses;
    }
}
