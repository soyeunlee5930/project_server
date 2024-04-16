package project.project.error;

import java.util.Arrays;

public enum ErrorCode {
    //400 BAD_REQUEST : 잘못된 요청
    INVALID_PARAMS(400, "InvalidParams", "필수데이터 누락, 또는 형식과 다른 데이터를 요청하셨습니다."),

    //401 UNAUTHORIZED : 인증되지 않은 사용자
    INVALID_AUTH_TOKEN(401, "Unauthorized", "권한 정보가 없는 토큰입니다."),

    //404 NOT_FOUND : 잘못된 리소스
    USER_NOT_FOUND(404, "NotFound", "해당하는 정보의 사용자를 찾을 수 없습니다."),
    NOT_FOUND(404, "NotFound", "존재하지 않는 데이터입니다."),

    // 409 CONFLICT : 리소스의 현재 상태와 충돌, 중복된 데이터 존재
    CONFLICT(409, "Conflict", "데이터가 이미 존재합니다."),

    //500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(500, "InternalServerError", "서버 에러입니다. 고객센터로 연락주시길 부탁드립니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ErrorCode valueOfCode(String errorCode) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(errorCode))
                .findAny()
                .orElse(null);
    }
}
