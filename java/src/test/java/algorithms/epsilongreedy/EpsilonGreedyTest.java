package algorithms.epsilongreedy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import algorithms.Algorithm;
import algorithms.TestResults;
import algorithms.Tests;
import arms.Arm;
import arms.BernoulliArm;

public class EpsilonGreedyTest {
    @Test
    public void test1() {
        BigDecimal epsilon = BigDecimal.valueOf(1L);
        int nArms = 1;
        EpsilonGreedy algo = new EpsilonGreedy(epsilon, nArms);
        int chosenArm = algo.selectArm();
        algo.update(chosenArm, BigDecimal.valueOf(1L));
        algo.update(chosenArm, BigDecimal.valueOf(2L));
        algo.update(chosenArm, BigDecimal.valueOf(3L));
    }

    @Test
    public void test() {
        BigDecimal epsilon = BigDecimal.valueOf(.10f);
        List<Arm> arms = new ArrayList<Arm>();
        arms.add(new BernoulliArm(BigDecimal.valueOf(0.10f)));
        arms.add(new BernoulliArm(BigDecimal.valueOf(0.10f)));
        arms.add(new BernoulliArm(BigDecimal.valueOf(0.10f)));
        arms.add(new BernoulliArm(BigDecimal.valueOf(0.10f)));
        arms.add(new BernoulliArm(BigDecimal.valueOf(0.90f)));
        Algorithm algo = new EpsilonGreedy(epsilon, arms.size());
        int numSims = 1;
        int horizon = 20;
        TestResults results = Tests.testAlgorithm(algo, arms, numSims, horizon);
        System.out.println("Sim Nums: " + results.getSimNums());
        System.out.println("Times: " + results.getTimes());
        System.out.println("Chosen Arms: " + results.getChosenArms());
        System.out.println("Rewards: " + results.getRewards());
        System.out.println("Cumulative Rewards: " + results.getCumulativeRewards());
    }
}
