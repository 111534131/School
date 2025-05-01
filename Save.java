package com.example.trpg.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "saves")
@Data
public class Save {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "save_id")
    private Long saveId;

    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "character_id", referencedColumnName = "character_id")
    private Character character;

    @Column(name = "save_name")
    private String saveName;

    @Column(name = "save_time")
    private String saveTime;
}