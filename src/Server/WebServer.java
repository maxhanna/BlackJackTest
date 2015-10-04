package Server;
import Client.ClientVariables;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	String serverAddress = "192.168.2.13:8000";
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
				

				// read the data sent. 
				// stop reading once a blank line is hit. This
				// blank line signals the end of the client HTTP
				// headers.
				try{
					while (true) {
						Socket remote = s.accept();
						System.out.println("----------- REQUEST --------");
						//remote is now the connected socket
						BufferedReader in = new BufferedReader(new InputStreamReader(
								remote.getInputStream()));
						PrintWriter out = new PrintWriter(remote.getOutputStream());
						String  thisLine = null;
						thisLine = in.readLine();
					
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
								model.shuffleDeck(model.deck);
								model.hands.put("Dealer,"+table, model.serveCards(model.deck));
								System.out.println("Initialize game table: "+table);
							}
							if (model.roomsPhase.get(table)!=null 
									&& model.roomsPhase.get(table)<2)
							{
								if (model.rooms.get(table)<6)
									if (!model.hands.containsKey(user+","+table))
										model.hands.put(user+","+table,model.serveCards(model.deck));
								for (String userHands:model.hands.keySet())
								{
									if (userHands.contains(","+table))
									{
										if (userHands.contains(user+","+table))
										{
											//The new user's hand
											String theHand = model.hands.get(userHands);
											theHand.replace(",", " ");

											theHand = theHand.replace("spades", "<font size=13>&#9824;</font>");
											theHand = theHand.replace("hearts", "<font size=13>&#9829;</font>");
											theHand = theHand.replace("clubs", "<font size=13>&#9827;</font>");
											theHand = theHand.replace("diamonds", "<font size=13>&#9830;</font>");
											theHand = theHand.replaceAll(",", "</tr><tr><td></td><td>");

											String theUserName = userHands.replace(","+table,"");
											out.println("<center><br><table><tr><td>"+theUserName+"</td> <td>" + theHand +"</td></tr></table></center>");

											out.println("<center><table><tr><td>"
													+ "<form>"
													+  "<input type=\"hidden\" name=\"userHit\" value=\""+user+"\">"
													+  "<input type=\"hidden\" name=\"table\" value=\""+table+"\">"
													+  "<input type=\"submit\" value=\"Hit\">"
													+ "</form>");
											out.println("</td><td><form>"
													+  "<input type=\"hidden\" name=\"userStay\" value=\""+user+"\">"
													+  "<input type=\"hidden\" name=\"table\" value=\""+table+"\">"
													+  "<input type=\"submit\" value=\"Stay\">"
													+ "</form></td></tr></table></center>");
										}
										else
										{
											String theHand = model.hands.get(userHands);
											theHand.replace(",", " ");

											theHand = theHand.replace("spades", "<font size=13>&#9824;</font>");
											theHand = theHand.replace("hearts", "<font size=13>&#9829;</font>");
											theHand = theHand.replace("clubs", "<font size=13>&#9827;</font>");
											theHand = theHand.replace("diamonds", "<font size=13>&#9830;</font>");
											theHand = theHand.replaceAll(",", "</td></tr><tr><td></td><td>");
											String theUserName = userHands.replace(","+table,"");
											out.println("<center><br><table><tr><td>"+theUserName + "</td> <td>" + theHand + "</td></tr></table></center>");

										}
									}
								}

							}

							out.println("</center></body></html>");
							out.flush();
							remote.close();
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
							remote.close();
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
								remote.close();
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
							remote.close();
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