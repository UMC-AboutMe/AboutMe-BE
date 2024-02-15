package com.example.aboutme.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class AlarmResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetProfileAlarmListDTO {
        @JsonProperty("profile_alarms")
        private List<AlarmResponse.GetProfileAlarmDTO> profileAlarmList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetProfileAlarmDTO {
        @JsonProperty("content")
        private String content;
        @JsonProperty("profile_serial_number")
        private Integer profileSerialNumber;
    }
}
