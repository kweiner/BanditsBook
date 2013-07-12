package algorithms.ucb;

import static java.math.BigDecimal.valueOf;
import static java.util.Collections.shuffle;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import algorithms.Algorithm;
import algorithms.TestResults;
import algorithms.Tests;
import arms.Arm;
import arms.BernoulliArm;

public class Ucb1Test {
    @Test
    public void test() throws Exception {
        float[] means = new float[] { 0.1f, 0.1f, 0.1f, 0.1f, 0.9f };
        List<Arm> arms = newArms(means);
        shuffle(arms, new Random(1L));
        System.out.println("Best arm is " + indexOfMax(means));

        PrintWriter writer = new PrintWriter("ucb1_results.csv", "UTF-8");
        final String delim = ",";

        Algorithm algo = new Ucb1(arms.size());
        TestResults results = Tests.testAlgorithm(algo, arms, 5000, 250);
        for (int i = 0; i < results.getSimNums().size(); i++) {
            writer.print(results.getSimNums().get(i) + delim);
            writer.print(results.getTimes().get(i) + delim);
            writer.print(results.getChosenArms().get(i) + delim);
            writer.print(results.getRewards().get(i) + delim);
            writer.print(results.getCumulativeRewards().get(i) + "\n");
        }

        writer.close();
    }

    /**
     * Constructs a list of bernoulli arms with given means.
     * @param means the array of means
     * @return the arms with the given means
     */
    private List<Arm> newArms(float[] means) {
        List<Arm> arms = new ArrayList<Arm>();
        for (float mean : means) {
            arms.add(new BernoulliArm(valueOf(mean)));
        }
        return arms;
    }

    /**
     * Finds the index of the largest item in items.
     * @param items a list of items to search
     * @return the index of the largest item
     */
    private int indexOfMax(float[] items) {
        int indexOfMax = 0;
        float maxValue = 0f;
        for (int i = 0; i < items.length; i++) {
            float item = items[i];
            if (item > maxValue) {
                maxValue = item;
                indexOfMax = i;
            }
        }
        return indexOfMax;
    }
}
