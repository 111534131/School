package com.example.trpg.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "players")
@Data
public class Player {
    @Id
    @Column(name = "player_id")
    private Integer playerId;

    @Column(name = "username")
    private String username;

    @Column(name = "last_login")
    private String lastLogin;
    // 在原有字段基础上添加
    @Column(name = "role")
    private String role = "USER";
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<Save> saves;
}