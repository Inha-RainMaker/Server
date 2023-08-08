package com.rainmaker.rainmaker.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_id")
    private Long id;

    private String name;

    private String department;

    @Builder
    public Major(String name, String department) {
        this.name = name;
        this.department = department;
    }
}
