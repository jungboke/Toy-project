package me.jungboke.baekshop.repository.query.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.jungboke.baekshop.domain.Member;
import me.jungboke.baekshop.domain.QMember;
import me.jungboke.baekshop.domain.QOrder;
import me.jungboke.baekshop.repository.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static me.jungboke.baekshop.domain.QMember.*;
import static me.jungboke.baekshop.domain.QOrder.*;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public List<MemberDTO> findMemberDTO() {
        return queryFactory
                .select(new QMemberDTO(member.name, member.loginId))
                .from(member)
                .fetch();
    }
}
