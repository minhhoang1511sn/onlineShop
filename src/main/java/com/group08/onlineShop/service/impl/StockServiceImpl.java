package com.group08.onlineShop.service.impl;

import com.group08.onlineShop.dto.requestDTO.StockRequest;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.dto.responseDTO.StockResponse;
import com.group08.onlineShop.exception.BadRequestException;
import com.group08.onlineShop.exception.ResourceNotFoundException;
import com.group08.onlineShop.model.Product;
import com.group08.onlineShop.model.Stock;
import com.group08.onlineShop.repository.ProductRepo;
import com.group08.onlineShop.repository.StockRepo;
import com.group08.onlineShop.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepo stockRepo;
    private final ProductRepo productRepo;
    @Override
    public List<StockResponse> getAllStock() {
        List<Stock> stocks = stockRepo.findAll();
        return addStockResponse(stocks);
    }

    @Override
    public List<StockResponse> getStockByProduct(Long productID) {
        Optional<Product> product = productRepo.findById(productID);
        if (product.isPresent()) {
            List<Stock> stocks = stockRepo.findStocksByProduct(product.get());
            return addStockResponse(stocks);
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Can not find stock by productID = " + productID, HttpStatus.NOT_FOUND.value());
        throw new BadRequestException(apiResponse);
    }

    @Override
    public StockResponse getStockByProductAndColorAndSize(Long productID, String color, String size)
            throws ResourceNotFoundException {
        Optional<Product> product = productRepo.findById(productID);
        if (product.isPresent()) {
            Stock stock = stockRepo.findStockByProductAndColorAndSize(product.get(), color, size).orElseThrow(()
                    -> new ResourceNotFoundException("Stock", "product", product.get()));
            return new StockResponse(stock.getId(), stock.getProduct().getId(), stock.getSize(),
                    stock.getColor(), stock.getQuantity());
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Can not find stock", HttpStatus.NOT_FOUND.value());
        throw new BadRequestException(apiResponse);
    }

    @Override
    public StockResponse addProductToStock(StockRequest stockRequest) throws ResourceNotFoundException {
        Product product = productRepo.findById(stockRequest.getProduct()).
                orElseThrow(() -> new ResourceNotFoundException("Product", "productID", stockRequest.getProduct()));
        Stock newStock = new Stock(product, stockRequest.getSize(), stockRequest.getColor(),
                stockRequest.getQuantity());
        Stock createdStock = stockRepo.save(newStock);
        return new StockResponse(createdStock.getId(), createdStock.getProduct().getId(),
                createdStock.getSize(), createdStock.getColor(), createdStock.getQuantity());
    }

    @Override
    public StockResponse updateStockQuantity(Long stockID, StockRequest stockRequest) {
        Optional<Stock> stock = stockRepo.findById(stockID);
        if (stock.isPresent()) {
            Stock updateStock = stock.get();
            updateStock.setQuantity(updateStock.getQuantity());
            Stock updatedStock = stockRepo.save(updateStock);
            return new StockResponse(updatedStock.getId(), updatedStock.getProduct().getId(),
                    updatedStock.getSize(), updatedStock.getColor(), updatedStock.getQuantity());
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Cannot find stock item to update",
                HttpStatus.NOT_FOUND.value());
        throw new BadRequestException(apiResponse);
    }

    @Override
    public ApiResponse deleteStockByID(Long stockID){
        Optional<Stock> stock = stockRepo.findById(stockID);
        if (stock != null) {
            stockRepo.deleteById(stockID);
            return new ApiResponse(Boolean.TRUE, "Stock deleted successfully");
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Cannot delete stock with stockID = " + stockID
                + ". Resource not found!", HttpStatus.NOT_FOUND.value());
        throw new BadRequestException(apiResponse);
    }

    private List<StockResponse> addStockResponse(List<Stock> stocks){
        List<StockResponse> stockResponses = new ArrayList<>(stocks.size());
        for(Stock stock : stocks) {
            stockResponses.add(new StockResponse(stock.getId(), stock.getProduct().getId(), stock.getSize(),
                    stock.getColor(), stock.getQuantity()));
        }
        return stockResponses;
    }
}
