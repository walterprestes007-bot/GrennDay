package com.greenday.controller;

import com.greenday.dto.SensorDTO;
import com.greenday.model.DadosSensor;
import com.greenday.model.Sensor;
import com.greenday.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propriedades/{propriedadeId}/sensores")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @PostMapping
    public ResponseEntity<SensorDTO> criar(@PathVariable Long propriedadeId, @RequestBody Sensor sensor) {
        return ResponseEntity.ok(SensorDTO.fromEntity(sensorService.criar(propriedadeId, sensor)));
    }

    @GetMapping
    public ResponseEntity<List<SensorDTO>> listar(@PathVariable Long propriedadeId) {
        List<SensorDTO> sensores = sensorService.listarPorPropriedade(propriedadeId).stream()
                .map(SensorDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(sensores);
    }

    @GetMapping("/{id}/historico")
    public ResponseEntity<List<DadosSensor>> historico(@PathVariable Long propriedadeId, @PathVariable Long id) {
        return ResponseEntity.ok(sensorService.historico(id));
    }
}
