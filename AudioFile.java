package com.example.trpg.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "audio_files")
public class AudioFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "audio_data", nullable = false, columnDefinition = "MEDIUMBLOB")
    private byte[] audioData;

    @Column(name = "upload_time")
    private String uploadTime;
}