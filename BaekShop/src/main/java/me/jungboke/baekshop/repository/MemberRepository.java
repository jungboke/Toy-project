package me.jungboke.baekshop.repository;

import me.jungboke.baekshop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
