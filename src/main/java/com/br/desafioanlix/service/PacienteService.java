package com.br.desafioanlix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.br.desafioanlix.model.Paciente;
import com.br.desafioanlix.repository.PacienteRepository;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    public Paciente save(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> findByNomeContaining(String nome) {
        return pacienteRepository.findByNomeContaining(nome);
    }

    public Paciente findById(Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        pacienteRepository.deleteById(id);
    }
}