package com.rainmaker.rainmaker.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.rainmaker.rainmaker.entity.ProfileImage;
import com.rainmaker.rainmaker.exception.file.MultipartFileNotReadableException;
import com.rainmaker.rainmaker.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class S3FileService {
    private final AmazonS3Client s3Client;
    private final ProfileImageRepository profileImageRepository;

    private static final String DIR_PATH = "member/";
    private static final String DEFAULT_PROFILE_IMAGE_NAME = "default-profile-image.png";

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    /**
     * S3 bucket에서 회원의 기본 프로필 이미지를 조회한 후 DB에 저장한다.
     */
    @Transactional
    public ProfileImage saveMemberDefaultProfileImage() {
        String defaultImageUrl = s3Client.getResourceUrl(bucketName, DIR_PATH + DEFAULT_PROFILE_IMAGE_NAME);
        return profileImageRepository.saveAndFlush(
                ProfileImage.builder()
                        .fileName(DEFAULT_PROFILE_IMAGE_NAME)
                        .storedFileName(DIR_PATH + DEFAULT_PROFILE_IMAGE_NAME)
                        .url(defaultImageUrl)
                        .build()
        );
    }

    /**
     * 전달받은 파일을 S3 bucket에 저장한 후 이를 바탕으로 ProfileImage entity를 생성하여 DB에 저장한 후 반환한다.
     *
     * @param multipartFile S3 bucket과 DB에 저장할 파일
     * @return 저장된 ProfileImage entity
     * @throws MultipartFileNotReadableException 전달받은 파일을 읽는 데 문제가 발생한 경우. (multipartFile.getInputStream()에서 IOException이 던져진 경우)
     */
    @Transactional
    public ProfileImage saveFile(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null) {
            originalFilename = "";
        }
        String storeFileName = createStoreFileName(originalFilename);
        ObjectMetadata objectMetadata = createMetadataWithContentInfo(multipartFile);

        try {
            InputStream inputStream = multipartFile.getInputStream();
            s3Client.putObject(
                    new PutObjectRequest(
                            bucketName,
                            storeFileName,
                            inputStream,
                            objectMetadata
                    ).withCannedAcl(CannedAccessControlList.PublicRead)
            );
            String storedFileUrl = s3Client.getResourceUrl(bucketName, storeFileName);

            return profileImageRepository.save(
                    ProfileImage.builder()
                            .fileName(originalFilename)
                            .storedFileName(storeFileName)
                            .url(storedFileUrl)
                            .build()
            );
        } catch (IOException e) {
            log.error("FileUploadService.saveFile() ex={} fileName={}", e, multipartFile.getOriginalFilename());
            throw new MultipartFileNotReadableException();
        }
    }


    /**
     * 전달받은 profileImage entity를 S3 bucket과 DB에서 삭제한다.
     *
     * @param profileImage 삭제할 profileImage entity
     */
    @Transactional
    public void deleteFile(ProfileImage profileImage) {
        s3Client.deleteObject(bucketName, profileImage.getStoredFileName());
        profileImageRepository.delete(profileImage);
    }

    private String createStoreFileName(String originalFilename) {
        // Extract file extension
        int pos = originalFilename.lastIndexOf(".");
        String extension = originalFilename.substring(pos + 1);
        String uuid = UUID.randomUUID().toString();
        return DIR_PATH + uuid + "." + extension;
    }

    private ObjectMetadata createMetadataWithContentInfo(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        return objectMetadata;
    }
}
