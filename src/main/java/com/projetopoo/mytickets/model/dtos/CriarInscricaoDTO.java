package com.projetopoo.mytickets.model.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record CriarInscricaoDTO(
        @NotNull(message = "O ID do evento é obrigatório")
        Long eventoId,

        @Null(message = "Não envie userId. O usuário será identificado pelo token.")
        Long userId
) {}
