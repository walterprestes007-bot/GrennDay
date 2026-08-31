package com.greenday.controller;

import com.greenday.model.Esp32;
import com.greenday.service.AlertaService;
import com.greenday.service.Esp32Service;
import com.greenday.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Endpoints publicos consumidos diretamente pelos dispositivos ESP32 em campo
 * (autenticados por codigo de dispositivo, nao por JWT de usuario).
 */
@RestController
@RequestMapping("/api/esp32")
@RequiredArgsConstructor
public class Esp32Controller {

    private final Esp32Service esp32Service;
    private final SensorService sensorService;
    private final AlertaService alertaService;

    @PostMapping("/propriedades/{propriedadeId}/registrar")
    public ResponseEntity<Esp32> registrar(@PathVariable Long propriedadeId, @RequestBody Esp32 dispositivo) {
        return ResponseEntity.ok(esp32Service.registrar(propriedadeId, dispositivo));
    }

    @PostMapping("/{codigoDispositivo}/heartbeat")
    public ResponseEntity<Void> heartbeat(@PathVariable String codigoDispositivo) {
        esp32Service.registrarHeartbeat(codigoDispositivo);
        return ResponseEntity.noContent().build();
    }

    /** Recebe uma leitura de um sensor especifico vinda do dispositivo. */
    @PostMapping("/sensores/{sensorId}/leitura")
    public ResponseEntity<Void> enviarLeitura(@PathVariable Long sensorId, @RequestBody Map<String, Double> corpo) {
        Double valor = corpo.get("valor");
        sensorService.registrarLeitura(sensorId, valor);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/propriedades/{propriedadeId}")
    public ResponseEntity<List<Esp32>> listar(@PathVariable Long propriedadeId) {
        return ResponseEntity.ok(esp32Service.listarPorPropriedade(propriedadeId));
    }
}
