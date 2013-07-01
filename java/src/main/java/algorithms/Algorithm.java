package algorithms;

import java.math.BigDecimal;

public interface Algorithm {
    /**
     * Resets internal counts and values.
     * @param nArms the number of arms
     */
    void initialize(int nArms);

    /**
     * Selects the next arm to pull.
     * @return the numeric index of the arm we should pull next
     */
    int selectArm();

    /**
     * Updates the quality of an arm by providing reward information.
     * @param chosenArm the chosen arm
     * @param reward the reward
     */
    void update(int chosenArm, BigDecimal reward);
}
