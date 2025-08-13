package com.foro.controller;

import com.foro.dto.AuthResponse;
import com.foro.dto.LoginRequest;
import com.foro.dto.RegisterRequest;
import com.foro.model.User;
import com.foro.repository.UserRepository;
import com.foro.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository ur, PasswordEncoder pe, AuthenticationManager am, JwtUtil ju) {
        this.userRepository = ur; this.passwordEncoder = pe; this.authenticationManager = am; this.jwtUtil = ju;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepository.existsByUsername(req.username)) return ResponseEntity.badRequest().body("Username already taken");
        if (userRepository.existsByEmail(req.email)) return ResponseEntity.badRequest().body("Email already in use");
        User u = new User();
        u.setUsername(req.username);
        u.setEmail(req.email);
        u.setPassword(passwordEncoder.encode(req.password));
        userRepository.save(u);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username, req.password)
        );
        // si no lanza excepción, autenticación correcta
        String token = jwtUtil.generateToken(req.username);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
