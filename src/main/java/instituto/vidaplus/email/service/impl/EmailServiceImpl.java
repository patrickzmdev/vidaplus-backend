package instituto.vidaplus.email.service.impl;

import instituto.vidaplus.consulta.model.Consulta;
import instituto.vidaplus.email.service.EmailService;
import instituto.vidaplus.exame.model.Exame;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.seguranca.model.Usuario;
import instituto.vidaplus.utils.validador.FormatadorData;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final FormatadorData formatadorData;

    @Override
    public void confirmacaoConsulta(Paciente paciente, String assunto, Consulta consulta) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(paciente.getEmail());
        message.setSubject(assunto);
        message.setText("Olá " + paciente.getNome() + ",\n\n" +
                "Sua consulta foi agendada com sucesso!\n" +
                "Data: " + formatadorData.formatarData(consulta.getData()) + "\n" +
                "Atenciosamente,\n" +
                "Instituto Vida Plus");
        mailSender.send(message);
    }

    @Override
    public void confirmacaoExame(Paciente paciente, String assunto, Exame exame) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(paciente.getEmail());
        message.setSubject(assunto);
        message.setText("Olá " + paciente.getNome() + ",\n\n" +
                "Seu exame foi confirmado com sucesso!\n" +
                "Data: " + formatadorData.formatarData(exame.getDataAgendamento()) + "\n" +
                "Exame: " + exame.getTipoExame().getDescricao() + "\n" +
                "Atenciosamente,\n" +
                "Instituto Vida Plus");
        mailSender.send(message);
    }

    @Override
    public void confirmacaoCadastro(Usuario usuario, String assunto, Usuario usuarioCadastro) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(usuarioCadastro.getEmail());
        message.setSubject(assunto);
        message.setText("Olá " + usuarioCadastro.getUsername() + ",\n\n" +
                "Seu cadastro foi realizado com sucesso!\n" +
                "Nome de usuário: " + usuario.getUsername() + "\n" +
                "Atenciosamente,\n" +
                "Instituto Vida Plus");
        mailSender.send(message);
    }

    @Override
    public void enviarCodigoDeRecuperacao(Usuario usuario, String codigo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(usuario.getEmail());
        message.setSubject("Código de Recuperação de Senha");
        message.setText("Olá " + usuario.getUsername() + ",\n\n" +
                "Seu código de recuperação de senha é: " + codigo + "\n" +
                "Atenciosamente,\n" +
                "Instituto Vida Plus");
        mailSender.send(message);
    }

    @Override
    public void enviarEmail(String email, String assunto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(assunto);
        message.setText("Olá,\n\n" +
                "Este é um email de teste.\n" +
                "Atenciosamente,\n" +
                "Instituto Vida Plus");
        mailSender.send(message);
    }
}
