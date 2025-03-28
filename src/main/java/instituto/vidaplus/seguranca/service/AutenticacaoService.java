package instituto.vidaplus.seguranca.service;

import instituto.vidaplus.seguranca.dto.RequisicaoLogin;
import instituto.vidaplus.seguranca.dto.RequisicaoRegistro;
import instituto.vidaplus.seguranca.model.Usuario;

public interface AutenticacaoService {
    Usuario registrarUsuario(RequisicaoRegistro requisicaoRegistro);
    String loginUsuario(RequisicaoLogin requisicaoLogin);
}
