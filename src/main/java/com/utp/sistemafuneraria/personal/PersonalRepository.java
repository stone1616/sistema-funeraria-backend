package com.utp.sistemafuneraria.personal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalRepository extends JpaRepository<Personal, Integer> {

    List<Personal> findByFechaEliminacionIsNull();
}
