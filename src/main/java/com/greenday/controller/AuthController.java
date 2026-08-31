package com.greenday.controller;

import com.greenday.dto.CadastroDTO;
import com.greenday.dto.LoginDTO;
import com.greenday.dto.UsuarioDTO;
import com.greenday.model.Usuario;
import com.greenday.service.AuthService;
import com.greenday.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioDTO> cadastrar(@Valid @RequestBody CadastroDTO dto) {
        Usuario usuario = usuarioService.cadastrar(dto);
        return ResponseEntity.ok(UsuarioDTO.fromEntity(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginDTO dto) {
        String token = authService.autenticar(dto);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
