package beom.moondoserver.util;

import lombok.Getter;

@Getter
public enum Difficulty {
    VERY_EASY(1),
    EASY(2),
    MEDIUM(3),
    HARD(4),
    VERY_DIFFICULT(5);

    private final Integer difficulty;

    Difficulty(Integer difficulty){
        this.difficulty = difficulty;
    }
}
