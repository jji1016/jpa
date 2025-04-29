package com.jongin.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BaseEntity {
    @CreatedDate //현재 시간 자동으로 주입
    @Column(updatable = false) //regDate는 한 번 들어가면 수정되지 않도록 설정.
    public
    LocalDateTime regDate;

    @LastModifiedDate //마지막 수정한 날짜 들어감
    public
    LocalDateTime modifyDate;
}
