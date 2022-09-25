package com.mungta.accusation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.mungta.accusation.constants.AccusationTestSample.*;
import static com.mungta.accusation.constants.AccusationTestSample.STARTED_DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;

class PartyInfoTest {

    @DisplayName("equals test")
    @Test
    void testEquals() {
        PartyInfo partyInfo = getNewPartyInfo();
        boolean result = partyInfo.equals(getNewPartyInfo());
        assertThat(result).isTrue();
    }

    private PartyInfo getNewPartyInfo() {
        return PartyInfo.builder()
                .partyId(PARTY_ID)
                .placeOfDeparture(PLACE_OF_DEPARTURE)
                .destination(DESTINATION)
                .startedDateTime(STARTED_DATE_TIME)
                .build();
    }

}
