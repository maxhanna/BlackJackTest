package Server;
import Client.ClientVariables;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	String serverAddress = "localhost";
	BlackJackVariables model = new BlackJackVariables();

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
						if (thisLine.contains("GET /?join="))
						{
							System.out.println(thisLine);
							String user = thisLine;
							String table = "";
							user = user.replace("GET /?join=", "");
							String[] tokens1 = user.split(" ");
							String[] tokens = tokens1[0].split("\\&");
							user = tokens[0];
							table = tokens[1];
							table = table.replace("table=", "");
							table = table.replace("+", " ");
							table.trim();
							// Send the response
							// Send the headers
							out.println("HTTP/1.0 200 OK");
							out.println("Content-Type: text/html");
							out.println("Server: Bot");
							// this blank line signals the end of the headers
							out.println("");
							// Send the HTML page
							out.println("<html><head></head><body><H1>"+table+"</H1>");

							out.println("<H2>Welcome "+user+"</H2>");
							out.println("<br>");
							
								
							if (model.rooms.get(table)>5)
							{
								out.println("<br>Room is currently full<br>");
							}
							if (model.roomsPhase.get(table)!=null 
									&& model.roomsPhase.get(table)==0)
							{
								model.roomsPhase.remove(table);
								model.roomsPhase.put(table, 1);
								model.hands.put("Dealer,"+table, model.serveCards(model.deck));
								System.out.println("Initialize game table: "+table);
							}
							if (model.roomsPhase.get(table)!=null 
									&& model.roomsPhase.get(table)<2)
							{
								if (model.rooms.get(table)<6)
									model.hands.put(user+","+table,model.serveCards(model.deck));
								for (String userHands:model.hands.keySet())
								{
									if (userHands.contains(","+table))
									{
										if (userHands.contains(user+","+table))
										{
											String theHand = model.hands.get(userHands);
											theHand.replace(",", " ");
											theHand = theHand.replace("Ace of spades", "<img src=cards/aceofspades.jpg width=100 height=235>");
											theHand = theHand.replace("King of spades", "<img src=cards/kingofspades.jpg width=100 height=235>");
											theHand = theHand.replace("Queen of spades", "<img src=cards/queenofspades.jpg width=100 height=235>");
											theHand = theHand.replace("Jack of spades", "<img src=cards/jackofspades.jpg width=100 height=235>");
											theHand = theHand.replace("10 of spades", "<img src=cards/10ofspades.jpg width=100 height=235>");
											theHand = theHand.replace("9 of spades", "<img src=cards/9ofspades.jpg width=100 height=235>");
											theHand = theHand.replace("8 of spades", "<img src=cards/8ofspades.jpg width=100 height=235>");
											theHand = theHand.replace("7 of spades", "<img src=cards/7ofspades.jpg width=100 height=235>");
											theHand = theHand.replace("6 of spades", "<img src=cards/6ofspades.jpg width=100 height=235>");
											theHand = theHand.replace("5 of spades", "<img src=cards/5ofspades.jpg width=100 height=235>");
											theHand = theHand.replace("4 of spades", "<img src=cards/4ofspades.jpg width=100 height=235>");
											theHand = theHand.replace("3 of spades", "<img src=cards/3ofspades.jpg width=100 height=235>");
											theHand = theHand.replace("2 of spades", "<img src=cards/2ofspades.jpg width=100 height=235>");
											theHand = theHand.replace("Ace of hearts", "<img src=cards/aceofhearts.jpg width=100 height=235>");
											theHand = theHand.replace("King of hearts", "<img src=cards/kingofhearts.jpg width=100 height=235>");
											theHand = theHand.replace("Queen of hearts", "<img src=cards/queenofhearts.jpg width=100 height=235>");
											theHand = theHand.replace("Jack of hearts", "<img src=cards/jackofhearts.jpg width=100 height=235>");
											theHand = theHand.replace("10 of hearts", "<img src=cards/10ofhearts.jpg width=100 height=235>");
											theHand = theHand.replace("9 of hearts", "<img src=cards/9ofhearts.jpg width=100 height=235>");
											theHand = theHand.replace("8 of hearts", "<img src=cards/8ofhearts.jpg width=100 height=235>");
											theHand = theHand.replace("7 of hearts", "<img src=cards/7ofhearts.jpg width=100 height=235>");
											theHand = theHand.replace("6 of hearts", "<img src=cards/6ofhearts.jpg width=100 height=235>");
											theHand = theHand.replace("5 of hearts", "<img src=cards/5ofhearts.jpg width=100 height=235>");
											theHand = theHand.replace("4 of hearts", "<img src=cards/4ofhearts.jpg width=100 height=235>");
											theHand = theHand.replace("3 of hearts", "<img src=cards/3ofhearts.jpg width=100 height=235>");
											theHand = theHand.replace("2 of hearts", "<img src=cards/2ofhearts.jpg width=100 height=235>");
											theHand = theHand.replace("Ace of clubs", "<img src=cards/aceofclubs.jpg width=100 height=235>");
											theHand = theHand.replace("King of clubs", "<img src=cards/kingofclubs.jpg width=100 height=235>");
											theHand = theHand.replace("Queen of clubs", "<img src=cards/queenofclubs.jpg width=100 height=235>");
											theHand = theHand.replace("Jack of clubs", "<img src=cards/jackofclubs.jpg width=100 height=235>");
											theHand = theHand.replace("10 of clubs", "<img src=cards/10ofclubs.jpg width=100 height=235>");
											theHand = theHand.replace("9 of clubs", "<img src=cards/9ofclubs.jpg width=100 height=235>");
											theHand = theHand.replace("8 of clubs", "<img src=cards/8ofclubs.jpg width=100 height=235>");
											theHand = theHand.replace("7 of clubs", "<img src=cards/7ofclubs.jpg width=100 height=235>");
											theHand = theHand.replace("6 of clubs", "<img src=cards/6ofclubs.jpg width=100 height=235>");
											theHand = theHand.replace("5 of clubs", "<img src=cards/5ofclubs.jpg width=100 height=235>");
											theHand = theHand.replace("4 of clubs", "<img src=cards/4ofclubs.jpg width=100 height=235>");
											theHand = theHand.replace("3 of clubs", "<img src=cards/3ofclubs.jpg width=100 height=235>");
											theHand = theHand.replace("2 of clubs", "<img src=cards/2ofclubs.jpg width=100 height=235>");
											theHand = theHand.replace("Ace of diamonds", "<img src=cards/aceofdiamonds.jpg width=100 height=235>");
											theHand = theHand.replace("King of diamonds", "<img src=cards/kingofdiamonds.jpg width=100 height=235>");
											theHand = theHand.replace("Queen of diamonds", "<img src=cards/queenofdiamonds.jpg width=100 height=235>");
											theHand = theHand.replace("Jack of diamonds", "<img src=cards/jackofdiamonds.jpg width=100 height=235>");
											theHand = theHand.replace("10 of diamonds", "<img src=cards/10ofdiamonds.jpg width=100 height=235>");
											theHand = theHand.replace("9 of diamonds", "<img src=cards/9ofdiamonds.jpg width=100 height=235>");
											theHand = theHand.replace("8 of diamonds", "<img src=cards/8ofdiamonds.jpg width=100 height=235>");
											theHand = theHand.replace("7 of diamonds", "<img src=cards/7ofdiamonds.jpg width=100 height=235>");
											theHand = theHand.replace("6 of diamonds", "<img src=cards/6ofdiamonds.jpg width=100 height=235>");
											theHand = theHand.replace("5 of diamonds", "<img src=cards/5ofdiamonds.jpg width=100 height=235>");
											theHand = theHand.replace("4 of diamonds", "<img src=cards/4ofdiamonds.jpg width=100 height=235>");
											theHand = theHand.replace("3 of diamonds", "<img src=cards/3ofdiamonds.jpg width=100 height=235>");
											theHand = theHand.replace("2 of diamonds", "<img src=cards/2ofdiamonds.jpg width=100 height=235>");
											
											String theUserName = userHands.replace(","+table,"");
											out.println("<br>"+theUserName+" " + model.hands.get(userHands));
										
										}
										else
										{
											String theHand = model.hands.get(userHands);
											String theUserName = userHands.replace(","+table,"");
											out.println("<br>"+theUserName+" " + theHand);
										
										}
									}
								}
								
							}

							out.println("</center></body></html>");
							out.flush();
							//	remote.close();
							break;
						}
						else if (thisLine.contains("GET /?username=Dealer")||thisLine.contains("GET /?username=dealer"))
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
							out.println("<html><head></head><body><H1>Welcome to the Black Jack Game</H1>");
							out.println("<H2><font color = red>Invalid Username Chosen</font></H2>");
							out.println("<form>Username: <input type=\"text\""
									+ " id=\"username\" name=\"username\"><br>"
									+ "<input type=\"submit\" value=\"Submit\"></form></body></html>");
							out.flush();
							//remote.close();
							break;
						}
						else if (thisLine.contains("GET /?username="))
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
							out.println("<html><head></head><body><H1>Lobby</H1>");
							
							out.println("<H2>Welcome "+user+"</H2>");
							for (String room : model.rooms.keySet())
							{
								out.println("<center>"+room
									+ "<br>"+model.rooms.get(room)
									+ "/6 Currently playing"
									+ "<form>"
									+  "<input type=\"hidden\" name=\"join\" value=\""+user+"\">"
									+  "<input type=\"hidden\" name=\"table\" value=\""+room+"\">"
									+  "<input type=\"submit\" value=\"Join\">"
									+ "</form>");
							}
							out.println("</center></body></html>");
								

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
							out.println("<html><head></head><body><H1>Welcome to the Black Jack Table</H1>");
							out.println("<H2>Enter A Username</H2>");
							out.println("<form>Username: <input type=\"text\""
									+ " id=\"username\" name=\"username\"><br>"
									+ "<input type=\"submit\" value=\"Submit\"></form></body></html>");
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