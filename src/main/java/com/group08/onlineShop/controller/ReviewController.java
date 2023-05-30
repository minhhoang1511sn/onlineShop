package com.group08.onlineShop.controller;

import com.group08.onlineShop.dto.requestDTO.ReviewRequest;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.exception.ResourceNotFoundException;
import com.group08.onlineShop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/review") @Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:3000")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/get-reviews")
    public ResponseEntity<?> getAllReviews(){
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), reviewService.getAllReview()));
    }

    @GetMapping("/get-reviews/account")
    public ResponseEntity<?> getReviewByAccount(@RequestParam(name = "accountID") Long accountID){
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), reviewService.getReviewByUser(accountID)));
    }

    @GetMapping("/get-reviews/product")
    public ResponseEntity<?> getReviewByProduct(@RequestParam(name = "productID") Long productID){
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), reviewService.getReviewByProduct(productID)));
    }

    @PostMapping("/post-review")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> postReview(@RequestBody ReviewRequest reviewRequest) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), reviewService.postReview(reviewRequest)));
    }

    @PutMapping("/update-review")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateReview(
            @RequestParam(name = "reviewID") Long reviewID,
            @RequestBody ReviewRequest reviewRequest) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), reviewService.updateReview(reviewID, reviewRequest)));
    }

    @DeleteMapping("/delete-review")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteReview(
            @RequestParam(name = "reviewID") Long reviewID) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), reviewService.deleteReviewByID(reviewID)));
    }
}
