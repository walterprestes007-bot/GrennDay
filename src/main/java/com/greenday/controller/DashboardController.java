package com.greenday.controller;

import com.greenday.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/propriedades/{propriedadeId}/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final RelatorioService relatorioService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> resumo(@PathVariable Long propriedadeId) {
        return ResponseEntity.ok(relatorioService.gerarResumoPropriedade(propriedadeId));
    }
}
