package arms;

import static java.lang.Math.random;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;

/**
 * Rewards a 1 some percentage of the time, and a 0 the rest.
 * @author Ken Weiner
 */
public class BernoulliArm implements Arm {
    private BigDecimal probability;

    /**
     * Constructs an arm.
     * @param probability the probability of this arm returning a 1
     */
    public BernoulliArm(BigDecimal probability) {
        this.probability = probability;
    }

    @Override
    public BigDecimal draw() {
        if (random() > this.probability.doubleValue()) {
            return ZERO;
        }
        return ONE;
    }
}
