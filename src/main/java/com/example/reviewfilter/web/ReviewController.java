package com.example.reviewfilter.web;

import com.example.reviewfilter.model.Review;
import com.example.reviewfilter.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/reviews")
    public String listReviews() {

        return "reviewFilter";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String text, @RequestParam String rating, @RequestParam int minRating, @RequestParam String date, Model model) {
        List<Review> reviews = reviewService.filterReviews(text, rating, minRating, date);
        model.addAttribute("filteredReviews", reviews);

        return "reviewFilter";
    }
}
