package com.mungta.accusation.api;

import com.mungta.accusation.api.dto.AccusationPartyMemberListResponse;
import com.mungta.accusation.service.AccusationPartyMembersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ACCUSATION MEMBER", description = "신고 대상 조회 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/mungta/accusation/party-members")
public class AccusationPartyMembersController {

    private final AccusationPartyMembersService accusationPartyMembersService;

    @GetMapping
    public ResponseEntity<AccusationPartyMemberListResponse> getAccusationPartyMembers(@RequestParam String memberId,
                                                                                       @RequestParam long partyId) {
        AccusationPartyMemberListResponse response =
                accusationPartyMembersService.getAccusationPartyMembers(memberId, partyId);
        return ResponseEntity.ok(response);
    }

}
