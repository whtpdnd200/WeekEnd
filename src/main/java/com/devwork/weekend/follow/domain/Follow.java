package com.devwork.weekend.follow.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@IdClass(FollowId.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`follow`")
@Entity
public class Follow {

    @Id
    private long userId;
    @Id
    private long followId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
