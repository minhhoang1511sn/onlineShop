package com.group08.onlineShop.service.impl;

import com.group08.onlineShop.dto.requestDTO.ReviewRequest;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.dto.responseDTO.ReviewResponse;
import com.group08.onlineShop.exception.BadRequestException;
import com.group08.onlineShop.exception.ResourceNotFoundException;
import com.group08.onlineShop.model.Account;
import com.group08.onlineShop.model.Product;
import com.group08.onlineShop.model.Review;
import com.group08.onlineShop.repository.AccountRepo;
import com.group08.onlineShop.repository.ProductRepo;
import com.group08.onlineShop.repository.ReviewRepo;
import com.group08.onlineShop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepo reviewRepo;
    private final AccountRepo accountRepo;
    private final ProductRepo productRepo;
    @Override
    public List<ReviewResponse> getAllReview() {
        List<Review> reviews = reviewRepo.findAll();
        return returnReviewResponse(reviews);
    }

    @Override
    public List<ReviewResponse> getReviewByUser(Long accountID) {
        try {
            Account account = accountRepo.findById(accountID).orElseThrow(() -> new ResourceNotFoundException("Account", "accountID", accountID));
            List<Review> reviews = reviewRepo.findReviewByAccount(account).orElseThrow(() -> new ResourceNotFoundException("Review", "accountID", accountID));
            return returnReviewResponse(reviews);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ReviewResponse> getReviewByProduct(Long productID) {
        try {
            Product product = productRepo.findById(productID).orElseThrow(() -> new ResourceNotFoundException("Product", "productID", productID));
            List<Review> reviews = reviewRepo.findReviewByProduct(product).orElseThrow(()
                    -> new ResourceNotFoundException("Review", "productID", productID));
            return returnReviewResponse(reviews);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReviewResponse postReview(ReviewRequest reviewRequest) throws ResourceNotFoundException {
        Account account = accountRepo.findById(reviewRequest.getAccount()).orElseThrow(()
                -> new ResourceNotFoundException("Account", "accountID", reviewRequest.getAccount()));
        Product product = productRepo.findById(reviewRequest.getProduct()).orElseThrow(()
                -> new ResourceNotFoundException("Product", "productID", reviewRequest.getProduct()));
        Review newReview = new Review(account,
                product,  Instant.now(), reviewRequest.getContent(),
                reviewRequest.getRate(), reviewRequest.getLike(), reviewRequest.getDislike());
        newReview.setAccount(account);
        newReview.setReviewDislike(reviewRequest.getDislike());
        newReview.setReviewLike(reviewRequest.getLike());
        newReview.setProduct(product);
        newReview.setReviewContent(reviewRequest.getContent());
        newReview.setReviewCreateAt(Instant.now());
        reviewRepo.save(newReview);
        return new ReviewResponse(newReview.getId(), newReview.getAccount().getId(),
                newReview.getProduct().getId(), newReview.getReviewCreateAt(), newReview.getReviewContent(),
                newReview.getReviewRate(), newReview.getReviewLike(), newReview.getReviewDislike());
    }

    @Override
    public ReviewResponse updateReview(Long reviewID, ReviewRequest reviewRequest) throws ResourceNotFoundException {
//        Find Review
        Review review = reviewRepo.findById(reviewID).orElseThrow(()
                -> new ResourceNotFoundException("Review", "reviewID", reviewID));
//        Set update information
        review.setReviewContent(reviewRequest.getContent());
        review.setReviewDislike(reviewRequest.getDislike());
        review.setReviewLike(reviewRequest.getLike());
        review.setReviewRate(review.getReviewRate());
//        Save - Update review
        Review updatedReview = reviewRepo.save(review);

        return new ReviewResponse(updatedReview.getId(), updatedReview.getAccount().getId(),
                updatedReview.getProduct().getId(), updatedReview.getReviewCreateAt(), updatedReview.getReviewContent(),
                updatedReview.getReviewRate(), updatedReview.getReviewLike(), updatedReview.getReviewDislike());
    }

    @Override
    public ApiResponse deleteReviewByID(Long reviewID) throws ResourceNotFoundException {
        Optional<Review> review = reviewRepo.findById(reviewID);
        if (review != null) {
            reviewRepo.deleteById(reviewID);
            return new ApiResponse(Boolean.TRUE, "Review deleted successfully");
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Cannot delete review with reviewID = " + reviewID
                + ". Resource not found!", HttpStatus.NOT_FOUND.value());
        throw new BadRequestException(apiResponse);
    }

    private List<ReviewResponse> returnReviewResponse(List<Review> reviews){
        List<ReviewResponse> reviewResponses = new ArrayList<>(reviews.size());
        for(Review review : reviews) {
            reviewResponses.add(new ReviewResponse(review.getId(), review.getAccount().getId(),
                    review.getProduct().getId(), review.getReviewCreateAt(), review.getReviewContent(),
                    review.getReviewRate(), review.getReviewLike(), review.getReviewDislike()));
        }
        return reviewResponses;
    }

}
