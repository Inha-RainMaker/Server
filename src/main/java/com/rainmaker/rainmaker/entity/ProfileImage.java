package com.rainmaker.rainmaker.entity;

import com.rainmaker.rainmaker.entity.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_img_id")
    private Long id;

    @NotNull
    private String fileName;

    @NotNull
    private String storedFileName;

    @NotNull
    private String url;


    @Builder
    public ProfileImage(String fileName, String storedFileName, String url) {
        this.fileName = fileName;
        this.storedFileName = storedFileName;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfileImage)) return false;
        ProfileImage that = (ProfileImage) o;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}