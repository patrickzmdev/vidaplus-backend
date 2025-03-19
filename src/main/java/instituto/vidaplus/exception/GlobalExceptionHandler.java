package instituto.vidaplus.exception;

import instituto.vidaplus.administrador.exception.AdministradorNaoEncontradoException;
import instituto.vidaplus.agenda.exception.AgendaNaoEncontradaException;
import instituto.vidaplus.exame.exception.ExameNaoEncontradoException;
import instituto.vidaplus.exception.genericas.*;
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
import instituto.vidaplus.suprimento.exception.QuantidadeSuprimentoException;
import instituto.vidaplus.suprimento.exception.SuprimentoNaoEncontradoException;
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

    @ExceptionHandler(AdministradorNaoEncontradoException.class)
    public ResponseEntity<String> handleAdministradorNaoEncontradoException(AdministradorNaoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
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
}
