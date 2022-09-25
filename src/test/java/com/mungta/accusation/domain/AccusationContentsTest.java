package com.mungta.accusation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccusationContentsTest {

    @DisplayName("equals test")
    @Test
    void testEquals() {
        AccusationContents accusationContents = new AccusationContents("title", "desc");
        boolean result = accusationContents.equals(new AccusationContents("title", "desc"));
        assertThat(result).isTrue();
    }

}
