package com.smartmobility.repository;

import com.smartmobility.model.Attore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttoreRepository extends JpaRepository<Attore, Long> {

}
