package com.example.aboutme.domain;

import com.example.aboutme.domain.common.BaseEntity;
import com.example.aboutme.domain.constant.Social;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Social social;

    private String email;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Profile> profileList = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Space space;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    private List<GuestBook> guestBookList = new ArrayList<>();
}
