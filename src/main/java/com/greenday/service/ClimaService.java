package com.greenday.service;

import com.greenday.exception.ResourceNotFoundException;
import com.greenday.model.Clima;
import com.greenday.repository.ClimaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClimaService {

    private final ClimaRepository climaRepository;
    private final PropriedadeService propriedadeService;

    public Clima registrar(Long propriedadeId, Clima clima) {
        clima.setPropriedade(propriedadeService.buscarPorId(propriedadeId));
        return climaRepository.save(clima);
    }

    public List<Clima> historico(Long propriedadeId) {
        return climaRepository.findByPropriedadeIdOrderByColetadoEmDesc(propriedadeId);
    }

    public Clima ultimo(Long propriedadeId) {
        return climaRepository.findFirstByPropriedadeIdOrderByColetadoEmDesc(propriedadeId)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum dado climatico para a propriedade " + propriedadeId));
    }
}
