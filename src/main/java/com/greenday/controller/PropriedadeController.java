package com.greenday.controller;

import com.greenday.model.Propriedade;
import com.greenday.model.Usuario;
import com.greenday.service.PropriedadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propriedades")
@RequiredArgsConstructor
public class PropriedadeController {

    private final PropriedadeService propriedadeService;

    @PostMapping
    public ResponseEntity<Propriedade> criar(@AuthenticationPrincipal Usuario usuario,
                                              @RequestBody Propriedade propriedade) {
        return ResponseEntity.ok(propriedadeService.criar(propriedade, usuario));
    }

    @GetMapping
    public ResponseEntity<List<Propriedade>> minhasPropriedades(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(propriedadeService.listarPorUsuario(usuario.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Propriedade> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(propriedadeService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Propriedade> atualizar(@PathVariable Long id, @RequestBody Propriedade dados) {
        return ResponseEntity.ok(propriedadeService.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        propriedadeService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
