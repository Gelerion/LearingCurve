package com.denis.learning.ddd.domain.services.competitor.v1.value;

/**
 * Created by denis.shuvalov on 27/02/2018.
 */
public class Score {
    public Score(int amount) {

    }

    public Score add(Score amount) {
        return this;
    }

    public boolean biggerThan(Score top100Threshold) {
        return false;
    }
}
