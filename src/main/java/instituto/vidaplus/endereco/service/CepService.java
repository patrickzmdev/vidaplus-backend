package instituto.vidaplus.endereco.service;

import instituto.vidaplus.endereco.dto.EnderecoDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CepService {

    private static final String VIACEP_URL = "https://viacep.com.br/ws/%s/json/";

    public EnderecoDTO buscarEnderecoPorCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(VIACEP_URL, cep.replaceAll("\\D", ""));

        return restTemplate.getForObject(url, EnderecoDTO.class);
    }
}
