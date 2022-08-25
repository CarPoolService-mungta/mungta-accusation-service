package com.mungta.accusation.service;

import com.mungta.accusation.api.dto.AccusationPartyMemberListResponse;
import com.mungta.accusation.client.PartyServiceClient;
import com.mungta.accusation.client.UserServiceClient;
import com.mungta.accusation.client.dto.PartyResponse;
import com.mungta.accusation.client.dto.UserResponse;
import com.mungta.accusation.domain.repositories.AccusationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mungta.accusation.api.dto.AccusationPartyMemberListResponse.MemberResponse;
import static com.mungta.accusation.constants.AccusationTestSample.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(value = MockitoExtension.class)
class AccusationPartyMembersServiceTest {

    @InjectMocks
    @Spy
    private AccusationPartyMembersService accusationPartyMembersService;

    @Mock
    private AccusationRepository accusationRepository;

    @Mock
    private PartyServiceClient partyServiceClient;

    @Mock
    private UserServiceClient userServiceClient;

    @DisplayName("[회원] 파티관리 서비스와 회원 서비스로부터 파티정보와 회원 정보들을 조회.")
    @Test
    void getAccusationPartyMembers() {
        given(partyServiceClient.getParty(PARTY_ID)).willReturn(getPartyResponse());
        given(userServiceClient.getUserList(anyList())).willReturn(getUserResponseList());
        //given(accusationRepository.findByMemberIdAndPartyInfo(anyString(), any())).willReturn(List.of());
        given(accusationRepository.findByMemberIdAndPartyInfo(anyString(), any())).willReturn(new ArrayList<>());

        AccusationPartyMemberListResponse response = accusationPartyMembersService.getAccusationPartyMembers(MEMBER_ID, PARTY_ID);

        assertAll(
                () -> assertThat(response.getPartyId()).isEqualTo(PARTY_ID),
                () -> assertThat(response.getPlaceOfDeparture()).isEqualTo(PLACE_OF_DEPARTURE),
                () -> assertThat(response.getDestination()).isEqualTo(DESTINATION),
                () -> assertThat(response.getStartedDateTime()).isEqualTo(STARTED_DATE_TIME),
                () -> assertThat(response.getMembers().size()).isEqualTo(1),
                () -> assertThat(response.getMembers().get(0)).isEqualTo(
                        MemberResponse.builder()
                                .id(ACCUSED_MEMBER_ID)
                                .name(ACCUSED_MEMBER_NAME)
                                .emailAddress(ACCUSED_MEMBER_EMAIL)
                                .image("")
                                .accusedYN(false)
                                .build()
                )
        );

    }

    private PartyResponse getPartyResponse() {
        return PartyResponse.builder()
                .partyId(PARTY_ID)
                .placeOfDeparture(PLACE_OF_DEPARTURE)
                .destination(DESTINATION)
                .startedDateTime(STARTED_DATE_TIME)
                //.memberIds(List.of(MEMBER_ID, ACCUSED_MEMBER_ID))
                .memberIds(Arrays.asList(MEMBER_ID, ACCUSED_MEMBER_ID))
                .build();
    }

    private List<UserResponse> getUserResponseList() {
        UserResponse userResponse = UserResponse.builder()
                .id(ACCUSED_MEMBER_ID)
                .name(ACCUSED_MEMBER_NAME)
                .emailAddress(ACCUSED_MEMBER_EMAIL)
                .image(new byte[0])
                .build();
        //return List.of(userResponse);
        return Arrays.asList(userResponse);
    }

}
