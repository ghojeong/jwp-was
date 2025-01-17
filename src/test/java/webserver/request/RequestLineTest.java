package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.common.Protocol;
import webserver.request.exception.IllegalRequestLineException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestLineTest {

    @DisplayName("HTTP GET 요청에 대한 RequestLine 을 파싱할 수 있어야 한다.")
    @Test
    void get() {
        RequestLine requestLine = RequestLine.from("GET /users HTTP/1.1");
        assertAll(
                () -> assertThat(requestLine.getMethod().name()).isEqualTo("GET"),
                () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getProtocol().getName()).isEqualTo("HTTP"),
                () -> assertThat(requestLine.getProtocol().getVersion()).isEqualTo("1.1")
        );
    }

    @DisplayName("HTTP POST 요청에 대한 RequestLine 을 파싱할 수 있어야 한다.")
    @Test
    void post() {
        RequestLine requestLine = RequestLine.from("POST /users HTTP/1.1");
        assertAll(
                () -> assertThat(requestLine.getMethod().name()).isEqualTo("POST"),
                () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getProtocol().getName()).isEqualTo("HTTP"),
                () -> assertThat(requestLine.getProtocol().getVersion()).isEqualTo("1.1")
        );
    }

    @DisplayName("RequestLine 을 파싱해서, method, path, query string, protocol 정보를 가져올 수 있다.")
    @Test
    void from() {
        RequestLine requestLine = RequestLine.from("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");
        assertAll(
                () -> assertThat(requestLine.getMethod()).isEqualTo(RequestMethod.GET),
                () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getQuery("userId")).isEqualTo("javajigi"),
                () -> assertThat(requestLine.getQuery("password")).isEqualTo("password"),
                () -> assertThat(requestLine.getQuery("name")).isEqualTo("JaeSung"),
                () -> assertThat(requestLine.getProtocol()).isEqualTo(Protocol.HTTP_1_1)
        );
    }

    @DisplayName("request line 이 null 일 경우, IllegalRequestLineException 이 발생한다.")
    @Test
    void emptyRequestLine() {
        assertThatThrownBy(
                () -> RequestLine.from(null)
        ).isInstanceOf(IllegalRequestLineException.class);
    }

    @DisplayName("부적절한 request line 일 경우, IllegalRequestLineException 이 발생한다.")
    @Test
    void illegalRequestLine() {
        assertThatThrownBy(
                () -> RequestLine.from("GET /users")
        ).isInstanceOf(IllegalRequestLineException.class);
    }

    @DisplayName("기본 프로토콜은 HTTP1.1 이다.")
    @Test
    void defaultProtocol() {
        assertThat(new RequestLine(RequestMethod.GET, Uri.from("/")).getProtocol())
                .isEqualTo(Protocol.HTTP_1_1);
    }
}
