package instituto.vidaplus.telemedicina.dto;

import instituto.vidaplus.telemedicina.model.Telemedicina;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelemedicinaDTO {

    private Long id;
    private Long consultaId;
    private String linkVideoChamada;
    private Boolean videoChamadaSegura;

    public TelemedicinaDTO(Telemedicina telemedicina) {
        this.id = telemedicina.getId();
        this.consultaId = telemedicina.getConsulta().getId();
        this.linkVideoChamada = telemedicina.getLinkVideoChamada();
        this.videoChamadaSegura = telemedicina.getVideoChamadaSegura();
    }
}
