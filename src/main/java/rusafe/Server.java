package rusafe;



import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/*
 * a simple static http server
*/
public class Server {

  @SuppressWarnings("restriction")
public static void main(String[] args) throws Exception {
	HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
    server.createContext("/places", new MyHandler());
    server.setExecutor(null); // creates a default executor
    server.start();
	  System.out.println("hello");
  }

  @SuppressWarnings("restriction")
static class MyHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
      byte [] response = "Hello World".getBytes();
      t.sendResponseHeaders(200, response.length);
      OutputStream os = t.getResponseBody();
      os.write(response);
      os.close();
    }
  }
}