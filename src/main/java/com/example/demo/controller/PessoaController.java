package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.DTO.PessoaDTO;
import com.example.demo.service.PessoaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

  @Autowired
  private PessoaService pessoaService;

  @PostMapping
  public ResponseEntity<PessoaDTO> salvar(@Valid @RequestBody PessoaDTO dto){
    PessoaDTO pessoa = pessoaService.salvarDto(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
  }

  @GetMapping
  public ResponseEntity<List<PessoaDTO>> getAll(){
    return ResponseEntity.ok(pessoaService.listarTodos());
  }

  @GetMapping("{id}")
  public ResponseEntity<PessoaDTO> bucarPorId(@PathVariable Long id){
    return ResponseEntity.ok(pessoaService.findById(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id){
    pessoaService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<PessoaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PessoaDTO dto){
    PessoaDTO pessoaAtualizada = pessoaService.atualizar(id, dto);
    return ResponseEntity.ok(pessoaAtualizada);
  }
}
