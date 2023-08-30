package com.rainmaker.rainmaker.repository;

import com.rainmaker.rainmaker.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MajorRepository extends JpaRepository<Major, Long> {
    public Optional<Major> findByName(String name);
}
