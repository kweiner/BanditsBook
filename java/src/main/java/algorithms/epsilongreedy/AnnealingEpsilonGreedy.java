package algorithms.epsilongreedy;

import static java.lang.Math.log;
import static java.lang.Math.random;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.util.Collections.nCopies;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import algorithms.Algorithm;

public class AnnealingEpsilonGreedy implements Algorithm {
    private static final MathContext MC = MathContext.DECIMAL32;

    /**
     * How many times we've played each of the N arms available to us. If there
     * are 2 arms, Arm 1 and Arm 2, which have both been played twice, then
     * we'll set counts = [2, 2].
     */
    private List<Long> counts;

    /**
     * Defines the average amount of reward we've gotten when playing each of
     * the N arms available to us. If Arm 1 gave us 1 unit of reward on one play
     * and 0 on another play, while Arm 2 gave us 0 units of reward on both
     * plays, then we'll set the values to [0.5, 0.0].
     */
    private List<BigDecimal> values;

    public AnnealingEpsilonGreedy(int nArms) {
        initialize(nArms);
    }

    @Override
    public void initialize(int nArms) {
        this.counts = new ArrayList<Long>(nCopies(nArms, 0L));
        this.values = new ArrayList<BigDecimal>(nCopies(nArms, ZERO));
    }

    @Override
    public int selectArm() {
        long t = sum(this.counts) + 1;
        BigDecimal epsilon = ONE.divide(valueOf(log(t + 0.0000001)), MC);

        if (random() > epsilon.doubleValue()) {
            // Exploiting
            return indexOfMax(this.values);
        }
        // Exploring
        return (int) (random() * this.values.size());
    }

    @Override
    public void update(int chosenArm, BigDecimal reward) {
        // Update counts
        long count = this.counts.get(chosenArm);
        long newCount = count + 1;
        this.counts.set(chosenArm, newCount);

        // Update values
        BigDecimal value = this.values.get(chosenArm);
        BigDecimal n = valueOf(newCount);
        BigDecimal nMinusOne = n.subtract(ONE);
        BigDecimal newValue = nMinusOne.divide(n, MC).multiply(value).add(ONE.divide(n, MC).multiply(reward));
        this.values.set(chosenArm, newValue);
    }

    /**
     * Finds the index of the largest item in items.
     * @param items a list of items to search
     * @return the index of the largest item
     */
    private int indexOfMax(List<BigDecimal> items) {
        int indexOfMax = 0;
        BigDecimal maxValue = ZERO;
        for (int i = 0; i < items.size(); i++) {
            BigDecimal item = items.get(i);
            if (item.compareTo(maxValue) > 0) {
                maxValue = item;
                indexOfMax = i;
            }
        }
        return indexOfMax;
    }

    /**
     * Finds the sum of the items.
     * @param items a list of items to sum
     * @return the sum of the items
     */
    private long sum(List<Long> items) {
        long sum = 0;
        for (Long item : items) {
            sum += item;
        }
        return sum;
    }
}
