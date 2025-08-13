package com.foro.dto;

public class AuthResponse { public String token; public String tokenType = "Bearer"; public AuthResponse(String t) { this.token = t; } }