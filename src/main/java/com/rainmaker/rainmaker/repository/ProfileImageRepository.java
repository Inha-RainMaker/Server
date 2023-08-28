package com.rainmaker.rainmaker.repository;

import com.rainmaker.rainmaker.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    @Query("select count(profileImage.id) > 0 from ProfileImage profileImage" +
            " where profileImage.fileName = 'default-profile-image'" +
            " and profileImage.storedFileName = 'default-profile-image'")
    boolean existsDefaultMemberProfileImage();

    @Query("select profileImage from ProfileImage profileImage" +
            " where profileImage.fileName = 'default-profile-image'" +
            " and profileImage.storedFileName = 'default-profile-image'")
    ProfileImage getDefaultMemberProfileImage();
}
