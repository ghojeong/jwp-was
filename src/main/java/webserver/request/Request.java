package webserver.request;

import javax.servlet.http.HttpSession;
import webserver.common.HttpCookie;
import webserver.common.Protocol;
import webserver.common.SessionManager;

import static webserver.request.RequestMethod.GET;
import static webserver.request.RequestMethod.POST;

public class Request {
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public Request(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public boolean isGet() {
        return GET == requestLine.getMethod();
    }

    public boolean isPost() {
        return POST == requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getQuery(String key) {
        return requestLine.getQuery(key);
    }

    public Protocol getProtocol() {
        return requestLine.getProtocol();
    }


    public String getContentType() {
        return requestHeader.getContentType();
    }

    public int getContentLength() {
        return requestHeader.getContentLength();
    }

    public HttpCookie getCookie() {
        return requestHeader.getCookie();
    }

    public HttpSession getSession() {
        return SessionManager.getSession(getCookie());
    }

    public String getBody(String key) {
        return requestBody.get(key);
    }
}
