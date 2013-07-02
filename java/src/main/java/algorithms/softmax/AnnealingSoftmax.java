package algorithms.softmax;

import static java.lang.Math.exp;
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

public class AnnealingSoftmax implements Algorithm {
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

    public AnnealingSoftmax(int nArms) {
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
        BigDecimal temperature = ONE.divide(valueOf(log(t + 0.0000001)), MC);

        BigDecimal z = sumScaled(this.values, temperature);
        List<BigDecimal> probs = new ArrayList<BigDecimal>(this.values.size());
        for (BigDecimal value : this.values) {
            probs.add(valueOf(exp(value.divide(temperature, MC).doubleValue())).divide(z, MC));
        }
        return categoricalDraw(probs);
    }

    @Override
    public void update(int chosenArm, BigDecimal reward) {
        // Update counts
        long count = this.counts.get(chosenArm);
        long newCount = count + 1;
        this.counts.set(chosenArm, newCount);

        // Update values
        BigDecimal value = this.values.get(chosenArm);
        BigDecimal n = BigDecimal.valueOf(newCount);
        BigDecimal nMinusOne = n.subtract(ONE);
        BigDecimal newValue = nMinusOne.divide(n, MC).multiply(value).add(ONE.divide(n, MC).multiply(reward));
        this.values.set(chosenArm, newValue);
    }

    private int categoricalDraw(List<BigDecimal> probs) {
        double z = random();
        BigDecimal cumProb = ZERO;
        for (int i = 0; i < probs.size(); i++) {
            BigDecimal prob = probs.get(i);
            cumProb = cumProb.add(prob);
            if (cumProb.doubleValue() > z) {
                return i;
            }
        }
        return probs.size() - 1;
    }

    private BigDecimal sumScaled(List<BigDecimal> items, BigDecimal temperature) {
        BigDecimal sum = ZERO;
        for (BigDecimal item : items) {
            sum = sum.add(valueOf(exp(item.divide(temperature, MC).doubleValue())));
        }
        return sum;
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
