package instituto.vidaplus.telemedicina.dto;

import instituto.vidaplus.prontuario.model.Prontuario;
import instituto.vidaplus.receita.model.Receita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelemedicinaDTO {

    private Long id;
    private Long consultaId;
    private List<Receita> receitas = new ArrayList<>();
    private List<Prontuario> prontuarios = new ArrayList<>();
    private String linkVideoChamada;
    private Boolean videoChamadaSegura;
}
