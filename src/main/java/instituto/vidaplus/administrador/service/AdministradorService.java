package instituto.vidaplus.administrador.service;

import instituto.vidaplus.administrador.model.Administrador;
import instituto.vidaplus.internacao.model.Internacao;
import instituto.vidaplus.leito.model.Leito;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.profissional.model.Profissional;
import instituto.vidaplus.suprimento.model.Suprimento;

import java.time.LocalDate;
import java.util.List;

public interface AdministradorService {
    Administrador cadastrarAdministrador(Administrador administrador);
    String editarAdministrador(Long administradorId, Administrador administrador);
    Administrador buscarAdministrador(Long administradorId);
    void excluirAdministrador(Long administradorId);

    // Gerenciamento de cadastros
    List<Paciente> listarPacientes();
    List<Profissional> listarProfissionais();

    // Controle de internações
    List<Internacao> listarInternacoesAtivas();

    // Gestão de leitos
    List<Leito> listarTodosLeitos();
    List<Leito> listarLeitosDisponiveis();
    List<Leito> listarLeitosOcupados();
    List<Leito> listarLeitosPorUnidade(Long unidadeId);

    // Relatórios
    byte[] gerarRelatorioOcupacao(LocalDate dataInicio, LocalDate dataFim);
    byte[] gerarRelatorioFinanceiro(LocalDate dataInicio, LocalDate dataFim);
    byte[] gerarRelatorioAtendimentos(LocalDate dataInicio, LocalDate dataFim);

    // Gestão de suprimentos
    List<Suprimento> listarSuprimentos();
    List<Suprimento> listarSuprimentosBaixoEstoque();
}
