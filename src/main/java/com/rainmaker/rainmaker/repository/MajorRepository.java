package com.rainmaker.rainmaker.repository;

import com.rainmaker.rainmaker.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorRepository extends JpaRepository<Major, Long> {
    public Major findByName(String name);
}
