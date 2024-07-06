// src/main/java/com/praveencodes/Studentsystem/controller/QuoteController.java
package com.praveencodes.Studentsystem.controller;

import com.praveencodes.Studentsystem.model.Quote;
import com.praveencodes.Studentsystem.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    @Autowired
    private QuoteService quoteService;

    @GetMapping
    public List<Quote> getAllQuotes() {
        return quoteService.getAllQuotes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quote> getQuoteById(@PathVariable Long id) {
        return quoteService.getQuoteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> addQuote(@RequestBody Quote quote) throws URISyntaxException {
        // Ensure default values are set if not provided
        if (quote.getLikes() == null) {
            quote.setLikes(0);
        }
        if (quote.getDislikes() == null) {
            quote.setDislikes(0);
        }

        Quote savedQuote = quoteService.addQuote(quote);

        // Assuming a simple message to return to frontend
        String message = "Quote added successfully with ID: " + savedQuote.getId();

        // Constructing response with URI to the new resource
        URI location = new URI("/api/quotes/" + savedQuote.getId());

        return ResponseEntity.created(location).body(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quote> updateQuote(@PathVariable Long id, @RequestBody Quote quoteDetails) {
        return quoteService.getQuoteById(id)
                .map(quote -> {
                    quote.setText(quoteDetails.getText());
                    quote.setLikes(quoteDetails.getLikes() != null ? quoteDetails.getLikes() : quote.getLikes());
                    quote.setDislikes(quoteDetails.getDislikes() != null ? quoteDetails.getDislikes() : quote.getDislikes());
                    return ResponseEntity.ok(quoteService.updateQuote(quote));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id) {
        return quoteService.getQuoteById(id)
                .map(quote -> {
                    quoteService.deleteQuote(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Quote> likeQuote(@PathVariable Long id) {
        return quoteService.getQuoteById(id)
                .map(quote -> ResponseEntity.ok(quoteService.likeQuote(id)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/dislike")
    public ResponseEntity<Quote> dislikeQuote(@PathVariable Long id) {
        return quoteService.getQuoteById(id)
                .map(quote -> {
                    quote.setDislikes(quote.getDislikes() != null ? quote.getDislikes() + 1 : 1);
                    return ResponseEntity.ok(quoteService.dislikeQuote(id));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/random")
    public ResponseEntity<Quote> getRandomQuote() {
        Quote randomQuote = quoteService.getRandomQuote();
        return ResponseEntity.ok(randomQuote);
    }


}
