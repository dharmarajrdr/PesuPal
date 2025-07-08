package com.pesupal.server.projections;

import com.pesupal.server.enums.Reaction;

public interface ReactionCountProjection {

    Reaction getReaction();

    Integer getCount();
}
