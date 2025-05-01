package com.example.trpg.repository; // 請確保套件名稱正確

import com.example.trpg.entity.CharacterAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterAttributesRepository extends JpaRepository<CharacterAttributes, Integer> {
    // 根據 characterId 查詢 CharacterAttributes
    CharacterAttributes findByCharacterId(String characterId);

    // 您可以根據需要新增其他查詢方法
}