package com.example.aboutme.domain.mapping;

import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Space;
import com.example.aboutme.domain.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSpace extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id")
    private Space space;

    private boolean favorite;

    // 즐겨찾기 토글 메서드
    public void toggleFavorite() {
        this.favorite = !this.favorite;
    }
}
