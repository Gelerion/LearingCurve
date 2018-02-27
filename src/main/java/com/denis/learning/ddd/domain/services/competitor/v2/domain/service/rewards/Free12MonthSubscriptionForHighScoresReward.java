package com.denis.learning.ddd.domain.services.competitor.v2.domain.service.rewards;

import com.denis.learning.ddd.domain.services.competitor.v1.CompetitorV1;
import com.denis.learning.ddd.domain.services.competitor.v1.value.Score;
import com.denis.learning.ddd.domain.services.competitor.v2.GameV2;
import com.denis.learning.ddd.domain.services.competitor.v2.domain.service.GamingRewardPolicy;
import com.denis.learning.ddd.domain.services.competitor.v2.ScoreFinder;

/**
 * Created by denis.shuvalov on 27/02/2018.
 * <p>
 * you can switch in and out specific score and loyalty
 * calculations according to the latest business promotions. Equally as importantly, you can
 * now make these concepts explicit in the domain. For example, when a business is running its
 * “free 12‐month subscription for high scores” promotion, you can model this explicitly in the
 * domain as a Free12MonthSubscriptionForHighScoresRewardPolicy domain service,
 */
public class Free12MonthSubscriptionForHighScoresReward implements GamingRewardPolicy {

    private ScoreFinder repository;

    public Free12MonthSubscriptionForHighScoresReward(ScoreFinder repository) {
        this.repository = repository;
    }

    @Override
    public void apply(GameV2 game) {
        Score top100Threshold = repository.findTopScore(game, 100);
        CompetitorV1 winner = game.winners().iterator().next();
        if (winner.score().biggerThan(top100Threshold)) {
            //trigger reward process
        }
    }
}
