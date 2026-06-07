package com.smartmobility.repository;

import com.smartmobility.model.ZonaGeografica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZonaGeograficaRepository extends JpaRepository<ZonaGeografica, Long> {

    List<ZonaGeografica> findAll();
}
