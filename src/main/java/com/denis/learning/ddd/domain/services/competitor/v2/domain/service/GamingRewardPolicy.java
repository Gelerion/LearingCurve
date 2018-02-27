package com.denis.learning.ddd.domain.services.competitor.v2.domain.service;

import com.denis.learning.ddd.domain.services.competitor.v2.GameV2;

/**
 * Created by denis.shuvalov on 27/02/2018.
 * <p>
 * This domain service
 * contains important business rules, lives inside the domain model, and hence is pure. However, some
 * domain services are not implemented within the domain model; instead, their implementation lives
 * in the service layer.
 */
public interface GamingRewardPolicy {

    void apply(GameV2 game);

}
