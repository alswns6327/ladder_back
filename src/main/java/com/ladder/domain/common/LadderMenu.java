package com.ladder.domain.common;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ladder_menu")
public class LadderMenu extends CommonColumns1{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LADDER_MENU_SEQ", nullable = false, updatable = false)
    private Long id;

    @Column(name = "LADDER_MENU_ORDER", nullable = false)
    private Integer ladderMenuOrder;

    @Column(name = "LADDER_MENU_PATH", nullable = false)
    private String ladderMenuPath;

    @Column(name = "LADDER_MENU_Name", nullable = false)
    private String ladderMenuName;
}
