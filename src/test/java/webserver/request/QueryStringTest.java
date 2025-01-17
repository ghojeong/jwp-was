package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.exception.IllegalQueryStringException;
import webserver.request.exception.IllegalQueryStringKeyException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class QueryStringTest {

    @DisplayName("QueryString 을 파싱해서 값을 가져올 수 있어야 한다.")
    @Test
    void parse() {
        QueryString queryString = QueryString.from("userId=javajigi&password=password&name=JaeSung");
        assertAll(
                () -> assertThat(queryString.get("userId")).isEqualTo("javajigi"),
                () -> assertThat(queryString.get("password")).isEqualTo("password"),
                () -> assertThat(queryString.get("name")).isEqualTo("JaeSung")
        );
    }

    @DisplayName("부적절한 QueryString 일 경우, IllegalQueryStringException 이 발생한다.")
    @Test
    void illegalQueryString() {
        assertThatThrownBy(
                () -> QueryString.from("==")
        ).isInstanceOf(IllegalQueryStringException.class);
    }

    @DisplayName("키에 대응되는 값이 존재하지 않으면, IllegalQueryStringKeyException 이 발생한다.")
    @Test
    void illegalQueryStringKey() {
        QueryString queryString = QueryString.from("");
        assertThatThrownBy(
                () -> queryString.get("Illegal_Key")
        ).isInstanceOf(IllegalQueryStringKeyException.class);
    }

    @DisplayName("key 와 value 를 통해 쿼리 스트링을 편집할 수 있다.")
    @Test
    void put() {
        QueryString queryString = QueryString.from("a=b");
        queryString.put("name", "JaeSung")
                .put("userId", "javajigi");

        assertThat(queryString.toString())
                .isEqualTo("a=b&name=JaeSung&userId=javajigi");
    }
}
