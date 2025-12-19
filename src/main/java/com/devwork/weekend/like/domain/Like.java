package com.devwork.weekend.like.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
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
