package com.denis.learning.ddd.domain.services.competitor.v1;

import com.denis.learning.ddd.domain.services.competitor.v1.value.Ranking;
import com.denis.learning.ddd.domain.services.competitor.v1.value.Score;

import java.util.UUID;

/**
 * Created by denis.shuvalov on 27/02/2018.
 */
public class CompetitorV1 {

    UUID id;
    String gameTag;
    Score score;
    Ranking worldRanking;

    public Score score() {
        return this.score;
    }
}
