package com.denis.learning.ddd.domain.services.competitor.v2.domain.service;

import com.denis.learning.ddd.domain.services.competitor.v2.GameV2;

/**
 * Created by denis.shuvalov on 27/02/2018.
 *
 * domain service interfaces - part of UL
 */
public interface GamingScorePolicy {

    void apply(GameV2 game);
}
