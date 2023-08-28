package com.rainmaker.rainmaker.entity;

import com.rainmaker.rainmaker.entity.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Builder
    public Member(Major major, ProfileImage profileImage, String password, String userName, String nickName, int grade, Gender gender, Role role, String phone, Mbti mbti) {
        this.major = major;
        this.profileImage = profileImage;
        this.password = password;
        this.userName = userName;
        this.nickName = nickName;
        this.grade = grade;
        this.gender = gender;
        this.role = role;
        this.phone = phone;
        this.mbti = mbti;
        this.coin = 5; // 사용자 처음 생성 시 사용할 수 있는 코인은 5가 default
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id")
    @NotNull
    private Major major;

    @JoinColumn(name = "profile_image_id")
    @OneToOne(fetch = FetchType.LAZY)
    private ProfileImage profileImage;

    @Column(length = 15, unique = true)
    @NotNull
    private String nickName; // 이름(별명) 아이디로 사용

    @Column(length = 15)
    @NotNull
    private String userName; // 이름(실명)

    @Column(length = 100)
    @NotNull
    private String password;

    @NotNull
    private int grade;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 30)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Mbti mbti;

    @NotNull
    private int coin;

    /**
     * password를 암호화한다.
     *
     * @param passwordEncoder
     */
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    /**
     * 비밀번호가 일치하는지 확인하는 메소드
     *
     * @param passwordEncoder
     * @param checkPassword
     * @return 비밀번호가 일치하면 true
     */
    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword) {
        return passwordEncoder.matches(checkPassword, getPassword());
    }

    /**
     * 회원가입할 때, USER의 권한을 부여한다.
     */
    public void addUserAuthority() {
        this.role = Role.USER;
    }
}
