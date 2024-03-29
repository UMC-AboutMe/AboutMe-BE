package com.example.aboutme.apiPayload.code.status;

import com.example.aboutme.apiPayload.code.BaseErrorCode;
import com.example.aboutme.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    // 일반적인 에러
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    _ENUM_TYPE_NOT_MATCH(HttpStatus.BAD_REQUEST, "COMMON404", "일치하는 타입이 없습니다"),

    // 마이스페이스 에러
    _UNVALID_CHARACTER_TYPE(HttpStatus.BAD_REQUEST, "SPACE400", "캐릭터 타입은 1부터 9까지의 숫자만 가능합니다."),
    _UNVALID_ROOM_TYPE(HttpStatus.BAD_REQUEST, "SPACE401", "방 타입은 1부터 4까지의 숫자만 가능합니다."),
    SPACE_NOT_FOUND(HttpStatus.NOT_FOUND, "SPACE402", "해당하는 스페이스가 존재하지 않습니다"),
    MEMBER_SPACE_NOT_FOUND(HttpStatus.NOT_FOUND, "SPACE403", "해당 스페이스가 아지트에 존재하지 않습니다"),
    MEMBER_SPACE_ALREADY_EXISTING(HttpStatus.BAD_REQUEST, "SPACE404", "해당 스페이스가 이미 아지트에 존재합니다."),
    CANNOT_SHARE_OWN_SPACE(HttpStatus.BAD_REQUEST, "SPACE404", "본인 스페이스는 아지트에 추가할 수 없습니다."),
    SPACE_MAXIMUN_IMAGE_COUNT(HttpStatus.BAD_REQUEST, "SPACE404", "이미지는 최대 세 개까지만 업로드할 수 있습니다."),
    SPACE_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "SPACE404", "이미 스페이스가 존재합니다."),
    SPACE_NICKNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "SPACE404", "스페이스 닉네임이 이미 존재합니다."),

    // 마이프로필 에러
    PROFILE_SIZE_OVERFLOW(HttpStatus.BAD_REQUEST, "PROFILE400", "이 이상 마이프로필을 생성할 수 없습니다"),
    PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "PROFILE401", "해당하는 프로필이 존재하지 않습니다"),
    MEMBER_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "PROFILE403", "해당 사용자의 마이프로필이 아닙니다."),
    MEMBER_PROFILE_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "PROFILE404", "해당 프로필은 이미 보관함에 존재합니다"),
    CANNOT_SHARE_OWN_PROFILE(HttpStatus.BAD_REQUEST, "PROFILE405", "본인 프로필은 보관함에 추가할 수 없습니다."),
    PROFILE_NOT_MATCH_MEMBER_AT_DELETE(HttpStatus.BAD_REQUEST, "PROFILE402", "해당 프로필을 삭제할 수 없습니다"),
    PROFILE_NOT_MATCH_MEMBER_AT_GET(HttpStatus.BAD_REQUEST, "PROFILE406", "해당 프로필을 조회할 수 없습니다"),
    PROFILE_NOT_MATCH_MEMBER_AT_UPDATE(HttpStatus.BAD_REQUEST, "PROFILE407", "해당 프로필을 수정할 수 없습니다"),
    PROFILE_NOT_MINE(HttpStatus.BAD_REQUEST, "PROFILE408", "공유하려는 프로필이 본인 것이 아닙니다."),
    PROFILE_ALREADY_SHARED(HttpStatus.BAD_REQUEST, "PROFILE409", "이미 저장된 프로필입니다."),

    // 마이프로필 이미지 에러
    PROFILE_IMAGE_CANNOT_CHANGE_TO_CHARACTER(HttpStatus.BAD_REQUEST, "IMAGE400", "마이스페이스에서 캐릭터를 설정해야 합니다"),
    PROFILE_IMAGE_REQUIRED(HttpStatus.BAD_REQUEST, "IMAGE401", "프로필 이미지는 필수입니다"),

    // 마이프로필 Feature 에러
    PROFILE_FEATURE_NOT_FOUND(HttpStatus.NOT_FOUND, "FEATURE400", "해당하는 프로필 특징이 존재하지 않습니다"),
    PROFILE_FEATURE_NOT_MATCH_PROFILE(HttpStatus.BAD_REQUEST, "FEATURE401", "해당 프로필 특징을 수정할 수 없습니다"),
    PROFILE_FEATURE_NAME_CANNOT_EMPTY(HttpStatus.BAD_REQUEST, "FEATURE401", "프로필의 이름은 필수입니다"),

    // 멤버 에러
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER400", "해당하는 사용자가 존재하지 않습니다"),

    // 멤버 프로필 에러
    MEMBER_IS_NOT_PROFILE_CREATOR(HttpStatus.NOT_FOUND, "MEMBERPROFILE400", "제거하려는 프로필과 프로필 작성자가 일치하지 않습니다"),

    // S3 에러
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "FILE400", "해당 파일이 존재하지 않습니다."),

    // 소셜타입 에러
    UNKNOWN_SOCIALTYPE(HttpStatus.NOT_FOUND, "SOCIAL400", "해당 소셜 타입이 존재하지 않습니다."),

    // 알람 에러
    ALARM_ALREADY_EXISTING(HttpStatus.BAD_REQUEST, "ALARM400", "해당 알람이 이미 존재합니다."),
    ALARM_NOT_MINE(HttpStatus.BAD_REQUEST, "ALARM401", "본인 알림이 아닙니다."),
    ALARM_NOT_FOUND(HttpStatus.BAD_REQUEST, "ALARM402", "해당 알림이 존재하지 않습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
