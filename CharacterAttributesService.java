package com.example.trpg.service;

import com.example.trpg.entity.CharacterAttributes;
import com.example.trpg.repository.CharacterAttributesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterAttributesService {

    @Autowired
    private CharacterAttributesRepository characterAttributesRepository;

    public CharacterAttributes getCharacterAttributesByCharacterId(String characterId) {
        return characterAttributesRepository.findByCharacterId(characterId);
    }

    // 您可以在這裡新增其他服務方法，例如：
    // - 儲存 CharacterAttributes
    // - 取得所有 CharacterAttributes
    // - 更新 CharacterAttributes
    // - 刪除 CharacterAttributes
}