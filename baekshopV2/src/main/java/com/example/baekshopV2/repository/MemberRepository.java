package com.example.baekshopV2.repository;

import com.example.baekshopV2.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByLoginId(String loginId);
}
