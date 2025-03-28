package instituto.vidaplus.email.service;

import instituto.vidaplus.consulta.model.Consulta;
import instituto.vidaplus.exame.model.Exame;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.seguranca.model.Usuario;

public interface EmailService {
    void confirmacaoConsulta(Paciente paciente, String assunto, Consulta consulta);
    void confirmacaoExame(Paciente paciente, String assunto, Exame exame);
    void confirmacaoCadastro(Usuario usuario, String assunto, Usuario usuarioCadastro);
    void enviarCodigoDeRecuperacao(Usuario usuario, String codigo);
    void enviarEmail(String email, String assunto);
}
