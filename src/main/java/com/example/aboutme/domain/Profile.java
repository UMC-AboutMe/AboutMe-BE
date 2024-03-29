package com.example.aboutme.domain;

import com.example.aboutme.domain.common.BaseEntity;
import com.example.aboutme.domain.mapping.MemberProfile;
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

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private ProfileImage profileImage;
    
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<MemberProfile> memberProfileList = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<Alarm> alarmList = new ArrayList<>();

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private List<Alarm> spaceList = new ArrayList<>();

    public void setMember(Member member){
        this.member = member;
        member.getProfileList().add(this);
    }

    public void setIsDefault(Boolean isDefault){
        this.isDefault = isDefault;
    }
}
