package com.example.reviewfilter;

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

    public List<Review> filterReviews(String text, String rating, int minRating) {
        List<Review> reviews = getAllReviews();
        List<Review> filteredReviews;

        if (text.equals("Yes")) {
            if (rating.equals("Highest First")) {

                filteredReviews = reviews.stream().filter(r -> r.getRating() >= minRating)
                        .sorted(Comparator.comparing(Review::getRating).reversed())
                        .collect(Collectors.toList());
                filteredReviews.sort(Comparator.comparing((Review r) -> !r.getReviewText().isEmpty())
                        .reversed());

            } else {

                filteredReviews = reviews.stream().filter(r -> r.getRating() >= minRating)
                        .sorted(Comparator.comparing(Review::getRating))
                        .collect(Collectors.toList());
                filteredReviews.sort(Comparator.comparing((Review r) -> !r.getReviewText().isEmpty())
                        .reversed());
            }

        } else {
            if (rating.equals("Highest First")) {

                filteredReviews = reviews.stream().filter(r -> r.getRating() >= minRating)
                        .sorted(Comparator.comparing(Review::getRating).reversed())
                        .collect(Collectors.toList());


            } else {

                filteredReviews = reviews.stream().filter(r -> r.getRating() >= minRating)
                        .sorted(Comparator.comparing(Review::getRating))
                        .collect(Collectors.toList());

            }

        }

        return filteredReviews;

    }
}

