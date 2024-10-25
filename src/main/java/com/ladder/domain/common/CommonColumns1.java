package com.ladder.domain.common;

import com.ladder.util.CommonUtil;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class CommonColumns1 {

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FIRST_SAVE_DT", updatable = false, nullable = false)
    private LocalDateTime firstSaveDt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATE_DT")
    private LocalDateTime lastUpdateDt;

    @Column(name = "FIRST_SAVE_USER", updatable = false, nullable = false)
    private String firstSaveUser;

    @Column(name = "LAST_UPDATE_USER")
    private String lastUpdateUser;

    @Column(name = "DEL_YN")
    private int delYn;

    public void remove(){
        this.lastUpdateDt = LocalDateTime.now();
        this.lastUpdateUser = CommonUtil.getLadderAccountId();
        this.delYn = 0;
    }

    @PrePersist
    public void prePersist() {
        this.firstSaveDt = LocalDateTime.now();
        this.firstSaveUser = CommonUtil.getLadderAccountId();
        this.delYn = 1;
    }

    @PreUpdate
    public void preUpdate(){
        this.lastUpdateDt = LocalDateTime.now();
        this.lastUpdateUser = CommonUtil.getLadderAccountId();
    }

}
