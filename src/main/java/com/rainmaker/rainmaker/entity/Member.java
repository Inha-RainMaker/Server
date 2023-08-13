package com.rainmaker.rainmaker.entity;

import com.rainmaker.rainmaker.entity.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Builder
    public Member(Major major, String password, String name, String nickName, int grade, Gender gender, Role role, String phone) {
        this.major = major;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.grade = grade;
        this.gender = gender;
        this.role = role;
        this.phone = phone;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "majorId")
    private Major major;

    // TODO 프로필 이미지 url

    @Column(length = 15, unique = true)
    private String nickName; // 이름(별명) 아이디로 사용

    @Column(length = 100)
    private String password;

    @Column(length = 15)
    private String name; // 이름(실명)

    private int grade;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 30)
    private String phone;

    /**
     * password를 암호화한다.
     * @param passwordEncoder
     */
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    /**
     * 비밀번호가 일치하는지 확인하는 메소드
     * @param passwordEncoder
     * @param checkPassword
     * @return 비밀번호가 일치하면 true
     */
    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword){
        return passwordEncoder.matches(checkPassword, getPassword());
    }

    /**
     * 회원가입할 때, USER의 권한을 부여한다.
     */
    public void addUserAuthority(){
        this.role = Role.USER;
    }
}
