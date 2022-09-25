package com.mungta.accusation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccusedMemberTest {

    @DisplayName("equals test")
    @Test
    void testEquals() {
        AccusedMember accusedMember = new AccusedMember("id", "name", "xx@gmail.com");
        boolean result = accusedMember.equals(new AccusedMember("id","name", "xx@gmail.com"));
        assertThat(result).isTrue();
    }

}
