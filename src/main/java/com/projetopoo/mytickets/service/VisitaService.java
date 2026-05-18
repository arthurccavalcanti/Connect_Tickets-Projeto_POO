package com.projetopoo.mytickets.service;

import com.projetopoo.mytickets.exception.EntityNotFoundException;
import com.projetopoo.mytickets.model.Visita;
import com.projetopoo.mytickets.model.Usuario;
import com.projetopoo.mytickets.model.dtos.VisitaDTO;
import com.projetopoo.mytickets.model.dtos.VisitaResponseDTO;
import com.projetopoo.mytickets.repository.UsuarioRepository;
import com.projetopoo.mytickets.repository.VisitaRepository;
import com.projetopoo.mytickets.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class VisitaService {

    private final VisitaRepository visitaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SecurityUtils securityUtils;

    public VisitaService(VisitaRepository visitaRepository,
            UsuarioRepository usuarioRepository,
            SecurityUtils securityUtils) {
        this.visitaRepository = visitaRepository;
        this.usuarioRepository = usuarioRepository;
        this.securityUtils = securityUtils;
    }

    @Transactional
    public Visita criarVisita(VisitaDTO dto) {
        Usuario solicitante = usuarioRepository.findById(dto.requesterId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Solicitante não encontrado com ID: " + dto.requesterId()));

        Visita visita = new Visita();
        visita.setScheduledAt(dto.scheduledAt());
        visita.setRequester(solicitante);
        visita.setIsAuthorized(false);
        visita.setVisitorCount(dto.visitorCount());

        if (dto.authorizerId() != null) {
            Usuario autorizador = usuarioRepository.findById(dto.authorizerId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Autorizador não encontrado com ID: " + dto.authorizerId()));
            visita.setAuthorizer(autorizador);
        }

        return visitaRepository.save(visita);
    }

    @Transactional(readOnly = true)
    public Page<Visita> listarVisitas(Pageable pageable) {
        if (securityUtils.isAdmin()) {
            return visitaRepository.findAll(pageable);
        }
        Long currentUserId = securityUtils.getCurrentUserId();
        return visitaRepository.findByRequesterIdUsuario(currentUserId, pageable);
    }

    @Transactional(readOnly = true)
    public Visita buscarPorId(Long id) {
        Visita visita = visitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visita não encontrada com ID: " + id));
        securityUtils.verifyOwnership(
                visita.getRequester() != null ? visita.getRequester().getIdUsuario() : null,
                "Você não tem permissão para visualizar esta visita.");
        return visita;
    }

    @Transactional
    public Visita atualizarAutorizacao(Long id, boolean autorizada) {
        Visita visita = visitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visita não encontrada com ID: " + id));
        Long currentUserId = securityUtils.getCurrentUserId();
        Usuario autorizador = usuarioRepository.findById(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("Autorizador não encontrado com ID: " + currentUserId));

        visita.setIsAuthorized(autorizada);
        visita.setAuthorizer(autorizador);
        return visitaRepository.save(visita);
    }

    @Transactional
    public void excluirVisita(Long id) {
        Visita visita = visitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visita não encontrada com ID: " + id));
        securityUtils.verifyOwnership(
                visita.getRequester() != null ? visita.getRequester().getIdUsuario() : null,
                "Você não tem permissão para excluir esta visita.");
        visitaRepository.delete(visita);
    }

    public VisitaResponseDTO toResponseDTO(Visita v) {
        return new VisitaResponseDTO(
                v.getIdVisita(),
                v.getScheduledAt(),
                v.getIsAuthorized(),
                v.getRequester() != null ? v.getRequester().getName() : null,
                v.getAuthorizer() != null ? v.getAuthorizer().getName() : null,
                v.getVisitorCount());
    }
}
