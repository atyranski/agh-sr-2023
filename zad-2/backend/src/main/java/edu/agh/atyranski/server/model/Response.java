package edu.agh.atyranski.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response<T> {

    private T payload;
    private Integer status;

    public Response(T payload, Integer status) {
        this.payload = payload;
        this.status = status;
    }

    public static <T> Response<T> success(T payload) {
        return new Response<>(payload, 200);
    }

    public static <T> Response<T> successWithNoContent() {
        return new Response<>(null, 204);
    }

    @Getter
    @RequiredArgsConstructor
    private static class Error {
        public final Integer code;
        private final String message;
    }
}
