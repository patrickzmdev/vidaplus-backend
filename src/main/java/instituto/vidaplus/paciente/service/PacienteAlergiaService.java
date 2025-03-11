package instituto.vidaplus.paciente.service;

import instituto.vidaplus.paciente.enums.AlergiaEnum;

public interface PacienteAlergiaService {
    void adicionarAlergia(Long pacienteId, AlergiaEnum alergia, String observacao);
    void removerAlergia(Long alergiaId);
    boolean pacientePossuiAlergias(Long pacienteId);
}
