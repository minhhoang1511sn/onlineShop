package com.group08.onlineShop.service;

import com.group08.onlineShop.dto.requestDTO.ReviewRequest;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.dto.responseDTO.ReviewResponse;
import com.group08.onlineShop.exception.ResourceNotFoundException;

import java.util.List;

public interface ReviewService {
    List<ReviewResponse> getAllReview();

    List<ReviewResponse>  getReviewByUser(Long accountID);

    List<ReviewResponse> getReviewByProduct(Long productID);

    ReviewResponse postReview(ReviewRequest reviewRequest) throws ResourceNotFoundException;

    ReviewResponse updateReview(Long reviewID, ReviewRequest reviewRequest) throws ResourceNotFoundException;

    ApiResponse deleteReviewByID(Long reviewID) throws ResourceNotFoundException;
}
