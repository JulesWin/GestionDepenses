package projet.gestiondepenses;

import org.springframework.core.convert.converter.Converter;

public class StringToLongConverter implements Converter<String, Long> {

    @Override
    public Long convert(String source) {
        try {
            return Long.parseLong(source);
        } catch (NumberFormatException e) {
            // Gérer l'erreur de conversion
            throw new IllegalArgumentException("Invalid format for ID");
        }
    }
}
