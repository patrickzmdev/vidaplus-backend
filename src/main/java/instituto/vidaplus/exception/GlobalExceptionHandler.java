package instituto.vidaplus.exception;

import instituto.vidaplus.agenda.exception.AgendaNaoEncontradaException;
import instituto.vidaplus.consulta.exception.ConsultaNaoEncontradaException;
import instituto.vidaplus.exame.exception.ExameNaoEncontradoException;
import instituto.vidaplus.exception.genericas.*;
import instituto.vidaplus.horario.exception.ConflitoDeHorarioException;
import instituto.vidaplus.horario.exception.HorarioNaoEncontradoException;
import instituto.vidaplus.internacao.exception.InternacaoNaoEncontradaException;
import instituto.vidaplus.internacao.exception.MotivoInternacaoException;
import instituto.vidaplus.leito.exception.LeitoJaCadastradoException;
import instituto.vidaplus.leito.exception.LeitoNaoExistenteException;
import instituto.vidaplus.leito.exception.LeitoOcupadoException;
import instituto.vidaplus.paciente.exception.AlergiaJaCadastradaException;
import instituto.vidaplus.paciente.exception.AlergiaNaoEncontradaException;
import instituto.vidaplus.paciente.exception.PacienteJaInternadoException;
import instituto.vidaplus.paciente.exception.PacienteNaoEncontradoException;
import instituto.vidaplus.profissional.exception.ProfissionalNaoEMedicoException;
import instituto.vidaplus.profissional.exception.ProfissionalNaoEncontradoException;
import instituto.vidaplus.profissional.exception.RegistroProfissionalObrigatorio;
import instituto.vidaplus.prontuario.exception.ProntuarioNaoEncontradoException;
import instituto.vidaplus.receita.exception.ItemReceitaNaoEncontradoException;
import instituto.vidaplus.receita.exception.ReceitaNaoEncontradaException;
import instituto.vidaplus.relatorio.exception.ErroEmRelatorioException;
import instituto.vidaplus.seguranca.exception.TokenException;
import instituto.vidaplus.seguranca.exception.UsuarioNaoEncontradoException;
import instituto.vidaplus.suprimento.exception.QuantidadeSuprimentoException;
import instituto.vidaplus.suprimento.exception.SuprimentoNaoEncontradoException;
import instituto.vidaplus.telemedicina.exception.TelemedicinaJaCadastradaException;
import instituto.vidaplus.telemedicina.exception.TelemedicinaNaoEncontradaException;
import instituto.vidaplus.unidade.exception.UnidadeHospitalarNaoEncontradaException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        for (ConstraintViolation<?> violation : violations) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PacienteNaoEncontradoException.class)
    public ResponseEntity<String> handlePacienteNaoEncontradoException(PacienteNaoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CPFInvalidoException.class)
    public ResponseEntity<String> handleCPFInvalidoException(CPFInvalidoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailInvalidoException.class)
    public ResponseEntity<String> handleEmailInvalidoException(EmailInvalidoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TelefoneInvalidoException.class)
    public ResponseEntity<String> handleTelefoneInvalidoException(TelefoneInvalidoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CepInvalidoException.class)
    public ResponseEntity<String> handleCepInvalidoException(CepInvalidoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LeitoNaoExistenteException.class)
    public ResponseEntity<String> handleLeitoNaoExistenteException(LeitoNaoExistenteException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LeitoOcupadoException.class)
    public ResponseEntity<String> handleLeitoOcupadoException(LeitoOcupadoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MotivoInternacaoException.class)
    public ResponseEntity<String> handleMotivoInternacaoException(MotivoInternacaoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternacaoNaoEncontradaException.class)
    public ResponseEntity<String> handleInternacaoNaoEncontradaException(InternacaoNaoEncontradaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DadoUnicoException.class)
    public ResponseEntity<String> handleDadoUnicoException(DadoUnicoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlergiaJaCadastradaException.class)
    public ResponseEntity<String> handleAlergiaJaCadastradaException(AlergiaJaCadastradaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlergiaNaoEncontradaException.class)
    public ResponseEntity<String> handleAlergiaNaoEncontradaException(AlergiaNaoEncontradaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegistroProfissionalObrigatorio.class)
    public ResponseEntity<String> handleRegistroProfissionalObrigatorio(RegistroProfissionalObrigatorio ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfissionalNaoEncontradoException.class)
    public ResponseEntity<String> handleProfissionalNaoEncontradoException(ProfissionalNaoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnidadeHospitalarNaoEncontradaException.class)
    public ResponseEntity<String> handleUnidadeHospitalarNaoEncontradaException(UnidadeHospitalarNaoEncontradaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(QuantidadeSuprimentoException.class)
    public ResponseEntity<String> handleQuantidadeSuprimentoException(QuantidadeSuprimentoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SuprimentoNaoEncontradoException.class)
    public ResponseEntity<String> handleSuprimentoNaoEncontradoException(SuprimentoNaoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LeitoJaCadastradoException.class)
    public ResponseEntity<String> handleLeitoJaCadastradoException(LeitoJaCadastradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfissionalNaoEMedicoException.class)
    public ResponseEntity<String> handleProfissionalNaoEMedicoException(ProfissionalNaoEMedicoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PacienteJaInternadoException.class)
    public ResponseEntity<String> handlePacienteJaInternadoException(PacienteJaInternadoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExameNaoEncontradoException.class)
    public ResponseEntity<String> handleExameNaoEncontradoException(ExameNaoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataInvalidaException.class)
    public ResponseEntity<String> handleDataInvalidaException(DataInvalidaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AgendaNaoEncontradaException.class)
    public ResponseEntity<String> handleAgendaNaoEncontradaException(AgendaNaoEncontradaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HorarioNaoEncontradoException.class)
    public ResponseEntity<String> handleHorarioNaoEncontradoException(HorarioNaoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConsultaNaoEncontradaException.class)
    public ResponseEntity<String> handleConsultaNaoEncontradaException(ConsultaNaoEncontradaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TelemedicinaJaCadastradaException.class)
    public ResponseEntity<String> handleTelemedicinaJaCadastradaException(TelemedicinaJaCadastradaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TelemedicinaNaoEncontradaException.class)
    public ResponseEntity<String> handleTelemedicinaNaoEncontradaException(TelemedicinaNaoEncontradaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflitoDeHorarioException.class)
    public ResponseEntity<String> handleConflitoDeHorarioException(ConflitoDeHorarioException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProntuarioNaoEncontradoException.class)
    public ResponseEntity<String> handleProntuarioNaoEncontradoException(ProntuarioNaoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ErroAoGerarPdfException.class)
    public ResponseEntity<String> handleErroAoGerarPdfException(ErroAoGerarPdfException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ReceitaNaoEncontradaException.class)
    public ResponseEntity<String> handleReceitaNaoEncontradaException(ReceitaNaoEncontradaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemReceitaNaoEncontradoException.class)
    public ResponseEntity<String> handleItemReceitaNaoEncontradoException(ItemReceitaNaoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ErroEmRelatorioException.class)
    public ResponseEntity<String> handleErroEmRelatorioException(ErroEmRelatorioException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<String> handleTokenException(TokenException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<String> handleUsuarioNaoEncontradoException(UsuarioNaoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<String> handleSenhaInvalidaException(SenhaInvalidaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsuarioJaCadastradoException.class)
    public ResponseEntity<String> handleUsuarioJaCadastradoException(UsuarioJaCadastradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<String> handleEmailJaCadastradoException(EmailJaCadastradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
