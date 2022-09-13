package com.mungta.accusation.client.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private String id;
    private String name;
    private String email;
    private String department;
    private byte[] image;

}
