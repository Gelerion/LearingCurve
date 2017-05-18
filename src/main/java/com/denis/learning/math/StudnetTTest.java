package com.denis.learning.math;

import org.apache.commons.math3.stat.inference.TTest;

public class StudnetTTest {
    public static void main(String[] args) {
        TTest tTest = new TTest();

        //null hypothesis -> averages are same

        double[] baseline = {1, 0.8, 1.2};
        double[] specimen = {0.5, 1.25, 0.5};

//        double pairedT = tTest.pairedTTest(baseline, specimen);
//        System.out.println("pairedT = " + pairedT);

        //The alpha-value is commonly set to 0.1—which means that a result is considered statistically
        //significant if it means that the specimen and baseline will be the same only 10% (0.1)
        //of the time (or conversely, that 90% of the time there is a difference between the specimen and baseline)
        //boolean isSignificant = tTest.pairedTTest(baseline, specimen, 0.1);
        //System.out.println("isSignificant = " + isSignificant);

//        double homoscedasticT = tTest.homoscedasticT(baseline, specimen);
//        double t = tTest.t(baseline, specimen);

        //observed significance level (p-value)
        //p-value refers to the probability that the null hypothesis for the test is false.
        double tTest1 = tTest.tTest(baseline, specimen);
        System.out.println("observed significance level (p-value)" + tTest1);
        // p-value = 43% -> 57% confidence that the results are different.
    }
}
