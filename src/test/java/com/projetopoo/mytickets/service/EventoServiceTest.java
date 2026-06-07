package com.projetopoo.mytickets.service;

import com.projetopoo.mytickets.exception.BusinessException;
import com.projetopoo.mytickets.exception.EntityNotFoundException;
import com.projetopoo.mytickets.model.Evento;
import com.projetopoo.mytickets.model.Usuario;
import com.projetopoo.mytickets.model.dtos.EventoDTO;
import com.projetopoo.mytickets.model.enums.EventCategory;
import com.projetopoo.mytickets.repository.EventoRepository;
import com.projetopoo.mytickets.repository.TipoEventoRepository;
import com.projetopoo.mytickets.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventoServiceTest {

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TipoEventoRepository tipoEventoRepository;

    @InjectMocks
    private EventoService eventoService;

    @Test
    void criarEvento_deveSalvarEvento_semCriadorOuTipo() {
        EventoDTO dto = new EventoDTO(null, "Show de Joao", "Descrição",
                500, "Arena Norte", null, false,
                LocalDateTime.now().plusDays(10), null,
                150.0, 500, false, EventCategory.SHOW, null, null, null);

        Evento eventoSalvo = new Evento();
        eventoSalvo.setEventName(dto.eventName());

        when(eventoRepository.save(any(Evento.class))).thenReturn(eventoSalvo);

        Evento resultado = eventoService.criarEvento(dto);

        assertThat(resultado.getEventName()).isEqualTo("Show Foo Fighters");
    }

    @Test
    void buscarPorId_deveLancarExcecao_quandoEventoNaoEncontrado() {
        when(eventoRepository.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventoService.buscarPorId(42L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("42");
    }

    @Test
    void deletarEvento_deveChamarDelete_quandoEventoExiste() {
        Evento evento = new Evento();
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));

        eventoService.deletarEvento(1L);

        verify(eventoRepository).delete(evento);
    }

    @Test
    void adicionarAdmin_deveLancarExcecao_quandoUsuarioJaEAdmin() {
        Usuario usuario = new Usuario();
        setId(usuario, 10L);

        Evento evento = new Evento();
        evento.setAdmins(new ArrayList<>(List.of(usuario)));

        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));
        when(usuarioRepository.findById(10L)).thenReturn(Optional.of(usuario));

        assertThatThrownBy(() -> eventoService.adicionarAdmin(1L, 10L))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("já é admin");
    }

    private void setId(Usuario usuario, Long id) {
        try {
            var field = Usuario.class.getDeclaredField("idUsuario");
            field.setAccessible(true);
            field.set(usuario, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
