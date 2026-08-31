package com.greenday.service;

import com.greenday.exception.ResourceNotFoundException;
import com.greenday.model.Propriedade;
import com.greenday.model.Usuario;
import com.greenday.repository.PropriedadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropriedadeService {

    private final PropriedadeRepository propriedadeRepository;

    public Propriedade criar(Propriedade propriedade, Usuario proprietario) {
        propriedade.setProprietario(proprietario);
        return propriedadeRepository.save(propriedade);
    }

    public List<Propriedade> listarPorUsuario(Long usuarioId) {
        return propriedadeRepository.findByProprietarioId(usuarioId);
    }

    public Propriedade buscarPorId(Long id) {
        return propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade nao encontrada: " + id));
    }

    public Propriedade atualizar(Long id, Propriedade dados) {
        Propriedade propriedade = buscarPorId(id);
        propriedade.setNome(dados.getNome());
        propriedade.setEndereco(dados.getEndereco());
        propriedade.setAreaHectares(dados.getAreaHectares());
        propriedade.setLatitude(dados.getLatitude());
        propriedade.setLongitude(dados.getLongitude());
        return propriedadeRepository.save(propriedade);
    }

    public void excluir(Long id) {
        propriedadeRepository.delete(buscarPorId(id));
    }
}
