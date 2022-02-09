package me.jungboke.baekshop.repository.query.member;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.jungboke.baekshop.domain.Member;
import me.jungboke.baekshop.domain.Order;

import java.util.List;

@Data
public class MemberDTO {
    private String name;
    private String loginId;

    @QueryProjection
    public MemberDTO(String name, String loginId) {
        this.name = name;
        this.loginId = loginId;
    }
}
