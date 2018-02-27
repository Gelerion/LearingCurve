package com.denis.learning.ddd.domain.services.competitor.v2;

import com.denis.learning.ddd.domain.services.competitor.v1.value.Score;

/**
 * Created by denis.shuvalov on 27/02/2018.
 */
public interface ScoreFinder {
    Score findTopScore(GameV2 game, int top);
}
