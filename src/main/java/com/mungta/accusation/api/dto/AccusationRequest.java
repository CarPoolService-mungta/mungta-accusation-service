package com.mungta.accusation.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccusationRequest {

    @Schema(description = "신고 등록한 회원 ID")
    @NotBlank(message = "{id.not.empty}")
    private String memberId;

    private PartyInfoRequest partyInfo;

    private AccusedMemberRequest accusedMember;

    private AccusationContentsRequest accusationContents;

}
