package com.denis.learning.ddd.domain.services.competitor.v2;

import com.denis.learning.ddd.domain.services.competitor.v1.CompetitorV1;
import com.denis.learning.ddd.domain.services.competitor.v2.domain.service.GamingRewardPolicy;
import com.denis.learning.ddd.domain.services.competitor.v2.domain.service.GamingScorePolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by denis.shuvalov on 27/02/2018.
 */
public class OnlineDeathmatchV2 implements GameV2 {

    private UUID id;
    private CompetitorV1 player1;
    private CompetitorV1 player2;

    private Iterable<GamingScorePolicy> scorers;
    private Iterable<GamingRewardPolicy> rewarders;

    private List<CompetitorV1> winners = new ArrayList<>();
    private List<CompetitorV1> losers = new ArrayList<>();

    public OnlineDeathmatchV2(UUID id, CompetitorV1 player1, CompetitorV1 player2, Iterable<GamingScorePolicy> scorers, Iterable<GamingRewardPolicy> rewarders) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.scorers = scorers;
        this.rewarders = rewarders;
    }

    void commenceBattle() {
        //...
        //battle completes

        //the following would actually be based on game result
        winners.add(player1);
        losers.add(player2);

        updateScoresAndRewards();
    }

    private void updateScoresAndRewards() {
        for (GamingScorePolicy scorer : scorers) {
            scorer.apply(this);
        }

        for (GamingRewardPolicy rewarder : rewarders) {
            rewarder.apply(this);
        }
    }

    @Override
    public Iterable<CompetitorV1> winners() {
        return winners;
    }

    @Override
    public Iterable<CompetitorV1> losers() {
        return losers;
    }
}
