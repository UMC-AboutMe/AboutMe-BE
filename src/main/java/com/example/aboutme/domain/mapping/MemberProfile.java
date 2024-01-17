package com.example.aboutme.domain.mapping;

import com.example.aboutme.domain.Member;
import com.example.aboutme.domain.Profile;
import com.example.aboutme.domain.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberProfile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean favorite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
