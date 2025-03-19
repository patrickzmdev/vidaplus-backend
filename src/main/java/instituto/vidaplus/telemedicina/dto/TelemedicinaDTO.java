package instituto.vidaplus.telemedicina.dto;

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
}
