package com.example.trpg.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "characters")
@Data
public class Character {
    @Id
    @Column(name = "character_id")
    private String characterId;

    @Column(name = "name")
    private String name;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "profession")
    private String profession;

    @Column(name = "education")
    private String education;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "birthplace")
    private String birthplace;

    @Column(name = "residence")
    private String residence;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL)
    private List<Save> saves; // 添加與 Save 的一對多關係

    // 添加與 CharacterAttributes 的一對一關係
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "character_id", referencedColumnName = "character_id", insertable = false, updatable = false)
    private CharacterAttributes attributes;
}