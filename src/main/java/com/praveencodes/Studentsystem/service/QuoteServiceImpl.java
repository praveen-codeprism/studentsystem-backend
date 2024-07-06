package com.praveencodes.Studentsystem.service;

import com.praveencodes.Studentsystem.model.Quote;
import com.praveencodes.Studentsystem.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class QuoteServiceImpl implements QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

    @Override
    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }

    @Override
    public Optional<Quote> getQuoteById(Long id) {
        return quoteRepository.findById(id);
    }

    @Override
    public Quote addQuote(Quote quote) {
        return quoteRepository.save(quote);
    }

    @Override
    public Quote updateQuote(Quote quote) {
        return quoteRepository.save(quote);
    }

    @Override
    public void deleteQuote(Long id) {
        quoteRepository.deleteById(id);
    }

    @Override
    public Quote likeQuote(Long id) {
        Optional<Quote> quote = quoteRepository.findById(id);
        if (quote.isPresent()) {
            Quote q = quote.get();
            q.setLikes(q.getLikes() + 1);
            return quoteRepository.save(q);
        }
        return null;
    }

    @Override
    public Quote dislikeQuote(Long id) {
        Optional<Quote> quote = quoteRepository.findById(id);
        if (quote.isPresent()) {
            Quote q = quote.get();
            q.setDislikes(q.getDislikes() + 1);
            return quoteRepository.save(q);
        }
        return null;
    }

    @Override
    public Quote getRandomQuote() {
        List<Quote> allQuotes = quoteRepository.findAll();
        if (allQuotes.isEmpty()) {
            return null; // Handle empty case appropriately
        }
        Random random = new Random();
        int randomIndex = random.nextInt(allQuotes.size());
        return allQuotes.get(randomIndex);
    }
}
