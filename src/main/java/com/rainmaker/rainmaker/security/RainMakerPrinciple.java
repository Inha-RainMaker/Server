package com.rainmaker.rainmaker.security;

import com.rainmaker.rainmaker.dto.member.MemberDto;
import com.rainmaker.rainmaker.entity.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RainMakerPrinciple implements UserDetails {
    private MemberDto memberDto;

    public static RainMakerPrinciple of(MemberDto memberDto) {
        return new RainMakerPrinciple(memberDto);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roleTypes = Set.of(Role.values());

        return roleTypes.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
    }

    /**
     * Principle 에 저장된 멤버의 id(PK) 를 조회
     *
     * @return Principle 에 저장된 멤버의 id(PK)
     */
    public Long getMemberId() {
        return memberDto.getId();
    }

    @Override
    public String getPassword() {
        return memberDto.getPassword();
    }

    @Override
    public String getUsername() {
        return memberDto.getNickName(); // TODO 닉네임, 실명 중 선택 필요
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
