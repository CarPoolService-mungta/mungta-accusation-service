package com.mungta.accusation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccusedMemberTest {

    @DisplayName("equals test")
    @Test
    void testEquals_true() {
        AccusedMember accusedMember = new AccusedMember("id", "name", "xx@gmail.com");
        AccusedMember other = new AccusedMember("id", "name", "xx@gmail.com");

        assertThat(accusedMember.equals(other)).isTrue();
        assertThat(accusedMember.hashCode() == other.hashCode()).isTrue();
    }

    @DisplayName("not equals test")
    @Test
    void testEquals_false() {
        AccusedMember accusedMember = new AccusedMember("id", "name", "xx@gmail.com");
        AccusedMember other = new AccusedMember("id", "name", "xx@naver.com");

        assertThat(accusedMember.equals(other)).isFalse();
        assertThat(accusedMember.hashCode() == other.hashCode()).isFalse();
    }

}
