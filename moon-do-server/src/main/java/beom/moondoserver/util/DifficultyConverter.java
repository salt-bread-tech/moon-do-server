package beom.moondoserver.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class DifficultyConverter implements AttributeConverter<Difficulty, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Difficulty attribute) {
        if(attribute == null)
            return 0;
        return attribute.getValue();
    }

    @Override
    public Difficulty convertToEntityAttribute(Integer dbData) {
        if (dbData == null)
            return null;
        return Difficulty.ofValue(dbData);
    /*
        for (Difficulty d : Difficulty.values()) {
            if (d.getValue() == dbData)
                return d;
        }
        throw new IllegalArgumentException("Invalid database value for Difficulty: " + dbData);
    */
    }
}
