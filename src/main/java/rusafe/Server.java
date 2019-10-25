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

	int port = System.getenv().get("PORT") != null ? Integer.parseInt(System.getenv().get("PORT")): 8080;
	HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
    server.createContext("/places", new MyHandler());
    server.setExecutor(null); // creates a default executor
    server.start();
	  System.out.println("hello");
  }

  @SuppressWarnings("restriction")
static class MyHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
    	t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

        if (t.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                t.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
                t.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type,Authorization");
                t.sendResponseHeaders(204, -1);
                return;
        }

      byte [] response = GetURL.getData().getBytes();
      t.sendResponseHeaders(200, response.length);
      OutputStream os = t.getResponseBody();
      os.write(response);
      os.close();
    }
  }
}