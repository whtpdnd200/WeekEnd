package com.devwork.weekend.like.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(LikeId.class)
@Entity
@Table(name = "`post_like`")
public class Like {

    @Id
    private long postId;

    @Id
    private long userId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
