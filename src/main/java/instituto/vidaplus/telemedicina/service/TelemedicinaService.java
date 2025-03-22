package instituto.vidaplus.telemedicina.service;

import instituto.vidaplus.telemedicina.dto.TelemedicinaDTO;

public interface TelemedicinaService {
    TelemedicinaDTO criarTelemedicina(Long consultaId);
    TelemedicinaDTO buscarTelemedicinaPorConsultaId(Long consultaId);
    TelemedicinaDTO atualizarLinkTelemedicina(Long telemedicinaId);
    String deletarTelemedicina(Long telemedicinaId);
    Boolean consultaEstaEmTelemedicina(Long consultaId);
    TelemedicinaDTO iniciarTelemedicina(Long telemedicinaId);
    TelemedicinaDTO finalizarTelemedicina(Long telemedicinaId);
}
