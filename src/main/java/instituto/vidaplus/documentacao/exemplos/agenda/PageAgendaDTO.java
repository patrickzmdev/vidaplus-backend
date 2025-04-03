package instituto.vidaplus.documentacao.exemplos.agenda;

import instituto.vidaplus.agenda.dto.AgendaDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "PageAgendaDTO", description = "Modelo de exemplo para a página de agenda")
public class PageAgendaDTO {

    @Schema(description = "Lista de agendas na página atual")
    private List<AgendaDTO> content;

    @Schema(description = "Informações de paginação")
    private Object pageable;

    @Schema(description = "Número total de elementos")
    private long totalElements;

    @Schema(description = "Número total de páginas")
    private int totalPages;
}
