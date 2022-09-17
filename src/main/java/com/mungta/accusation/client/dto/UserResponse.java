package com.mungta.accusation.client.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private String userId;
    private String userName;
    private String userMailAddress;
    private String userTeamName;
    private byte[] image;   // todo: UserDto 필드명 확인

}
