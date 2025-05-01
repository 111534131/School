package com.example.trpg.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "character_attributes")
@Data
public class CharacterAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "character_id")
    private String characterId;

    @Column(name = "strength")
    private Integer strength;

    @Column(name = "constitution")
    private Integer constitution;

    @Column(name = "agility")
    private Integer agility;

    @Column(name = "willpower")
    private Integer willpower;

    @Column(name = "intelligence")
    private Integer intelligence;

    @Column(name = "luck")
    private Integer luck;
}