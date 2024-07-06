package com.praveencodes.Studentsystem.service;

import com.praveencodes.Studentsystem.model.Quote;

import java.util.List;
import java.util.Optional;

public interface QuoteService {
    List<Quote> getAllQuotes();
    Optional<Quote> getQuoteById(Long id);
    Quote addQuote(Quote quote);
    Quote updateQuote(Quote quote);
    void deleteQuote(Long id);
    Quote likeQuote(Long id);
    Quote dislikeQuote(Long id);

    Quote getRandomQuote(); // New method declaration for fetching a random quote
}
