package beom.moondoserver.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class DifficultyConverter implements AttributeConverter<Difficulty, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Difficulty attribute) {
        if(attribute == null)
            return 0;
        return attribute.getDifficulty();
    }

    @Override
    public Difficulty convertToEntityAttribute(Integer dbData) {
        for (Difficulty d : Difficulty.values()) {
            if (d.getDifficulty() == dbData)
                return d;
        }
        throw new IllegalArgumentException("Invalid database value for Difficulty: " + dbData);
    }
}
