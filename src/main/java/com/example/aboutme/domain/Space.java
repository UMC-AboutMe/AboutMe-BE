package com.example.aboutme.domain;

import com.example.aboutme.domain.common.BaseEntity;
import com.example.aboutme.domain.constant.Mood;
import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Space extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private Integer characterType;

    private Integer roomType;

    @Enumerated(EnumType.STRING)
    private Mood mood;

    private String musicUrl;

    private String statusMessage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private List<SpaceImage> spaceImageList = new ArrayList<>();

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private List<Plan> planList = new ArrayList<>();

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    public void updateCharacterType(Integer characterType){
        this.characterType = characterType;
    }

    public void updateRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    public void updateMood(Mood mood) {
        this.mood = mood;
    }

    public void updateMusirUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public void updateStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
  
    public void addPlan(Plan newPlan) {
        planList.add(newPlan);
    }
}
