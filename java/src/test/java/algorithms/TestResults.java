package algorithms;

import java.math.BigDecimal;
import java.util.List;

/**
 * Holds the results of algorithm tests.
 * @author Ken Weiner
 */
public class TestResults {
    private List<Integer> simNums;
    private List<Integer> times;
    private List<Integer> chosenArms;
    private List<BigDecimal> rewards;
    private List<BigDecimal> cumulativeRewards;

    public TestResults(List<Integer> simNums, List<Integer> times, List<Integer> chosenArms, List<BigDecimal> rewards,
            List<BigDecimal> cumulativeRewards) {
        this.simNums = simNums;
        this.times = times;
        this.chosenArms = chosenArms;
        this.rewards = rewards;
        this.cumulativeRewards = cumulativeRewards;
    }

    public List<Integer> getSimNums() {
        return simNums;
    }

    public List<Integer> getTimes() {
        return times;
    }

    public List<Integer> getChosenArms() {
        return chosenArms;
    }

    public List<BigDecimal> getRewards() {
        return rewards;
    }

    public List<BigDecimal> getCumulativeRewards() {
        return cumulativeRewards;
    }
}
