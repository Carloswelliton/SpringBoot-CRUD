package com.example.demo.models.DTO;

import jakarta.validation.constraints.*;

public record PessoaDTO(
  Long id,
  
  @NotBlank(message="O nome não pode ser vazio")
  String nome,

  @NotBlank(message="O cpf não pode ser vazio")
  String cpf,

  @Min(value=1, message="Idade inválida")
  @Max(value=105, message="Idade inválida")
  int idade
) {}