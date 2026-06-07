package com.smartmobility.repository;

import com.smartmobility.model.Flotta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlottaRepository extends JpaRepository<Flotta, Long> {

}
