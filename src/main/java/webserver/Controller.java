package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ResourceService;
import webserver.request.Request;
import webserver.request.RequestFactory;
import webserver.request.RequestMethod;
import webserver.response.Response;

public class Controller implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    private Socket connection;

    public Controller(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug(
                "New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort()
        );

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = RequestFactory.createRequest(
                    new BufferedReader(new InputStreamReader(in, "UTF-8"))
            );
            Response response = handleRequest(request);
            DataOutputStream dos = new DataOutputStream(out);
            dos.write(response.toBytes());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Response handleRequest(Request request) throws IOException {
        if (request.getMethod() == RequestMethod.GET) {
            return ResourceService.getResource(request);
        }
        return Response.createNotImplemented();
    }
}
