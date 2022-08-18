package com.mungta.accusation.service;

import com.mungta.accusation.api.dto.AccusationPartyMemberListResponse;
import com.mungta.accusation.domain.PartyInfo;
import com.mungta.accusation.domain.repositories.AccusationRepository;
import com.mungta.accusation.client.PartyServiceClient;
import com.mungta.accusation.client.UserServiceClient;
import com.mungta.accusation.client.dto.PartyResponse;
import com.mungta.accusation.client.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static com.mungta.accusation.api.dto.AccusationPartyMemberListResponse.MemberResponse;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AccusationPartyMembersService {

    private final AccusationRepository accusationRepository;

    private final PartyServiceClient partyServiceClient;
    private final UserServiceClient userServiceClient;

    public AccusationPartyMemberListResponse getAccusationPartyMembers(final String memberId, final long partyId) {
        log.info("Get PartyInfo from PartyService. partyId: {}", partyId);
        PartyResponse party = partyServiceClient.getParty(partyId);

        List<String> memberIds = party.getMemberIds().stream()
                .filter(id -> !id.equals(memberId))
                .collect(Collectors.toList());

        log.info("Get MemberList from UserService. memberIds: {}", memberIds);
        List<UserResponse> userList = userServiceClient.getUserList(memberIds);

        List<String> preAccusedMemberIdList = getAccusedMemberIdByPartyId(memberId, party);

        return AccusationPartyMemberListResponse.of(party, getMemberResponse(userList, preAccusedMemberIdList));
    }

    private List<String> getAccusedMemberIdByPartyId(String memberId, PartyResponse party) {
        PartyInfo partyInfo = PartyInfo.builder()
                .partyId(party.getPartyId())
                .placeOfDeparture(party.getPlaceOfDeparture())
                .destination(party.getDestination())
                .startedDateTime(party.getStartedDateTime())
                .build();

        return  accusationRepository.findByMemberIdAndPartyInfo(memberId, partyInfo).stream()
                .map(accusation -> accusation.getAccusedMember().getId())
                .collect(Collectors.toList());
    }

    private List<MemberResponse> getMemberResponse(List<UserResponse> userList, List<String> preAccusedMemberIdList) {
        return userList.stream()
                .map(userResponse -> {
                    byte[] byteEnc64 = Base64.encodeBase64(userResponse.getImage());

                    return MemberResponse.builder()
                            .id(userResponse.getId())
                            .name(userResponse.getName())
                            .emailAddress(userResponse.getEmailAddress())
                            .image(new String(byteEnc64, StandardCharsets.UTF_8))
                            .accusedYN(preAccusedMemberIdList.contains(userResponse.getId()))
                            .build();
                }).collect(Collectors.toList());
    }

}
