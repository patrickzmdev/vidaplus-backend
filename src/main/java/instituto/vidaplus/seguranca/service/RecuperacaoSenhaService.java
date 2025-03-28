package instituto.vidaplus.seguranca.service;

public interface RecuperacaoSenhaService {
    void requisitarRecuperacaoSenha(String email);
    Boolean validarSenhaDeRecuperacao(String email, String senha);
    void resetarSenha(String email, String codigo, String novaSenha);
}
