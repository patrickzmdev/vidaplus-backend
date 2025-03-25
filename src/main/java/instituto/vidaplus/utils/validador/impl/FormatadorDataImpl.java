package instituto.vidaplus.utils.validador.impl;

import instituto.vidaplus.utils.validador.FormatadorData;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class FormatadorDataImpl implements FormatadorData {
    @Override
    public String formatarData(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }
}
