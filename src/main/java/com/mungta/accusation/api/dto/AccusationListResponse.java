package com.mungta.accusation.api.dto;

import com.mungta.accusation.domain.Accusation;
import com.mungta.accusation.domain.AccusationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class AccusationListResponse {

    @Schema(description = "신고내역 리스트")
    private List<AccusationInfoResponse> accusations;

    public static AccusationListResponse of(List<Accusation> accusationList) {
        List<AccusationInfoResponse> accusationResponseList = accusationList.stream()
                .map(AccusationInfoResponse::of)
                .collect(Collectors.toList());

        return new AccusationListResponse(accusationResponseList);
    }

    @EqualsAndHashCode
    @Getter
    @Builder
    public static class AccusationInfoResponse {

        @Schema(description = "신고 ID")
        private long id;

        @Schema(description = "파티 ID")
        private long partyId;

        @Schema(description = "제목")
        private String title;

        @Schema(description = "신고 상태")
        private AccusationStatus accusationStatus;

        @Schema(description = "최종 수정일")
        private String modifiedDateTime;

        public static AccusationInfoResponse of(Accusation accusation) {
            return AccusationInfoResponse.builder()
                    .id(accusation.getId())
                    .partyId(accusation.getPartyInfo().getPartyId())
                    .title(accusation.getAccusationContents().getTitle())
                    .accusationStatus(accusation.getAccusationStatus())
                    .modifiedDateTime(
                            accusation.getModifiedDateTime()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    )
                    .build();
        }

    }

}
