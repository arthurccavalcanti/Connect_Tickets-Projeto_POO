package com.projetopoo.mytickets.service;

import com.projetopoo.mytickets.exception.BusinessException;
import com.projetopoo.mytickets.exception.EntityNotFoundException;
import com.projetopoo.mytickets.model.Evento;
import com.projetopoo.mytickets.model.Inscricao;
import com.projetopoo.mytickets.model.Usuario;
import com.projetopoo.mytickets.model.dtos.CriarInscricaoDTO;
import com.projetopoo.mytickets.model.dtos.InscricaoDTO;
import com.projetopoo.mytickets.model.dtos.InscricaoResponseDTO;
import com.projetopoo.mytickets.repository.EventoRepository;
import com.projetopoo.mytickets.repository.InscricaoRepository;
import com.projetopoo.mytickets.repository.UsuarioRepository;
import com.projetopoo.mytickets.security.CustomUserDetails;
import com.projetopoo.mytickets.security.SecurityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;
    private final SecurityUtils securityUtils;

    public InscricaoService(InscricaoRepository inscricaoRepository,
                            UsuarioRepository usuarioRepository,
                            EventoRepository eventoRepository,
                            SecurityUtils securityUtils) {
        this.inscricaoRepository = inscricaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
        this.securityUtils = securityUtils;
    }

    @Transactional
    public Inscricao salvarInscricao(InscricaoDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.userId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.userId()));

        Evento evento = eventoRepository.findById(dto.eventId())
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado com ID: " + dto.eventId()));

        List<Inscricao> inscricoesExistentes = inscricaoRepository.findByEvent_IdEvento(dto.eventId());
        int ocupado = inscricoesExistentes.stream().mapToInt(Inscricao::getVisitorCount).sum();

        if (ocupado + dto.visitorCount() > evento.getCapacity()) {
            throw new BusinessException("Capacidade insuficiente. Vagas disponíveis: " + (evento.getCapacity() - ocupado));
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setUser(usuario);
        inscricao.setEvent(evento);
        inscricao.setRegistrationAt(dto.registrationAt() != null ? dto.registrationAt() : LocalDateTime.now());
        inscricao.setVisitorCount(dto.visitorCount());

        return inscricaoRepository.save(inscricao);
    }

    @Transactional
    public InscricaoResponseDTO criar(CriarInscricaoDTO dto, String emailUsuarioLogado) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + emailUsuarioLogado));

        Evento evento = eventoRepository.findById(dto.eventoId())
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado com ID: " + dto.eventoId()));

        if (inscricaoRepository.existsByUserAndEvent(usuario, evento)) {
            throw new BusinessException("Usuário já inscrito neste evento");
        }

        int visitorCount = 1;
        List<Inscricao> inscricoesExistentes = inscricaoRepository.findByEvent_IdEvento(dto.eventoId());
        int ocupado = inscricoesExistentes.stream().mapToInt(Inscricao::getVisitorCount).sum();

        if (ocupado + visitorCount > evento.getCapacity()) {
            throw new BusinessException("Capacidade insuficiente. Vagas disponíveis: " + (evento.getCapacity() - ocupado));
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setUser(usuario);
        inscricao.setEvent(evento);
        inscricao.setRegistrationAt(LocalDateTime.now());
        inscricao.setVisitorCount(visitorCount);

        Inscricao inscricaoSalva = inscricaoRepository.save(inscricao);
        return toResponseDTO(inscricaoSalva);
    }

    @Transactional(readOnly = true)
    public List<Inscricao> listarTodas() {
        return inscricaoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Inscricao> listarInscricoesUsuarioLogado() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUsuario().getIdUsuario();
        return inscricaoRepository.findByUser_IdUsuario(userId);
    }

    @Transactional
    public void excluirInscricao(Long id) {
        Inscricao inscricao = inscricaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscrição não encontrada com ID: " + id));
        securityUtils.verifyOwnership(
                inscricao.getUser() != null ? inscricao.getUser().getIdUsuario() : null,
                "Você não tem permissão para excluir esta inscrição."
        );
        inscricaoRepository.delete(inscricao);
    }

    public InscricaoResponseDTO toResponseDTO(Inscricao inscricao) {
        return new InscricaoResponseDTO(
                inscricao.getIdInscricao(),
                inscricao.getUser().getName(),
                inscricao.getEvent().getIdEvento(),
                inscricao.getEvent().getEventName(),
                inscricao.getRegistrationAt(),
                inscricao.getVisitorCount()
        );
    }
}
