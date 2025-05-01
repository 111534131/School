package com.example.trpg.repository;

import com.example.trpg.entity.Save;
import org.springframework.data.jpa.repository.JpaRepository; // 推薦使用 JpaRepository
import java.util.Optional;

public interface SaveRepository extends JpaRepository<Save, Long> { // 改為 Long
    Optional<Save> findByPlayerPlayerId(Integer playerId);
}