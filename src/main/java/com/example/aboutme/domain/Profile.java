package com.example.aboutme.domain;

import com.example.aboutme.domain.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer serialNumber;
    private Boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<ProfileFeature> profileFeatureList = new ArrayList<>();

    public void setMember(Member member){
        this.member = member;
        member.getProfileList().add(this);
    }

    public void setProfileFeatureList(List<ProfileFeature> profileFeatureList){
        this.profileFeatureList = profileFeatureList;

        profileFeatureList.forEach(profileFeature -> {
            profileFeature.setProfile(this);
        });
    }
}
