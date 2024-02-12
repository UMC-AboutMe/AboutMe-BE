package com.example.aboutme.domain;

import com.example.aboutme.domain.common.BaseEntity;
import com.example.aboutme.domain.constant.Mood;
import lombok.*;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id")
    private Space space;
}
