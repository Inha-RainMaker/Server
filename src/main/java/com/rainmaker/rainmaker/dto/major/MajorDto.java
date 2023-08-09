package com.rainmaker.rainmaker.dto.major;

import com.rainmaker.rainmaker.entity.Major;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MajorDto {
    private String name;
    private String department;

    public MajorDto(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public static MajorDto from(Major major) {
        return new MajorDto(major.getName(), major.getDepartment());
    }

    public static MajorDto of(String name, String department) {
        return new MajorDto(name, department);
    }
}
