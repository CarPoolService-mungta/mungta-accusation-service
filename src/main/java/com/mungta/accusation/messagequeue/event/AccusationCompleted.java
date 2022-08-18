package com.mungta.accusation.messagequeue.event;

import lombok.Getter;

@Getter
public class AccusationCompleted extends AbstractEvent {

    private final String accusedMemberId;

    public AccusationCompleted(String accusedMemberId) {
        super();
        this.accusedMemberId = accusedMemberId;
    }

    @Override
    public String toString() {
        return "AccusationCompleted{" +
                "eventType='" + eventType + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", accusedMemberId='" + accusedMemberId + '\'' +
                '}';
    }

}
