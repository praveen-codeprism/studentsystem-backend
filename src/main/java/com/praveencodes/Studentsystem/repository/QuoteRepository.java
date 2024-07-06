// src/main/java/com/praveencodes/Studentsystem/repository/QuoteRepository.java
package com.praveencodes.Studentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.praveencodes.Studentsystem.model.Quote;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
