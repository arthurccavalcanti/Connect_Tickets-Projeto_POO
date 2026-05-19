package com.projetopoo.mytickets.model.dtos;

import java.time.LocalDateTime;

public record InscricaoResponseDTO(
        Long idInscricao,
        String userName,
        Long idEvento,
        String eventName,
        LocalDateTime registrationAt,
        int visitorCount
) {}
