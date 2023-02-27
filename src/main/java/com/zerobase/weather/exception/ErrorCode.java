package com.zerobase.weather.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("내부 서버 오류가 발생했습니다."),
    NOT_FOUND_WEATHER_DATA("날씨 정보가 존재하지 않습니다."),
    NOT_FOUND_DIARY("일기를 찾을 수 없습니다."),
    INVALID_DATE("날짜 형식이 옳바르지 않습니다"),
    ;

    private final String message;
}
