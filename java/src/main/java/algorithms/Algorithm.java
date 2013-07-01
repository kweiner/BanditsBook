package algorithms;

import java.math.BigDecimal;

public interface Algorithm {
    /**
     * Selects the next arm to pull.
     * @return the numeric index of the arm we should pull next
     */
    int selectArm();

    /**
     * Updates the quality of an arm by providing reward information.
     */
    void update(int chosenArm, BigDecimal reward);
}
