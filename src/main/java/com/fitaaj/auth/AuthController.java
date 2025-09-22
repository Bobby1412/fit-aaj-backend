package com.fitaaj.auth;

import com.fitaaj.user.User;
import com.fitaaj.user.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public record RegisterRequest(@NotBlank String name, @Email String email, @NotBlank String password) {}
    public record LoginRequest(@Email String email, @NotBlank String password) {}

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email already in use"));
        }
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        userRepository.save(user);
        return ResponseEntity.ok(Map.of(
                "message", "signup successful",
                "userId", user.getId(),
                "name", user.getName()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return userRepository.findByEmail(request.email())
                .filter(u -> passwordEncoder.matches(request.password(), u.getPasswordHash()))
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(Map.of("message", "login successful", "userId", u.getId(), "name", u.getName())))
                .orElseGet(() -> ResponseEntity.status(401).body(Map.of("message", "Invalid credentials")));
    }
}


