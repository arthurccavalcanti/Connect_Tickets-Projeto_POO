package com.projetopoo.mytickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.projetopoo.mytickets.model.Evento;
import com.projetopoo.mytickets.model.Inscricao;
import com.projetopoo.mytickets.model.Usuario;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    List<Inscricao> findByEvent_IdEvento(Long eventoId);

    List<Inscricao> findByUser_IdUsuario(Long usuarioId);

    boolean existsByUserAndEvent(Usuario user, Evento event);

}
