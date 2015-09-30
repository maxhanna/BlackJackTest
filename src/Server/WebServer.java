package Server;
import Client.ClientVariables;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	String serverAddress = "localhost";
	ClientVariables model = new ClientVariables();

	protected void start() {
		ServerSocket s;

		System.out.println("Webserver starting up on port 8000");
		System.out.println("(press ctrl-c to exit)");
		try {
			// create the main server socket
			s = new ServerSocket(8000);
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return;
		}

		System.out.println("Waiting for requests");
		for (;;) {
			try {
				// wait for a connection
				Socket remote = s.accept();
				System.out.println("----------- REQUEST --------");
				// remote is now the connected socket
				BufferedReader in = new BufferedReader(new InputStreamReader(
						remote.getInputStream()));
				PrintWriter out = new PrintWriter(remote.getOutputStream());

				// read the data sent. 
				// stop reading once a blank line is hit. This
				// blank line signals the end of the client HTTP
				// headers.
				String  thisLine = null;
				try{
					while ((thisLine = in.readLine()) != null && !remote.isClosed()) {
						System.out.println(thisLine);
						if (thisLine.contains("?username="))
						{
							System.out.println(thisLine);
							String user = thisLine;
							user = user.replace("GET /?username=", "");
							String[] tokens = user.split(" ");
							user = tokens[0];

							// Send the response
							// Send the headers
							out.println("HTTP/1.0 200 OK");
							out.println("Content-Type: text/html");
							out.println("Server: Bot");
							// this blank line signals the end of the headers
							out.println("");
							// Send the HTML page
							out.println("<H1>Welcome to the Black Jack Table</H1>");
							out.println("<H2>Welcome "+user+"</H2>");


							out.flush();
						//	remote.close();
							break;
						}
						else if (thisLine.contains("GET / HTTP"))
						{
							System.out.println(thisLine);
							// Send the response
							// Send the headers
							out.println("HTTP/1.0 200 OK");
							out.println("Content-Type: text/html");
							out.println("Server: Bot");
							// this blank line signals the end of the headers
							out.println("");
							// Send the HTML page
							out.println("<H1>Welcome to the Black Jack Table</H1>");
							out.println("<H2>Enter A Username</H2>");
							out.println("<form>Username: <input type=\"text\""
									+ " id=\"username\" name=\"username\"><br>"
									+ "<input type=\"submit\" value=\"Submit\"></form>");
							out.flush();
							//remote.close();
							break;
						}
					}       
				}catch(Exception e){
					e.printStackTrace();
				}
				
			} catch (Exception e) {
				System.out.println("Error: " + e);
			}
		}
	}

	/**
	 * Start the application.
	 * 
	 * @param args
	 *            Command line parameters are not used.
	 */
	public static void main(String args[]) {
		WebServer ws = new WebServer();
		ws.start();
	}
}