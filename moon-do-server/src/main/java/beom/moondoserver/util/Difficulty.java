package beom.moondoserver.util;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Difficulty {
    BEGINNER(1, "Beginner"),
    EASY(2, "Easy"),
    NORMAL(3, "Normal"),
    HARD(4, "Hard"),
    MASTER(5, "Master");

    private final Integer value;
    private final String difficultyValue;

    Difficulty(Integer value, String difficultyValue){
        this.value = value;
        this.difficultyValue = difficultyValue;
    }

    public static Difficulty ofValue(Integer value){
        return Arrays.stream(Difficulty.values())
                .filter(v -> v.getValue().equals(value))
                .findAny().orElse(null);
    }
}
