package com.example.idealselect.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad Request"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_USERID(HttpStatus.UNAUTHORIZED, "존재하지 않는 USERID 입니다."),
    MISMATCH_PASSWORD(HttpStatus.UNAUTHORIZED, "패스워드가 일치하지 않습니다."),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "로그인을 하지 않아 이용하실 수 없습니다."),
    /* 403 FORBIDDEN : 권한이 없는 사용자 */

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 이미지입니다."),


    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    ALREADY_EXIST_USERID(HttpStatus.CONFLICT, "이미 가입된 USERID 입니다."),
    DUPLICATED_NAME_EXIST(HttpStatus.CONFLICT, "중복되는 이상형 이름이 존재합니다."),
    /* 500 */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "server error"),
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
