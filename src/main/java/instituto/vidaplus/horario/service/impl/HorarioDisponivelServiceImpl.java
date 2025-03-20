package instituto.vidaplus.horario.service.impl;

import instituto.vidaplus.agenda.exception.AgendaNaoEncontradaException;
import instituto.vidaplus.agenda.model.Agenda;
import instituto.vidaplus.agenda.repository.AgendaRepository;
import instituto.vidaplus.exception.genericas.DataInvalidaException;
import instituto.vidaplus.horario.dto.HorarioDisponivelDTO;
import instituto.vidaplus.horario.enums.DiasDaSemanaEnum;
import instituto.vidaplus.horario.exception.HorarioNaoEncontradoException;
import instituto.vidaplus.horario.model.HorarioDisponivel;
import instituto.vidaplus.horario.repository.HorarioDisponivelRepository;
import instituto.vidaplus.horario.service.HorarioDisponivelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HorarioDisponivelServiceImpl implements HorarioDisponivelService {

    private HorarioDisponivelRepository horarioDisponivelRepository;
    private AgendaRepository agendaRepository;

    @Override
    @Transactional
    public HorarioDisponivelDTO criarHorarioDisponivel(HorarioDisponivelDTO horarioDisponivelDTO) {
        Agenda agenda = agendaRepository.findById(horarioDisponivelDTO.getAgendaId())
                .orElseThrow(() -> new AgendaNaoEncontradaException("Agenda não encontrada"));

        HorarioDisponivel horarioDisponivel = new HorarioDisponivel();
        horarioDisponivel.setAgenda(agenda);
        horarioDisponivel.setDiaDaSemana(horarioDisponivelDTO.getDiaDaSemana());
        horarioDisponivel.setHoraInicio(horarioDisponivelDTO.getHoraInicio());
        horarioDisponivel.setHoraFim(horarioDisponivelDTO.getHoraFim());
        horarioDisponivel.setDuracaoMediaEmMinutos(horarioDisponivelDTO.getDuracaoMediaEmMinutos());
        horarioDisponivel.setDisponivel(true);

        HorarioDisponivel horarioDisponivelSalvo = horarioDisponivelRepository.save(horarioDisponivel);
        return new HorarioDisponivelDTO(horarioDisponivelSalvo);

    }

    @Override
    public HorarioDisponivelDTO buscarHorarioDisponivelPorId(Long id) {
        HorarioDisponivel horario = horarioDisponivelRepository.findById(id)
                .orElseThrow(() -> new HorarioNaoEncontradoException("Horário não encontrado"));

        return new HorarioDisponivelDTO(horario);
    }

    @Override
    public Page<HorarioDisponivelDTO> listarHorarios(Pageable pageable) {
        return horarioDisponivelRepository.findAll(pageable)
                .map(HorarioDisponivelDTO::new);
    }

    @Override
    @Transactional
    public HorarioDisponivelDTO atualizarHorarioDisponivel(Long id, HorarioDisponivelDTO horarioDTO) {
        HorarioDisponivel horario = horarioDisponivelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horário não encontrado com o id: " + id));

        if (horarioDTO.getAgendaId() != null) {
            Agenda agenda = agendaRepository.findById(horarioDTO.getAgendaId())
                    .orElseThrow(() -> new RuntimeException("Agenda não encontrada com o id: " + horarioDTO.getAgendaId()));
            horario.setAgenda(agenda);
        }

        if (horarioDTO.getDiaDaSemana() != null) {
            horario.setDiaDaSemana(horarioDTO.getDiaDaSemana());
        }

        if (horarioDTO.getHoraInicio() != null) {
            horario.setHoraInicio(horarioDTO.getHoraInicio());
        }

        if (horarioDTO.getHoraFim() != null) {
            horario.setHoraFim(horarioDTO.getHoraFim());
        }

        if (horarioDTO.getDuracaoMediaEmMinutos() != null) {
            horario.setDuracaoMediaEmMinutos(horarioDTO.getDuracaoMediaEmMinutos());
        }

        if (horarioDTO.getDisponivel() != null) {
            horario.setDisponivel(horarioDTO.getDisponivel());
        }

        HorarioDisponivel updatedHorario = horarioDisponivelRepository.save(horario);
        return new HorarioDisponivelDTO(updatedHorario);
    }

    @Override
    @Transactional
    public String deletarHorarioDisponivel(Long id) {
        if (!horarioDisponivelRepository.existsById(id)) {
            throw new RuntimeException("Horário não encontrado com o id: " + id);
        }
        horarioDisponivelRepository.deleteById(id);
        return "Horário deletado com sucesso";
    }

    @Override
    public List<HorarioDisponivelDTO> listarHorariosPorAgenda(Long agendaId) {
        return horarioDisponivelRepository.findByAgendaId(agendaId).stream()
                .map(HorarioDisponivelDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<HorarioDisponivelDTO> listarHorariosDisponiveisPorAgenda(Long agendaId) {
        return horarioDisponivelRepository.findByAgendaIdAndDisponivelTrue(agendaId).stream()
                .map(HorarioDisponivelDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String marcarHorarioComoIndisponivel(Long id) {
        HorarioDisponivel horario = horarioDisponivelRepository.findById(id)
                .orElseThrow(() -> new HorarioNaoEncontradoException("Horário não encontrado"));
        horario.setDisponivel(false);
        horarioDisponivelRepository.save(horario);
        return "Horário marcado como indisponível";
    }

    @Override
    @Transactional
    public String marcarHorarioComoDisponivel(Long id) {
        HorarioDisponivel horario = horarioDisponivelRepository.findById(id)
                .orElseThrow(() -> new HorarioNaoEncontradoException("Horário não encontrado"));
        horario.setDisponivel(true);
        horarioDisponivelRepository.save(horario);
        return "Horário marcado como disponível";
    }

    @Override
    public Boolean verificarDisponibilidadeDeHorario(Long agendaId, LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
        Agenda agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new AgendaNaoEncontradaException("Agenda não encontrada"));

        DiasDaSemanaEnum diaDaSemana = buscarDiaDaSemanaPorValor(data.getDayOfWeek().getValue());
        List<HorarioDisponivel> horarios = horarioDisponivelRepository.findByAgendaIdAndDiaDaSemanaAndDisponivelTrue(agendaId, diaDaSemana);

        for(HorarioDisponivel horario : horarios){
            if(horario.getHoraInicio().isBefore(horaFim) && horario.getHoraFim().isAfter(horaInicio)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<HorarioDisponivelDTO> listarHorariosDisponiveisPorData(Long agendaId, LocalDate data) {
        Agenda agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new AgendaNaoEncontradaException("Agenda não encontrada"));

        DiasDaSemanaEnum diaDaSemana = buscarDiaDaSemanaPorValor(data.getDayOfWeek().getValue());

        List<HorarioDisponivel> horariosDisponiveis = horarioDisponivelRepository.findByAgendaIdAndDiaDaSemanaAndDisponivelTrue(agendaId, diaDaSemana);

        return horariosDisponiveis.stream().map(HorarioDisponivelDTO::new).collect(Collectors.toList());
    }

    private DiasDaSemanaEnum buscarDiaDaSemanaPorValor(int valor){
        for(DiasDaSemanaEnum dia : DiasDaSemanaEnum.values()){
            if(dia.getValor().equals(valor)){
                return dia;
            }
        }
        throw new DataInvalidaException("Dia da semana inválido");
    }
}
