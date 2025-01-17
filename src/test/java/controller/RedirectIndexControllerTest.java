package controller;

import java.io.IOException;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.Request;
import webserver.request.RequestBody;
import webserver.request.RequestHeader;
import webserver.request.RequestLine;
import webserver.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

class RedirectIndexControllerTest {

    @DisplayName("GET / 의 경우, /index.html 로 Redirect 한다.")
    @Test
    void doGet() throws IOException {
        // given
        RequestLine requestLine = RequestLine.from(
                "GET / HTTP/1.1"
        );
        RequestHeader requestHeader = RequestHeader.from(Collections.emptyList());
        RequestBody requestBody = RequestBody.from("");
        Request request = new Request(requestLine, requestHeader, requestBody);

        // when
        Response response = new ListUserController().service(request);
        String actual = response.toString();

        //then
        String expected = "HTTP/1.1 302 Found \r\n"
                + "Location: /user/login.html \r\n"
                + "\r\n";
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("POST / 의 경우, Not Implemented 를 반환한다.")
    @Test
    void doPost() throws IOException {
        // given
        RequestLine requestLine = RequestLine.from(
                "POST / HTTP/1.1"
        );
        RequestHeader requestHeader = RequestHeader.from(Collections.emptyList());
        RequestBody requestBody = RequestBody.from("");
        Request request = new Request(requestLine, requestHeader, requestBody);

        // when
        Response response = new ListUserController().service(request);
        String actual = response.toString();

        //then
        String expected = "HTTP/1.1 501 Not Implemented \r\n"
                + "Content-Type: text/html;charset=utf-8 \r\n"
                + "Content-Length: 19 \r\n"
                + "\r\n"
                + "Not Implemented Yet";
        assertThat(actual).isEqualTo(expected);
    }
}
