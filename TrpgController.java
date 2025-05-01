package com.example.trpg.controller;

import com.example.trpg.entity.*; // 匯入 CharacterAttributes
import com.example.trpg.entity.Character;
import com.example.trpg.repository.*;
import com.example.trpg.service.CharacterAttributesService; // 匯入 CharacterAttributesService
import com.example.trpg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/trpg/api")
@CrossOrigin(origins = "*")
public class TrpgController {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private SaveRepository saveRepository;

    @Autowired
    private AudioFileRepository audioFileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CharacterAttributesService characterAttributesService;

    // 獲取所有角色
    @GetMapping("/characters")
    public ResponseEntity<List<Character>> getAllCharacters() {
        try {
            List<Character> characters = characterRepository.findAll();
            return ResponseEntity.ok(characters);
        } catch (Exception e) {
            System.err.println("獲取角色失敗: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 檢查存檔
    @GetMapping("/save/{playerId}")
    public ResponseEntity<?> getSave(@PathVariable Integer playerId) {
        try {
            Optional<Save> save = saveRepository.findByPlayerPlayerId(playerId);
            return save.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.ok().body(null));
        } catch (Exception e) {
            System.err.println("查詢存檔失敗: " + e.getMessage());
            return ResponseEntity.badRequest().body("查詢存檔失敗: " + e.getMessage());
        }
    }

    // 保存角色選擇
    @PostMapping("/save")
    public ResponseEntity<String> saveGame(@RequestBody SaveRequest saveRequest) {
        try {
            System.out.println("Received save request: " + saveRequest);

            if (saveRequest.getPlayerId() == null) {
                return ResponseEntity.badRequest().body("playerId 為必填項");
            }
            if (saveRequest.getCharacterId() == null || saveRequest.getCharacterId().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("characterId 為必填項");
            }
            if (saveRequest.getSaveName() == null || saveRequest.getSaveName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("存檔名稱不能為空");
            }

            Player player = playerRepository.findById(saveRequest.getPlayerId())
                    .orElseGet(() -> {
                        Player newPlayer = new Player();
                        newPlayer.setPlayerId(saveRequest.getPlayerId());
                        newPlayer.setUsername("Player_" + saveRequest.getPlayerId());
                        newPlayer.setLastLogin(LocalDateTime.now().toString());
                        return playerRepository.save(newPlayer);
                    });

            Optional<Character> characterOpt = characterRepository.findById(saveRequest.getCharacterId());
            if (!characterOpt.isPresent()) {
                return ResponseEntity.badRequest().body("角色 " + saveRequest.getCharacterId() + " 不存在");
            }

            Save save = new Save();
            save.setPlayer(player);
            save.setCharacter(characterOpt.get());
            save.setSaveName(saveRequest.getSaveName().trim());
            save.setSaveTime(LocalDateTime.now().toString());
            saveRepository.save(save);

            System.out.println("Save successful: saveId=" + save.getSaveId());
            return ResponseEntity.ok("存檔成功");

        } catch (Exception e) {
            System.err.println("Save failed: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("存檔失敗: " + e.getMessage());
        }
    }

    // 更新存檔
    @PutMapping("/save/{saveId}")
    public ResponseEntity<String> updateSave(@PathVariable Long saveId, @RequestBody SaveRequest saveRequest) {
        try {
            Optional<Save> existingSave = saveRepository.findById(saveId);
            if (!existingSave.isPresent()) {
                return ResponseEntity.badRequest().body("存檔不存在");
            }
            Save save = existingSave.get();

            save.setSaveTime(LocalDateTime.now().toString());

            if (saveRequest.getSaveName() != null && !saveRequest.getSaveName().trim().isEmpty()) {
                save.setSaveName(saveRequest.getSaveName().trim());
            }

            saveRepository.save(save);
            System.out.println("Save updated: saveId=" + save.getSaveId());
            return ResponseEntity.ok("存檔更新成功");
        } catch (Exception e) {
            System.err.println("Save update failed: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("存檔更新失敗: " + e.getMessage());
        }
    }

    // 上傳音效檔案
    @PostMapping("/upload-audio")
    public ResponseEntity<String> uploadAudio(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("上傳檔案為空");
            }

            AudioFile audioFile = new AudioFile();
            audioFile.setFileName(file.getOriginalFilename());
            audioFile.setAudioData(file.getBytes());
            audioFile.setUploadTime(LocalDateTime.now().toString());
            audioFileRepository.save(audioFile);

            return ResponseEntity.ok("音效上傳成功: " + file.getOriginalFilename());
        } catch (Exception e) {
            System.err.println("音效上傳失敗: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("音效上傳失敗: " + e.getMessage());
        }
    }

    // 獲取音效
    @GetMapping("/audio/{id}")
    public ResponseEntity<byte[]> getAudio(@PathVariable Long id) {
        try {
            Optional<AudioFile> audioFileOpt = audioFileRepository.findById(id);
            return audioFileOpt.map(audioFile -> ResponseEntity.ok()
                            .header("Content-Type", "audio/wav")
                            .body(audioFile.getAudioData()))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            System.err.println("獲取音效失敗: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 管理員獲取所有用戶（移除硬編碼認證）
    @GetMapping("/admin/users")
    public ResponseEntity<List<Player>> getAllUsers() {
        System.out.println("訪問 /trpg/api/admin/users");
        return ResponseEntity.ok(playerRepository.findAll());
    }

    // 管理員獲取所有存檔（新增端點）
    @GetMapping("/admin/saves")
    public ResponseEntity<List<Save>> getAllSaves() {
        System.out.println("訪問 /trpg/api/admin/saves");
        try {
            List<Save> saves = saveRepository.findAll();
            return ResponseEntity.ok(saves);
        } catch (Exception e) {
            System.err.println("獲取存檔列表失敗: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 管理員刪除存檔（移除硬編碼認證）
    @DeleteMapping("/admin/saves/{saveId}")
    public ResponseEntity<String> deleteSave(@PathVariable Long saveId) {
        System.out.println("刪除存檔: saveId=" + saveId);
        try {
            if (!saveRepository.existsById(saveId)) {
                return ResponseEntity.badRequest().body("存檔不存在");
            }
            saveRepository.deleteById(saveId);
            return ResponseEntity.ok("存檔已刪除");
        } catch (Exception e) {
            System.err.println("刪除存檔失敗: " + e.getMessage());
            return ResponseEntity.badRequest().body("刪除存檔失敗: " + e.getMessage());
        }
    }

    // 註冊新用戶
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        System.out.println("Received register request: " + request);
        try {
            String username = request.get("username");
            String password = request.get("password");
            String role = request.getOrDefault("role", "player");

            if (username == null || password == null) {
                return ResponseEntity.badRequest().body("用戶名和密碼為必填項");
            }

            userService.registerUser(username, password, role);
            System.out.println("Registration successful for username: " + username);
            return ResponseEntity.ok("註冊成功");
        } catch (Exception e) {
            System.err.println("Registration failed: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("註冊失敗: " + e.getMessage());
        }
    }

    // 登入（移除 token 生成）
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "登入成功");
            response.put("role", user.getRole().toLowerCase());
            response.put("playerId", user.getPlayerId() != null ? user.getPlayerId() : null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("登入失敗: " + e.getMessage());
            return ResponseEntity.status(401).body("登入失敗: " + e.getMessage());
        }
    }

    // 檢查管理員頁面訪問權限（移除硬編碼認證）
    @GetMapping("/admin/check")
    public ResponseEntity<?> checkAdminAccess() {
        System.out.println("訪問 /trpg/api/admin/check");
        return ResponseEntity.ok("允許訪問");
    }

    // 生成唯一的 playerId（簡單實現，實際應用中可優化）
    private Integer generatePlayerId() {
        Integer maxPlayerId = playerRepository.findAll().stream()
                .map(Player::getPlayerId)
                .max(Integer::compare)
                .orElse(0);
        return maxPlayerId + 1;
    }

    // 獲取角色數值
    @GetMapping("/character/attributes/{characterId}")
    public ResponseEntity<CharacterAttributes> getCharacterAttributes(@PathVariable String characterId) {
        try {
            CharacterAttributes attributes = characterAttributesService.getCharacterAttributesByCharacterId(characterId);
            if (attributes == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(attributes);
        } catch (Exception e) {
            System.err.println("獲取角色數值失敗: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}

// 用於接收前端 POST 和 PUT 請求的 DTO
class SaveRequest {
    private Integer playerId;
    private String characterId;
    private String saveName;

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    @Override
    public String toString() {
        return "SaveRequest{" +
                "playerId=" + playerId +
                ", characterId='" + characterId + '\'' +
                ", saveName='" + saveName + '\'' +
                '}';
    }
}

// 登入請求 DTO
class LoginRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}