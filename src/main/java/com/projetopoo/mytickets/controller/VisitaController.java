package com.projetopoo.mytickets.controller;

import com.projetopoo.mytickets.model.dtos.VisitaDTO;
import com.projetopoo.mytickets.model.dtos.VisitaResponseDTO;
import com.projetopoo.mytickets.service.VisitaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visitas")
public class VisitaController {

    private final VisitaService service;

    public VisitaController(VisitaService service) {
        this.service = service;
    }

    @GetMapping
    public Page<VisitaResponseDTO> listar(
            @PageableDefault(size = 10, sort = "idVisita", direction = Sort.Direction.ASC) Pageable pageable) {
        return service.listarVisitas(pageable).map(service::toResponseDTO);
    }

    @GetMapping("/{idVisita}")
    public VisitaResponseDTO buscarPorId(@PathVariable Long idVisita) {
        return service.toResponseDTO(service.buscarPorId(idVisita));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VisitaResponseDTO criar(@Valid @RequestBody VisitaDTO dto) {
        return service.toResponseDTO(service.criarVisita(dto));
    }

    @PatchMapping("/{idVisita}/autorizacao")
    @PreAuthorize("hasRole('ADMIN')")
    public VisitaResponseDTO atualizarAutorizacao(@PathVariable Long idVisita,
            @RequestParam boolean autorizada) {
        return service.toResponseDTO(service.atualizarAutorizacao(idVisita, autorizada));
    }

    @DeleteMapping("/{idVisita}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long idVisita) {
        service.excluirVisita(idVisita);
    }
}
