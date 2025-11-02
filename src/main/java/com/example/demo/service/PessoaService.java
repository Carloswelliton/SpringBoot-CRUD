package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.DTO.PessoaDTO;
import com.example.demo.models.PessoaModel;
import com.example.demo.repository.PessoaRepository;

@Service
public class PessoaService {

  @Autowired
  private PessoaRepository pessoaRepository;

  //Com DTO
  public List<PessoaDTO> listarTodos(){
    return pessoaRepository.findAll()
      .stream()
      .map(pessoa -> new PessoaDTO(
        pessoa.getId(),
        pessoa.getNome(),
        pessoa.getCpf(),
        pessoa.getIdade()
      ))
      .toList();
  }

  //Com DTO
  public PessoaDTO salvarDto(PessoaDTO dto){
    try {
        if(dto.nome().isEmpty() || dto.nome() == null){
          throw new RuntimeException("O nome não pode ficar vazio");
        }
        if(dto.cpf().isEmpty() || dto.cpf() == null){
          throw new RuntimeException("CPF não pode ficar vazio");
        }
        if(dto.idade() >= 105 || dto.idade() <= 0){
          throw new RuntimeException("Idade inválida");
        }

        PessoaModel pessoa = new PessoaModel();
        pessoa.getId();
        pessoa.setNome(dto.nome());
        pessoa.setCpf(dto.cpf());
        pessoa.setIdade(dto.idade());
        PessoaModel salvo = pessoaRepository.save(pessoa);
        return new PessoaDTO(salvo.getId(),salvo.getNome(), salvo.getCpf(), salvo.getIdade());

    } catch (Exception e) {
      System.err.println("Erro ao salvar cadastro " + e.getMessage());
      throw new RuntimeException("Erro ao salvar pessoa " + e.getMessage());
    }
  }

  public PessoaDTO findById(Long id){
    PessoaModel pessoa = pessoaRepository.findById(id).orElseThrow(()-> new RuntimeException("Pessoa não localizada"));
    return new PessoaDTO(
      pessoa.getId(),
      pessoa.getNome(),
      pessoa.getCpf(),
      pessoa.getIdade()
    );
  }

  public void delete(Long id){
    pessoaRepository.deleteById(id);
  }

  public PessoaDTO atualizar(Long id, PessoaDTO dto){
    PessoaModel pessoaExistente = pessoaRepository.findById(id).orElseThrow(()->new RuntimeException("Pessoa não encontrada"));
    pessoaExistente.setNome(dto.nome());
    pessoaExistente.setCpf(dto.cpf());
    pessoaExistente.setIdade(dto.idade());

    PessoaModel pessoaAtualizada = pessoaRepository.save(pessoaExistente);
    return new PessoaDTO(
      pessoaAtualizada.getId(),
      pessoaAtualizada.getNome(),
      pessoaAtualizada.getCpf(),
      pessoaAtualizada.getIdade()
    );
  }
 }
