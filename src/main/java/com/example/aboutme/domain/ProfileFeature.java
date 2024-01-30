package com.example.aboutme.domain;

import com.example.aboutme.domain.common.BaseEntity;
import com.example.aboutme.domain.constant.Side;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileFeature extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Side side;

    private String profileKey;

    private String profileValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public void setProfile(Profile profile){
        this.profile = profile;
        profile.getProfileFeatureList().add(this);
    }

    public void update(String profileKey, String profileValue){
        this.profileKey = profileKey;
        this.profileValue = profileValue;
    }
}
