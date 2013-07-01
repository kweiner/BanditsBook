package algorithms;

import static java.math.BigDecimal.ZERO;
import static java.util.Collections.nCopies;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import arms.Arm;

/**
 * Used to test algorithms with different configurations.
 * @author Ken Weiner
 */
public class Tests {
    public static TestResults testAlgorithm(Algorithm algo, List<Arm> arms, int numSims, int horizon) {
        int n = numSims * horizon;
        List<Integer> chosenArms = new ArrayList<Integer>(nCopies(n, 0));
        List<BigDecimal> rewards = new ArrayList<BigDecimal>(nCopies(n, ZERO));
        List<BigDecimal> cumulativeRewards = new ArrayList<BigDecimal>(nCopies(n, ZERO));
        List<Integer> simNums = new ArrayList<Integer>(nCopies(n, 0));
        List<Integer> times = new ArrayList<Integer>(nCopies(n, 0));

        for (int sim = 1; sim < numSims + 1; sim++) {
            algo.initialize(arms.size());
            for (int t = 1; t < horizon + 1; t++) {
                int index = (sim - 1) * horizon + t - 1;
                simNums.set(index, sim);
                times.set(index, t);

                int chosenArm = algo.selectArm();
                chosenArms.set(index, chosenArm);

                BigDecimal reward = arms.get(chosenArms.get(index)).draw();
                rewards.set(index, reward);

                if (t == 1) {
                    cumulativeRewards.set(index, reward);
                } else {
                    cumulativeRewards.set(index, cumulativeRewards.get(index - 1).add(reward));
                }

                algo.update(chosenArm, reward);
            }
        }
        return new TestResults(simNums, times, chosenArms, rewards, cumulativeRewards);
    }
}
