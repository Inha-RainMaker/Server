package com.rainmaker.rainmaker.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.rainmaker.rainmaker.config.S3Config;
import com.rainmaker.rainmaker.entity.ProfileImage;
import com.rainmaker.rainmaker.repository.ProfileImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@Import(S3Config.class)
@ExtendWith(MockitoExtension.class)
class S3FileServiceTest {
    @InjectMocks
    private S3FileService sut;

    @Mock
    private ProfileImageRepository profileImageRepository;
    @Mock
    private AmazonS3Client s3Client;

    @Test
    void multipartFile이_주어지면_ProfileImage_Entity를_생성하여_저장한_후_반환한다() {
        // given
        given(s3Client.putObject(any(PutObjectRequest.class))).willReturn(null);
        given(profileImageRepository.save(any(ProfileImage.class))).willReturn(createProfileImageWithId());

        // when
        sut.saveFile(
                new MockMultipartFile(
                        "file",
                        "test.txt",
                        MediaType.TEXT_PLAIN_VALUE,
                        "test".getBytes()
                )
        );

        // then
        then(profileImageRepository).should().save(any(ProfileImage.class));
    }

    private ProfileImage createProfileImage() {
        return ProfileImage.builder()
                .fileName("test")
                .storedFileName("test")
                .url("url")
                .build();
    }

    private ProfileImage createProfileImageWithId() {
        ProfileImage profileImage = createProfileImage();
        ReflectionTestUtils.setField(profileImage, "id", 1L);
        return profileImage;
    }
}