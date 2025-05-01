package com.example.trpg.service;

import com.example.trpg.entity.User;
import com.example.trpg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private Map<String, Integer> loginAttempts = new HashMap<>();
    private Map<String, Long> lockoutUntil = new HashMap<>();

    public User registerUser(String username, String password, String role) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new RuntimeException("用戶名已存在");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role.toLowerCase());
        user.setCreatedAt(new Date());
        user.setPlayerId(role.equals("player") ? generatePlayerId() : null);
        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        if (lockoutUntil.containsKey(username)) {
            long lockoutTime = lockoutUntil.get(username);
            if (System.currentTimeMillis() < lockoutTime) {
                throw new RuntimeException("帳號已被鎖定，請在 " + ((lockoutTime - System.currentTimeMillis()) / 1000) + " 秒後重試");
            } else {
                loginAttempts.remove(username);
                lockoutUntil.remove(username);
            }
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用戶不存在"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            int attempts = loginAttempts.getOrDefault(username, 0) + 1;
            loginAttempts.put(username, attempts);
            if (attempts >= 5) {
                lockoutUntil.put(username, System.currentTimeMillis() + 300000); // 鎖定 5 分鐘
                throw new RuntimeException("登入失敗次數過多，帳號已被鎖定 5 分鐘");
            }
            throw new RuntimeException("密碼錯誤，剩餘嘗試次數：" + (5 - attempts));
        }

        loginAttempts.remove(username);
        return user;
    }

    private Integer generatePlayerId() {
        Integer maxPlayerId = userRepository.findAll().stream()
                .map(User::getPlayerId)
                .filter(playerId -> playerId != null)
                .max(Integer::compare)
                .orElse(0);
        return maxPlayerId + 1;
    }
}