package com.example.aboutme.domain;

import com.example.aboutme.domain.common.BaseEntity;
import com.example.aboutme.domain.constant.ProfileImageType;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProfileImageType type;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id")
    private Space space;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public void update(ProfileImageType type){
        this.type = type;
        this.imageUrl = null;
        this.space = null;
    }

    public void update(ProfileImageType type, String imageUrl){
        this.type = type;
        this.imageUrl = imageUrl;
        this.space = null;
    }

    public void update(ProfileImageType type, Space space){
        this.type = type;
        this.space = space;
        this.imageUrl = null;
    }

    /**
     * @return if type is USER_IMAGE return profileImageURL, else return null
     */
    public String getImageUrl(){
        if(this.type == ProfileImageType.USER_IMAGE){
            return this.imageUrl;
        }

        return null;
    }

    /**
     * @return if type is CHARACTER return characterType, else return null
     */
    public Integer getCharacterType(){
        if(this.type == ProfileImageType.CHARACTER && this.space != null){
            return space.getCharacterType();
        }

        return null;
    }
}
