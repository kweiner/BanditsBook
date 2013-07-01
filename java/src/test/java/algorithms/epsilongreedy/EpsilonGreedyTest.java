package algorithms.epsilongreedy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

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
    public void test2() {
        double[] means = { 0.1, 0.1, 0.1, 0.1, 0.9 };
        List<Arm> arms = new ArrayList<Arm>(means.length);
        for (int i = 0; i < means.length; i++) {
            arms.add(new BernoulliArm(BigDecimal.valueOf(means[i])));
        }
        Collections.shuffle(arms);
        System.out.println(arms.get(0).draw());
        System.out.println(arms.get(1).draw());
        System.out.println(arms.get(2).draw());
        System.out.println(arms.get(2).draw());
        System.out.println(arms.get(3).draw());
        System.out.println(arms.get(2).draw());
        System.out.println(arms.get(4).draw());
    }
}
