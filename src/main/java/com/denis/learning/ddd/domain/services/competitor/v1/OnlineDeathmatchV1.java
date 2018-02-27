package com.denis.learning.ddd.domain.services.competitor.v1;

import com.denis.learning.ddd.domain.services.competitor.v1.value.Score;

import java.util.UUID;

/**
 * Created by denis.shuvalov on 27/02/2018.
 */
public class OnlineDeathmatchV1 implements Game {

    private UUID id;
    private CompetitorV1 player1;
    private CompetitorV1 player2;

    public OnlineDeathmatchV1(CompetitorV1 player1, CompetitorV1 player2) {
        //...
        this.player1 = player1;
        this.player2 = player2;
        this.id = UUID.randomUUID();
    }

    public UUID id() {
        return this.id;
    }

    void commenceBattle() {
        //...
        //battle completes

        //the following would actually be based on game result
        CompetitorV1 winner = player1;
        CompetitorV1 loser = player2;

        updateScoresAndRewards(winner, loser);
    }

    private void updateScoresAndRewards(CompetitorV1 winner, CompetitorV1 loser) {
        // real system uses rankings, history, bonus points, in-game actions
        // seasonal promotions, marketing campaigns
        winner.score = winner.score.add(new Score(50));
        loser.score = loser.score.add(new Score(20));
    }
}
