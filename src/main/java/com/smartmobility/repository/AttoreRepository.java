package com.smartmobility.repository;

import com.smartmobility.model.Attore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttoreRepository extends JpaRepository<Attore, Long> {
    Optional<Attore> findByEmail(String email);
    boolean existsByEmail(String email);
}
