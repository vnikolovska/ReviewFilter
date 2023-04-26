package com.example.reviewfilter.service;

import com.example.reviewfilter.model.Review;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {


    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Review> getAllReviews() {
        TypeReference<List<Review>> typeReference = new TypeReference<List<Review>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/static/reviews (2) (2).json");
        List<Review> reviews = null;
        try {
            reviews = objectMapper.readValue(inputStream, typeReference);


        } catch (IOException e) {
            System.out.println("Unable to list reviews: " + e.getMessage());
        }
        return reviews;
    }


    public List<Review> filterReviews(String text, String rating, int minRating, String date) {
        List<Review> reviews = getAllReviews();
        List<Review> filteredReviews = reviews.stream().filter(r -> r.getRating() >= minRating).collect(Collectors.toList());

        Comparator<Review> ratingComparator = Comparator.comparing(Review::getRating);
        Comparator<Review> dateComparator = Comparator.comparing(Review::getReviewCreatedOnDate);


        if (date.equals("Oldest First")) {
            dateComparator = dateComparator.reversed();
        }
        if (rating.equals("Highest First")) {
            ratingComparator = ratingComparator.reversed();
        }
        filteredReviews.sort(ratingComparator.thenComparing(dateComparator));

        if (text.equals("Yes")) {
            filteredReviews.sort(Comparator.comparing((Review r) -> !r.getReviewText().isEmpty()).reversed());
        }


        return filteredReviews;
    }


}

