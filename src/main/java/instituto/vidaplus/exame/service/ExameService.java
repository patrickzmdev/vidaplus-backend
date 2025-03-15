package instituto.vidaplus.exame.service;

import instituto.vidaplus.exame.dto.ExameDTO;
import instituto.vidaplus.exame.dto.ExameSuprimentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExameService {
    ExameDTO cadastrarExame(Long profissionalId, ExameDTO exameDTO);
    ExameDTO editarExame(Long id, ExameDTO exameDTO);
    ExameDTO buscarExame(Long id);
    Page<ExameDTO> buscarExames(Pageable pageable);
    String deletarExame(Long id);
    String finalizarExame(Long id);
    String cancelarExame(Long id);
    ExameSuprimentoDTO adicionarSuprimentoAExame(Long exameId, Long suprimentoId, Integer quantidadeUtilizada);
}

//nao esquecer
//@Transactional
//public void adicionarSuprimentoAExame(Long exameId, Long suprimentoId, Integer quantidadeUtilizada) {
//    // Busca o exame e o suprimento
//    Exame exame = exameRepository.findById(exameId)
//            .orElseThrow(() -> new RuntimeException("Exame não encontrado"));
//    Suprimento suprimento = suprimentoRepository.findById(suprimentoId)
//            .orElseThrow(() -> new RuntimeException("Suprimento não encontrado"));
//
//    // Verifica se há estoque suficiente
//    if (suprimento.getQuantidade() < quantidadeUtilizada) {
//        throw new RuntimeException("Estoque insuficiente para o suprimento: " + suprimento.getNome());
//    }
//
//    // Atualiza o estoque do suprimento
//    suprimento.setQuantidade(suprimento.getQuantidade() - quantidadeUtilizada);
//    suprimentoRepository.save(suprimento);
//
//    // Cria o registro na tabela associativa
//    ExameSuprimento exameSuprimento = new ExameSuprimento();
//    exameSuprimento.setExame(exame);
//    exameSuprimento.setSuprimento(suprimento);
//    exameSuprimento.setQuantidadeUtilizada(quantidadeUtilizada);
//    exameSuprimentoRepository.save(exameSuprimento);
//}
//}
