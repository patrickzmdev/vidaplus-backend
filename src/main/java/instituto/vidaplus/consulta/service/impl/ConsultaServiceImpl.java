package instituto.vidaplus.consulta.service.impl;

import instituto.vidaplus.consulta.dto.ConsultaDTO;
import instituto.vidaplus.consulta.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultaServiceImpl implements ConsultaService {
    @Override
    public ConsultaDTO criarConsulta(ConsultaDTO consultaDTO) {
        return null;
    }

    @Override
    public ConsultaDTO buscarConsultaPorId(Long id) {
        return null;
    }

    @Override
    public Page<ConsultaDTO> listarConsultas(Pageable pageable) {
        return null;
    }

    @Override
    public ConsultaDTO atualizarConsulta(Long id, ConsultaDTO consultaDTO) {
        return null;
    }

    @Override
    public String deletarConsulta(Long id) {
        return "";
    }

    @Override
    public ConsultaDTO converterParaTelemedicina(Long consultaId, String linkVideoChamada) {
        return null;
    }

    @Override
    public ConsultaDTO removerTelemedicina(Long consultaId) {
        return null;
    }

    @Override
    public ConsultaDTO atualizarLinkTelemedicina(Long consultaId, String novoLink) {
        return null;
    }

    @Override
    public Boolean isConsultaTelemedicina(Long consultaId) {
        return null;
    }

    @Override
    public List<ConsultaDTO> buscarConsultasPorPaciente(Long pacienteId) {
        return List.of();
    }

    @Override
    public List<ConsultaDTO> buscarConsultasPorProfissional(Long profissionalId) {
        return List.of();
    }
}
