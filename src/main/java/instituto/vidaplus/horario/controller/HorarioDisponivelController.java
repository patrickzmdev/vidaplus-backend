package instituto.vidaplus.horario.controller;

import instituto.vidaplus.horario.dto.HorarioDisponivelDTO;
import instituto.vidaplus.horario.service.HorarioDisponivelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/horarios")
@RequiredArgsConstructor
public class HorarioDisponivelController {

    private final HorarioDisponivelService horarioDisponivelService;

    @Operation(summary = "Criar horário disponível",
            description = "Cria um novo horário disponível para uma agenda específica")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do horário disponível",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HorarioDisponivelDTO.class),
                    examples = @ExampleObject(
                            value = "{\"diaDaSemana\": \"SEGUNDA\", \"horaInicio\": \"08:00\", \"horaFim\": \"09:00\", \"duracaoMediaEmMinutos\": 30, \"disponivel\": true}"
                    )
            )
    )
    @ApiResponse(responseCode = "201",
            description = "Horário criado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HorarioDisponivelDTO.class),
                    examples = @ExampleObject(
                            value = "{\"diaDaSemana\": \"SEGUNDA\", \"horaInicio\": \"08:00\", \"horaFim\": \"09:00\", \"duracaoMediaEmMinutos\": 30, \"disponivel\": true}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Agenda não encontrada",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Agenda não encontrada\"}"
                    )
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<HorarioDisponivelDTO> criarHorarioDisponivel(
            @Parameter(description = "Id da agenda", example = "1", required = true) @RequestParam Long agendaId, @RequestBody HorarioDisponivelDTO horarioDisponivelDTO) {
        HorarioDisponivelDTO horarioAdicionado = horarioDisponivelService.criarHorarioDisponivel(agendaId,horarioDisponivelDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(horarioAdicionado);
    }

    @Operation(summary = "Buscar horário por ID",
            description = "Busca um horário disponível pelo seu ID")
    @ApiResponse(responseCode = "200",
            description = "Horário encontrado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HorarioDisponivelDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"agendaId\": 1, \"diaDaSemana\": \"SEGUNDA\", \"horaInicio\": \"08:00:00\", \"horaFim\": \"09:00:00\",\"duracaoMediaEmMinutos\": 30, \"disponivel\": true}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Horário não encontrado",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Horário não encontrado\"}"
                    )
            )
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<HorarioDisponivelDTO> buscarHorarioDisponivelPorId(
            @Parameter(description = "Id do horario", example = "1", required = true)@PathVariable Long id) {
        HorarioDisponivelDTO horarioBuscado = horarioDisponivelService.buscarHorarioDisponivelPorId(id);
        return ResponseEntity.ok(horarioBuscado);
    }

    @Operation(summary = "Listar todos horários",
            description = "Lista todos os horários disponíveis com paginação")
    @ApiResponse(responseCode = "200",
            description = "Horários listados com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HorarioDisponivelDTO.class),
                    examples = @ExampleObject(
                            value = "{\"content\":[{\"id\": 1, \"agendaId\": 1, \"diaDaSemana\": \"SEGUNDA\", \"horaInicio\": \"08:00:00\", \"horaFim\": \"09:00:00\", \"duracaoMediaEmMinutos\": 30, \"disponivel\": true}], \"pageable\":{\"pageNumber\":0,\"pageSize\":20}, \"totalElements\":1, \"totalPages\":1}"
                    )
            )
    )
    @Parameter(name = "page", description = "Número da página (0..N)", schema = @Schema(type = "integer", defaultValue = "0"))
    @Parameter(name = "size", description = "Tamanho da página", schema = @Schema(type = "integer", defaultValue = "20"))
    @Parameter(name = "sort", description = "Ordenação: propriedade,direção (ex: id,asc)", schema = @Schema(type = "string", defaultValue = "id,asc"))
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<HorarioDisponivelDTO> listarHorarios(Pageable pageable) {
        return horarioDisponivelService.listarHorarios(pageable);
    }

    @Operation(summary = "Deletar horário",
            description = "Remove um horário disponível do sistema")
    @ApiResponse(responseCode = "200",
            description = "Horário removido com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Horário deletado com sucesso\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Horário não encontrado",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Horário não encontrado com o id: 1\"}"
                    )
            )
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String deletarHorarioDisponivel(
            @Parameter(description = "Id do horario", example = "1", required = true)@PathVariable Long id) {
        return horarioDisponivelService.deletarHorarioDisponivel(id);
    }

    @Operation(summary = "Listar horários por agenda",
            description = "Lista todos os horários de uma agenda específica")
    @ApiResponse(responseCode = "200",
            description = "Horários listados com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HorarioDisponivelDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"agendaId\": 1, \"diaDaSemana\": \"SEGUNDA\", \"horaInicio\": \"08:00:00\", \"horaFim\": \"09:00:00\",\"duracaoMediaEmMinutos\": 30, \"disponivel\": true}"
                    )
            )
    )
    @GetMapping("/agenda/{agendaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<HorarioDisponivelDTO>> listarHorariosPorAgenda(
            @Parameter(description = "Id da agenda", example = "1", required = true)@PathVariable Long agendaId) {
        List<HorarioDisponivelDTO> horariosPorAgenda = horarioDisponivelService.listarHorariosPorAgenda(agendaId);
        return ResponseEntity.ok(horariosPorAgenda);
    }

    @Operation(summary = "Listar horários por agenda",
            description = "Lista todos os horários de uma agenda específica")
    @ApiResponse(responseCode = "200",
            description = "Horários listados com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HorarioDisponivelDTO.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1, \"agendaId\": 1, \"diaDaSemana\": \"SEGUNDA\", \"horaInicio\": \"08:00:00\", \"horaFim\": \"09:00:00\",\"duracaoMediaEmMinutos\": 30, \"disponivel\": true}"
                    )
            )
    )
    @GetMapping("/agenda/{agendaId}/disponiveis")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<HorarioDisponivelDTO>> listarHorariosDisponiveisPorAgenda(
            @Parameter(description = "Id da agenda", example = "1", required = true) @PathVariable Long agendaId) {
        List<HorarioDisponivelDTO> horariosDisponiveisPorAgenda = horarioDisponivelService.listarHorariosDisponiveisPorAgenda(agendaId);
        return ResponseEntity.ok(horariosDisponiveisPorAgenda);
    }

    @Operation(summary = "Marcar horário como indisponível",
            description = "Altera o status de um horário para indisponível")
    @ApiResponse(responseCode = "200",
            description = "Horário marcado como indisponível com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Horário marcado como indisponível\"}"
                    )
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Horário não encontrado",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"mensagem\": \"Horário não encontrado\"}"
                    )
            )
    )
    @PutMapping("/{id}/indisponivel")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String marcarHorarioComoIndisponivel(@Parameter(description = "Id do horário", example = "1", required = true) @PathVariable Long id) {
        return horarioDisponivelService.marcarHorarioComoIndisponivel(id);
    }


    @PutMapping("/{id}/disponivel")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String marcarHorarioComoDisponivel(@Parameter(description = "Id do horario", example = "1", required = true)@PathVariable Long id) {
        return horarioDisponivelService.marcarHorarioComoDisponivel(id);
    }

    @Operation(summary = "Verificar disponibilidade de horário",
            description = "Verifica se um determinado horário está disponível para agendamento")
    @ApiResponse(responseCode = "200",
            description = "Verificação realizada com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "true"
                    )
            )
    )

    @GetMapping("/agenda/{agendaId}/disponibilidade")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Boolean verificarDisponibilidadeDeHorario(
            @Parameter(description = "ID da agenda", example = "1", required = true) @PathVariable Long agendaId,
            @Parameter(description = "Data do horário", example = "2025-10-15", required = true) @RequestParam LocalDate data,
            @Parameter(description = "Hora de início", example = "08:00:00", required = true) @RequestParam LocalTime horaInicio,
            @Parameter(description = "Hora de fim", example = "09:00:00", required = true) @RequestParam LocalTime horaFim){
        return horarioDisponivelService.verificarDisponibilidadeDeHorario(agendaId, data, horaInicio, horaFim);
    }

    @Operation(summary = "Listar horários disponíveis por data",
            description = "Lista os horários disponíveis de uma agenda para uma data específica")
    @ApiResponse(responseCode = "200",
            description = "Horários disponíveis listados com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HorarioDisponivelDTO.class),
                    examples = @ExampleObject(
                            value = "[{\"id\": 1, \"agendaId\": 1, \"diaDaSemana\": \"SEGUNDA\", \"horaInicio\": \"08:00:00\", \"horaFim\": \"09:00:00\",\"duracaoMediaEmMinutos\": 30, \"disponivel\": true}]"
                    )
            )
    )
    @GetMapping("/agenda/{agendaId}/disponiveis/data")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<HorarioDisponivelDTO>> listarHorariosDisponiveisPorData(
            @Parameter(description = "ID da agenda", example = "1", required = true) @PathVariable Long agendaId,
            @Parameter(description = "Data para busca", example = "2023-10-15", required = true) @RequestParam LocalDate data) {
        List<HorarioDisponivelDTO> horariosDisponiveisPorData = horarioDisponivelService.listarHorariosDisponiveisPorData(agendaId, data);
        return ResponseEntity.ok(horariosDisponiveisPorData);
    }

}
