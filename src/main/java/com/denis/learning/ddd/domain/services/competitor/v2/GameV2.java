package com.denis.learning.ddd.domain.services.competitor.v2;

import com.denis.learning.ddd.domain.services.competitor.v1.CompetitorV1;

/**
 * Created by denis.shuvalov on 27/02/2018.
 */
public interface GameV2 {

    Iterable<CompetitorV1> winners();

    Iterable<CompetitorV1> losers();

}
