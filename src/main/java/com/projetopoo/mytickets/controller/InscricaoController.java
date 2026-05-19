package com.projetopoo.mytickets.controller;

import com.projetopoo.mytickets.model.dtos.InscricaoDTO;
import com.projetopoo.mytickets.model.dtos.InscricaoResponseDTO;
import com.projetopoo.mytickets.service.InscricaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    private final InscricaoService service;

    public InscricaoController(InscricaoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InscricaoResponseDTO criar(@Valid @RequestBody InscricaoDTO dto) {
        var insc = service.salvarInscricao(dto);
        return new InscricaoResponseDTO(
                insc.getIdInscricao(),
                insc.getUser().getName(),
                insc.getEvent().getIdEvento(),
                insc.getEvent().getEventName(),
                insc.getRegistrationAt(),
                insc.getVisitorCount()
        );
    }

    @GetMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public List<InscricaoResponseDTO> listarTodas() {
        return service.listarTodas().stream()
                .map(insc -> new InscricaoResponseDTO(
                        insc.getIdInscricao(),
                        insc.getUser().getName(),
                        insc.getEvent().getIdEvento(),
                        insc.getEvent().getEventName(),
                        insc.getRegistrationAt(),
                        insc.getVisitorCount()
                ))
                .toList();
    }

    @GetMapping("/me")
    public List<InscricaoResponseDTO> listarMinhasInscricoes() {
        return service.listarInscricoesUsuarioLogado().stream()
                .map(insc -> new InscricaoResponseDTO(
                        insc.getIdInscricao(),
                        insc.getUser().getName(),
                        insc.getEvent().getIdEvento(),
                        insc.getEvent().getEventName(),
                        insc.getRegistrationAt(),
                        insc.getVisitorCount()
                ))
                .toList();
    }

    @DeleteMapping("/{idInscricao}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long idInscricao) {
        service.excluirInscricao(idInscricao);
    }
}
