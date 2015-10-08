package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;

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
						Date currDate = new Date();
						for (String table : model.roomRestartTimes.keySet())
						{
							long diff = currDate.getTime() - model.roomRestartTimes.get(table).getTime();

							long diffMinutes = diff / (60 * 1000) % 60;
							if (diffMinutes>0)
							{
								model.roomRestartTimes.remove(table);
								model.rooms.remove(table);
								model.rooms.put(table, 0);
								model.roomsPhase.remove(table);
								model.roomsPhase.put(table,0);
								model.stays.remove(table);
								model.stays.put(table, "");
								model.splits.remove(table);
								model.splits.put(table, "");

								Iterator<Entry<String, String>> it = model.hands.entrySet().iterator();
								while(it.hasNext())
								{
									Entry<String, String> userName = (Entry<String, String>) it.next();
									if (userName.toString().contains(table))
									{
										System.out.println("Removing : "+userName);
										it.remove();
									}
								}

								System.out.println("Setting phase to 0 for table: "+table);
							}
						}
						Socket remote = s.accept();
						System.out.println("----------- REQUEST --------");
						//remote is now the connected socket
						BufferedReader in = new BufferedReader(new InputStreamReader(
								remote.getInputStream()));
						PrintWriter out = new PrintWriter(remote.getOutputStream());
						String  thisLine = null;
						thisLine = in.readLine();

						if (thisLine!=null && thisLine.contains("GET /?userSplit="))
						{
							System.out.println(thisLine);
							String user = thisLine;
							String table = "";
							user = user.replace("GET /?userSplit=", "");
							String[] tokens1 = user.split(" ");
							String[] tokens = tokens1[0].split("\\&");
							user = tokens[0];
							table = tokens[1];
							table = table.replace("table=", "");
							user = user.replaceAll("\\+", " ");
							user = user.replaceAll("%20", " ");
							user = user.replaceAll("%2B", " ");
							table = table.replaceAll("\\+", " ");
							table = table.replaceAll("%20", " ");
							table = table.replaceAll("%2B", " ");
							table.trim();
							int roomSize = model.rooms.get(table);
							roomSize++;
							model.rooms.remove(table);
							model.rooms.put(table,roomSize);
							if (model.hands.containsKey(user+","+table))
							{
								if (!model.splits.containsKey(table))
									model.splits.put(table,"");
								String currSplits = model.splits.get(table);
								if (currSplits.equals(""))
									currSplits = user;
								else
									currSplits = currSplits + "," + user;
								model.splits.remove(table);
								model.splits.put(table, currSplits);
								String currHand = model.hands.get(user+","+table);
								String[] cards = currHand.split(",");
								if (cards.length==2)
								{
									currHand = cards[0];
									String currHand2 = cards[1];
									currHand = currHand + "," + model.hit(model.deck);
									currHand2 = currHand2 + "," + model.hit(model.deck);
									model.hands.remove(user+","+table);
									model.hands.put(user+","+table,currHand);
									model.hands.put(user+" Second Hand,"+table,currHand2);
								}
							}
							// Send the response
							// Send the headers
							out.println("HTTP/1.0 200 OK");
							out.println("Content-Type: text/html");
							out.println("Server: Bot");
							// this blank line signals the end of the headers
							out.println("");
							// Send the HTML page
							out.println("<html><head><meta http-equiv=\"refresh\" content=\"0;url=/?join="+user+"&table="+table+"\" /></head></html>");

							out.flush();
							remote.close();
							break;
							
						}
						else if (thisLine.contains("GET /?join="))
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
							user = user.replaceAll("\\+", " ");
							user = user.replaceAll("%20", " ");
							user = user.replaceAll("%2B", " ");
							user = user.replaceAll(" Second Hand", "");
							table = table.replaceAll("\\+", " ");
							table = table.replaceAll("%20", " ");
							table = table.replaceAll("%2B", " ");
							table.trim();
							// Send the response
							// Send the headers
							out.println("HTTP/1.0 200 OK");
							out.println("Content-Type: text/html");
							out.println("Server: Bot");
							// this blank line signals the end of the headers
							out.println("");
							// Send the HTML page
							out.println("<html><head><meta http-equiv=\"refresh\" content=\"3;url=/?join="+user+"&table="+table+"\" />"
									+ "<script type=\"text/javascript\">"
									+ "function makeMeGlow()"
									+ "{  var myButton =document.getElementById('theButton');"
									+ "myButton.style.background = \"black\";"
									+ "myButton.style.color = \"white\";"
									+ "myButton.value = \"Stay\"; "
									+ "setTimeout('nowImGlowing()', 2000);"
									+ "}"

									+ "function nowImGlowing()"
									+"{  var myButton1 =document.getElementById('theButton');"

									+"myButton1.style.background = \"gold\";"
									+"myButton1.style.color = \"black\";"
									+ "myButton1.value = \"Stay\"; "
									+ "setTimeout('makeMeGlow()', 2000);"
									+ "}"
									+ "</script>"
									+ "</head><body onLoad=\"makeMeGlow()\"><H1>"+table+"</H1>");
							String name = user;
							if (name.contains("Second Hand"))
								name.replace("Second Hand", "");
							out.println("<H2>Welcome "+name+"</H2>");
							out.println("<br>");
							if (model.stays.get(table)!=null && model.stays.get(table).contains("Dealer"))
							{		
								currDate = new Date();

								long diff = currDate.getTime() - model.roomRestartTimes.get(table).getTime();

								long diffSeconds = diff / 1000 % 60;
								if (diffSeconds<60)
								{
									int diffy = 60-(int)diffSeconds;
									out.println("<center>There are "+ diffy + " seconds until table restarts for next round!</center>");	
									//display winner(s),
									int dealerScore = model.calculatePoints(model.hands.get("Dealer,"+table));
									if (dealerScore > 21)
										dealerScore = 0;
									String victors = "";
									String competitors = model.stays.get(table);
									String[] competitionArray;
									if (!competitors.contains(","))
									{
										competitionArray = new String[1];
										competitionArray[0] = competitors;
									}
									else
										competitionArray = competitors.split(",");
									for(String competitor: competitionArray)
									{
										competitor = competitor.trim();
										//System.out.println("Points for " + competitor + ": "+model.calculatePoints(model.hands.get(competitor+","+table)));
										
											if (model.calculatePoints(model.hands.get(competitor+","+table)) > dealerScore && model.calculatePoints(model.hands.get(competitor+","+table)) < 22)
											{
												competitor = competitor.replace("Second Hand","");
												if (!victors.contains(competitor))
												{
													if (victors.equals(""))
														victors = competitor;
													else
														victors = victors + ", " + competitor;
												}
											}
										
									}
									if (victors.equals("") && dealerScore < 22)
									{
										out.println("<br><center><h1> Dealer won</h1></center>");

									}
									else 
									{
										if (!victors.equals(""))
											out.println("<br><center><h1>"+victors+" won</h1></center>");
										else
											out.println("<br><center><h1> Draw!</h1></center>");
									}
								}
								else
								{
									out.println("<center>Get ready for next round!</center>");		

								}
								for (String userHands:model.hands.keySet())
								{
									if (userHands.contains(","+table))
									{
										if (userHands.contains(user+","+table))
										{
											//The new user's hand
											String theHand = model.hands.get(userHands);

											String unEditedHand = theHand;
											theHand.replace(",", " ");

											theHand = theHand.replace("spades", "<font size=13>&#9824;</font>");
											theHand = theHand.replace("hearts", "<font size=13>&#9829;</font>");
											theHand = theHand.replace("clubs", "<font size=13>&#9827;</font>");
											theHand = theHand.replace("diamonds", "<font size=13>&#9830;</font>");
											theHand = theHand.replaceAll(",", "</tr><tr><td></td><td>");

											String theUserName = userHands.replace(","+table,"");
											out.println("<center><br><table><tr><td><h1>"+theUserName+"("+model.calculatePoints(unEditedHand)+" points)</h1></td> <td>" + theHand +"</td></tr></table></center>");

										}
										else
										{
											String theHand = model.hands.get(userHands);
											String[] cards = theHand.split(",");
											theHand = "";
											for(String card : cards)
												theHand = theHand + ", "+card;
											String unEditedHand = theHand;

											theHand = theHand.replace("spades", "<font size=13>&#9824;</font>");
											theHand = theHand.replace("hearts", "<font size=13>&#9829;</font>");
											theHand = theHand.replace("clubs", "<font size=13>&#9827;</font>");
											theHand = theHand.replace("diamonds", "<font size=13>&#9830;</font>");
											theHand = theHand.replaceAll(",", "</td></tr><tr><td></td><td>");
											String theUserName = userHands.replace(","+table,"");
											out.println("<center><br><table><tr><td><h2>"+theUserName + "("+model.calculatePoints(unEditedHand)+" points)</h2></td> <td>" + theHand + "</td></tr></table></center>");
											if (model.stays.get(table).contains(theUserName))
											{
												out.println("<center>"+theUserName + " stays</center>");	
											}
										}
										
									}
									
								}
								
							}
							else
							{
								if (model.rooms.containsKey(table) && model.rooms.get(table)>5 && !model.hands.containsKey(user+","+table))
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
									{
										if (!model.hands.containsKey(user+","+table))
										{
											model.hands.put(user+","+table,model.serveCards(model.deck));
											int roomSize = model.rooms.get(table);
											roomSize++;
											model.rooms.remove(table);
											model.rooms.put(table,roomSize);
										}
									}
									for (String userHands:model.hands.keySet())
									{
										if (userHands.contains(","+table))
										{
											if (userHands.contains(user+","+table) || userHands.contains(user+" Second Hand,"+table))
											{
												//The new user's hand
												String theHand = model.hands.get(userHands);
												String unEditedHand = theHand;
												theHand.replace(",", " ");

												theHand = theHand.replace("spades", "<font size=13>&#9824;</font>");
												theHand = theHand.replace("hearts", "<font size=13>&#9829;</font>");
												theHand = theHand.replace("clubs", "<font size=13>&#9827;</font>");
												theHand = theHand.replace("diamonds", "<font size=13>&#9830;</font>");
												theHand = theHand.replaceAll(",", "</tr><tr><td></td><td>");

												String theUserName = userHands.replace(","+table,"");
												int score = model.calculatePoints(unEditedHand);
												
												out.println("<center><br><table><tr><td><h1>"+name+"("+score+" points)</h1></td> <td>" + theHand +"</td></tr></table></center>");
												//check to see if player already is staying
												String[] staying = model.stays.get(table).split(",");
												boolean flag = false;
												//cycle through each player, check if they match your username
												for (String stayer : staying)
												{
													if (stayer.equals(theUserName))
														flag = true;
												}
												//if no one matches your username, allow player to hit, stay,split.
												if (!flag)
												{
													if (score < 21)
													{
													out.println("<center><table><tr><td>"
															+ "<form>"
															+  "<input type=\"hidden\" name=\"userHit\" value=\""+theUserName+"\">"
															+  "<input type=\"hidden\" name=\"table\" value=\""+table+"\">"
															+  "<input type=\"submit\" value=\"Hit\">"
															+ "</form>");
													}
													if (score > 20)
													{
													out.println("</td><td><center><form>"
															+  "<input type=\"hidden\" name=\"userStay\" value=\""+theUserName+"\">"
															+  "<input type=\"hidden\" name=\"table\" value=\""+table+"\">"
															+  "<input id=\"theButton\" value=\"GLOW\" type=\"submit\">"
															+ "</form>");
													}
													else
													{
													out.println("</td><td><form>"
															+  "<input type=\"hidden\" name=\"userStay\" value=\""+theUserName+"\">"
															+  "<input type=\"hidden\" name=\"table\" value=\""+table+"\">"
															+  "<input type=\"submit\" value=\"Stay\">"
															+ "</form>");

													}
													if (model.splitPossible(unEditedHand) && !model.splits.get(table).contains(theUserName))
													{
														out.println("</td><td><form>"
																+  "<input type=\"hidden\" name=\"userSplit\" value=\""+theUserName+"\">"
																+  "<input type=\"hidden\" name=\"table\" value=\""+table+"\">"
																+  "<input type=\"submit\" value=\"Split\">"
																+ "</form>");

													}
													out.println("</td></tr></table></center>");
												}
											}
											else
											{
												String theHand = model.hands.get(userHands);
												String[] cards = theHand.split(",");
												cards[1] = "X of X";
												theHand = "";
												for(String card : cards)
													theHand = theHand + ", "+card;

												String unEditedHand = theHand;
												theHand = theHand.replace("spades", "<font size=13>&#9824;</font>");
												theHand = theHand.replace("hearts", "<font size=13>&#9829;</font>");
												theHand = theHand.replace("clubs", "<font size=13>&#9827;</font>");
												theHand = theHand.replace("diamonds", "<font size=13>&#9830;</font>");
												theHand = theHand.replaceAll(",", "</td></tr><tr><td></td><td>");
												String theUserName = userHands.replace(","+table,"");
												out.println("<center><br><table><tr><td><h2>"+theUserName + "("+model.calculatePoints(unEditedHand)+" points)</h2></td> <td>" + theHand + "</td></tr></table></center>");
												if (model.stays.get(table).contains(theUserName))
												{
													out.println("<center>"+theUserName + " stays</center>");	
												}
											}
										}
									}

								}
								else
								{
									
									for (String userHands:model.hands.keySet())
									{
										if (userHands.contains(","+table))
										{
											if (userHands.contains(user+","+table) || userHands.contains(user+" Second Hand,"+table))
											{
												//The new user's hand
												String theHand = model.hands.get(userHands);
												String unEditedHand = theHand;
												theHand.replace(",", " ");

												theHand = theHand.replace("spades", "<font size=13>&#9824;</font>");
												theHand = theHand.replace("hearts", "<font size=13>&#9829;</font>");
												theHand = theHand.replace("clubs", "<font size=13>&#9827;</font>");
												theHand = theHand.replace("diamonds", "<font size=13>&#9830;</font>");
												theHand = theHand.replaceAll(",", "</tr><tr><td></td><td>");

												String theUserName = userHands.replace(","+table,"");
												
												out.println("<center><br><table><tr><td><h1>"+name+"("+model.calculatePoints(unEditedHand)+" points)</h1></td> <td>" + theHand +"</td></tr></table></center>");
												//check to see if player already is staying
												String[] staying = model.stays.get(table).split(",");
												boolean flag = false;
												//cycle through each player, check if they match your username
												for (String stayer : staying)
												{
													if (stayer.equals(theUserName))
														flag = true;
												}
												//if no one matches your username, allow player to hit, stay,split.
												if (!flag)
												{
													out.println("<center><table><tr>");
													if (model.calculatePoints(unEditedHand)<21)
													{
														out.println("<td>"
															+ "<form>"
															+  "<input type=\"hidden\" name=\"userHit\" value=\""+theUserName+"\">"
															+  "<input type=\"hidden\" name=\"table\" value=\""+table+"\">"
															+  "<input type=\"submit\" value=\"Hit\">"
															+ "</form></td>");
													}
													out.println("<td><form>"
															+  "<input type=\"hidden\" name=\"userStay\" value=\""+theUserName+"\">"
															+  "<input type=\"hidden\" name=\"table\" value=\""+table+"\">"
															+  "<input type=\"submit\" value=\"Stay\">"
															+ "</form></td></tr></table></center>");
											
												}
											}
											else
											{
												String theHand = model.hands.get(userHands);

												String[] cards = theHand.split(",");
												cards[1] = "X of X";
												theHand = "";
												for(String card : cards)
													theHand = theHand + ", "+card;

												String unEditedHand = theHand;
												theHand = theHand.replace("spades", "<font size=13>&#9824;</font>");
												theHand = theHand.replace("hearts", "<font size=13>&#9829;</font>");
												theHand = theHand.replace("clubs", "<font size=13>&#9827;</font>");
												theHand = theHand.replace("diamonds", "<font size=13>&#9830;</font>");
												theHand = theHand.replaceAll(",", "</td></tr><tr><td></td><td>");
												String theUserName = userHands.replace(","+table,"");
												out.println("<center><br><table><tr><td><h2>"+theUserName + "("+model.calculatePoints(unEditedHand)+" points)</h2></td> <td>" + theHand + "</td></tr></table></center>");
												if (model.stays.get(table).contains(theUserName))
												{
													out.println("<center>"+theUserName + " stays</center>");	
												}
											}
										}
									}
								}
								
							}
							
							out.println("</center></body></html>");
							out.flush();
							remote.close();
							break;
						}
						else if (thisLine.contains("GET /?userHit="))
						{
							System.out.println(thisLine);
							String user = thisLine;
							String table = "";
							user = user.replace("GET /?userHit=", "");
							String[] tokens1 = user.split(" ");
							String[] tokens = tokens1[0].split("\\&");
							user = tokens[0];
							table = tokens[1];
							user = user.replaceAll("%20", " ");
							user = user.replaceAll("\\+", " ");
							user = user.replaceAll("%2B", " ");
							table = table.replaceAll("table=", "");
							table = table.replaceAll("\\+", " ");
							table = table.replaceAll("%20", " ");
							table = table.replaceAll("%2B", " ");
							table.trim();
							// Send the response
							// Send the headers

							if (model.roomsPhase.get(table)==1)
							{
								model.roomsPhase.remove(table);
								model.roomsPhase.put(table, 2);
							}
							String currentHand = model.hands.get(user+","+table);
							currentHand = currentHand + "," + model.hit(model.deck);
							model.hands.remove(user+","+table);
							model.hands.put(user+","+table,currentHand);
							out.println("HTTP/1.0 200 OK");
							out.println("Content-Type: text/html");
							out.println("Server: Bot");
							// this blank line signals the end of the headers
							out.println("");
							// Send the HTML page
							out.println("<html><head><meta http-equiv=\"refresh\" content=\"3;url=/?join="+user+"&table="+table+"\" />"
									+ "<script type=\"text/javascript\">"
									+ "function makeMeGlow()"
									+ "{  var myButton =document.getElementById('theButton');"
									+ "myButton.style.background = \"black\";"
									+ "myButton.style.color = \"white\";"
									+ "myButton.value = \"Stay\"; "
									+ "setTimeout('nowImGlowing()', 2000);"
									+ "}"

									+ "function nowImGlowing()"
									+"{  var myButton1 =document.getElementById('theButton');"

									+"myButton1.style.background = \"gold\";"
									+"myButton1.style.color = \"black\";"
									+ "myButton1.value = \"Stay\"; "
									+ "setTimeout('makeMeGlow()', 2000);"
									+ "}"
									+ "</script>"
									+ "</head><body onLoad=\"makeMeGlow()\"><H1>"+table+"</H1>");
							String name = user;
							if (name.contains("Second Hand"))
								name.replace("Second Hand", "");
							out.println("<H2>Welcome "+name+"</H2>");
							out.println("<br>");
							for (String userHands:model.hands.keySet())
							{
								if (userHands.contains(","+table))
								{
									if (userHands.contains(user+","+table) || userHands.contains(user+" Second Hand,"+table))
									{
										//The new user's hand
										String theHand = model.hands.get(userHands);

										String unEditedHand = theHand;
										theHand.replace(",", " ");

										theHand = theHand.replace("spades", "<font size=13>&#9824;</font>");
										theHand = theHand.replace("hearts", "<font size=13>&#9829;</font>");
										theHand = theHand.replace("clubs", "<font size=13>&#9827;</font>");
										theHand = theHand.replace("diamonds", "<font size=13>&#9830;</font>");
										theHand = theHand.replaceAll(",", "</tr><tr><td></td><td>");

										String theUserName = userHands.replace(","+table,"");
										System.out.println(unEditedHand);	
										int score = model.calculatePoints(unEditedHand);
										
										out.println("<center><br><table><tr><td><h1>"+name+"("+score+" points)</h1></td> <td>" + theHand +"</td></tr></table></center>");
										System.out.println(score);
										//check to see if player already is staying
										String[] staying = model.stays.get(table).split(",");
										boolean flag = false;
										//cycle through each player, check if they match your username
										for (String stayer : staying)
										{
											if (stayer.equals(theUserName))
												flag = true;
										}
										//if no one matches your username, allow player to hit, stay,split.
										if (!flag)
										{
											if (score < 21)
											{
												out.println("<center><table><tr><td>"
														+ "<form>"
														+  "<input type=\"hidden\" name=\"userHit\" value=\""+theUserName+"\">"
														+  "<input type=\"hidden\" name=\"table\" value=\""+table+"\">"
														+  "<input type=\"submit\" value=\"Hit\">"
														+ "</form>");
											}
											if (score > 20)
											{
												out.println("</td><td><center><form>"
														+  "<input type=\"hidden\" name=\"userStay\" value=\""+theUserName+"\">"
														+  "<input type=\"hidden\" name=\"table\" value=\""+table+"\">"
														+  "<input type=\"submit\" id=\"theButton\" value=\"GLOW\">"
														+ "</form>");
											}
											else
											{
												out.println("</td><td><form>"
														+  "<input type=\"hidden\" name=\"userStay\" value=\""+theUserName+"\">"
														+  "<input type=\"hidden\" name=\"table\" value=\""+table+"\">"
														+  "<input type=\"submit\" value=\"Stay\">"
														+ "</form>");
											}
	
											if (model.splitPossible(unEditedHand))
											{
												out.println("</td><td><form>"
														+  "<input type=\"hidden\" name=\"userSplit\" value=\""+theUserName+"\">"
														+  "<input type=\"hidden\" name=\"table\" value=\""+table+"\">"
														+  "<input type=\"submit\" value=\"Split\">"
														+ "</form>");
	
											}
										}
										out.println("</td></tr></table></center>");
									}
									else
									{
										String theHand = model.hands.get(userHands);
										String[] cards = theHand.split(",");
										cards[1] = "X of X";
										theHand = "";
										for(String card : cards)
											theHand = theHand + ", "+card;
										String unEditedHand = theHand;

										theHand = theHand.replace("spades", "<font size=13>&#9824;</font>");
										theHand = theHand.replace("hearts", "<font size=13>&#9829;</font>");
										theHand = theHand.replace("clubs", "<font size=13>&#9827;</font>");
										theHand = theHand.replace("diamonds", "<font size=13>&#9830;</font>");
										theHand = theHand.replaceAll(",", "</td></tr><tr><td></td><td>");
										String theUserName = userHands.replace(","+table,"");
										out.println("<center><br><table><tr><td><h2>"+theUserName + "("+model.calculatePoints(unEditedHand)+" points)</h2></td> <td>" + theHand + "</td></tr></table></center>");
										if (model.stays.get(table).contains(theUserName))
										{
											out.println("<center>"+theUserName + " stays</center>");	
										}
									}
								}
							}
							out.println("</center></body></html>");
							out.flush();
							remote.close();
							break;

						}
						else if (thisLine.contains("GET /?userStay="))
						{

							System.out.println(thisLine);
							String user = thisLine;
							String table = "";
							user = user.replace("GET /?userStay=", "");
							String[] tokens1 = user.split(" ");
							String[] tokens = tokens1[0].split("\\&");
							user = tokens[0];
							table = tokens[1];
							table = table.replaceAll("table=", "");
							table = table.replaceAll("\\+", " ");
							table = table.replaceAll("%20", " ");
							table = table.replaceAll("%2B", " ");
							user = user.replaceAll("\\+", " ");
							user = user.replaceAll("%20", " ");
							user = user.replaceAll("%2B", " ");
							table.trim();
							// Send the response
							// Send the headers
							out.println("HTTP/1.0 200 OK");
							out.println("Content-Type: text/html");
							out.println("Server: Bot");

							// this blank line signals the end of the headers
							out.println("");
							out.println("<html><head><meta http-equiv=\"refresh\" content=\"0;url=/?join="+user+"&table="+table+"\" /></head><body>");
							if (model.roomsPhase.get(table)==null)
							{
								model.roomsPhase.put(table, 0);
							}
							if (model.roomsPhase.get(table)!=null && model.roomsPhase.get(table)==1)
							{
								model.roomsPhase.remove(table);
								model.roomsPhase.put(table, 2);
							}
							if (model.roomsPhase.get(table)<1)
							{
								out.println("<br><a href=\"/?join="+user+"&table="+table+"\">Click here to play again!</a>");
							}
							else{
								String staying = model.stays.get(table);
								String[] stayers = model.stays.get(table).split(",");
								boolean flag = false;
								for(String stayer : stayers)
								{
									if (stayer.equals(user))
										flag = true;
								}
								if (!flag)
								{
									if (staying.equals(""))
										staying = user;
									else
										staying = staying + ", "+ user;
									model.stays.remove(table);
									model.stays.put(table,staying);
								}

								// check if all users have stayed
								// if so, display results page, if not, display game page.

								int count = model.stays.get(table).length() - model.stays.get(table).replace(",", "").length();
								if (count == model.rooms.get(table)-1)
								{
									// every user has played
									// Set the dealers hand, if dealer has not played.
							
									
									// Send the HTML page
									out.println("<H1>"+table+"</H1>");
									String name = user;
									if (name.contains("Second Hand"))
										name.replace("Second Hand", "");
									out.println("<H2>Welcome "+name+", now displaying results</H2>");
									out.println("<br>");
									if (model.stays.get(table).contains("Dealer"))
									{
										//dealer's final points were determined
										//start a countdown to reset the table.

										currDate = new Date();
										if (!model.roomRestartTimes.containsKey(table))
											model.roomRestartTimes.put(table, currDate);

										long diff = currDate.getTime() - model.roomRestartTimes.get(table).getTime();

										long diffSeconds = diff / 1000 % 60;
										System.out.println("display scores for :" + table);
										if (diffSeconds>60)
										{
											int diffy = 60 - (int)diffSeconds;
											out.println("<br> There are "+diffy+" seconds until "
													+ "the table restarts for another round.");

											//display winner(s),
											int dealerScore = model.calculatePoints(model.hands.get("Dealer,"+table));
											if (dealerScore > 21)
												dealerScore = 0;
											String victors = "";
											String competitors = model.stays.get(table);
											String[] competitionArray = competitors.split(",");
											for(String competitor: competitionArray)
											{
												competitor = competitor.trim();
												System.out.println("Points for " + competitor + ": "+model.calculatePoints(model.hands.get(competitor+","+table)));
												if (model.calculatePoints(model.hands.get(competitor+","+table)) > dealerScore && model.calculatePoints(model.hands.get(competitor+","+table)) < 22)
												{
													competitor = competitor.replace("Second Hand","");
													if (!victors.contains(competitor))
													{
														if (victors.equals(""))
															victors = competitor;
														else
															victors = victors + ", " + competitor;
													}
												}
											}
											if (victors.equals(""))
											{
												out.println("<br><center><h1> Dealer won</h1></center>");

											}
											else 
											{
												out.println("<br><center><h1> "+victors+" won</h1></center>");

											}

										}

									}
									else
									{
										int dealerPoints = model.calculatePoints(model.hands.get("Dealer,"+table));
										String dealerHand = model.hands.get("Dealer,"+table);
										while (dealerPoints<17)
										{
											dealerHand = dealerHand + ","+model.hit(model.deck);
											dealerPoints = model.calculatePoints(dealerHand);
											model.hands.remove("Dealer,"+table);
											model.hands.put("Dealer,"+table,dealerHand);
										}
										String stayingUsers = model.stays.get(table);
										stayingUsers = stayingUsers + ",Dealer";
										model.stays.remove(table);
										model.stays.put(table,stayingUsers);
										model.roomRestartTimes.remove(table);
										model.roomRestartTimes.put(table, new Date());
										//display winner(s),
										int dealerScore = model.calculatePoints(model.hands.get("Dealer,"+table));
										if (dealerScore > 21)
											dealerScore = 0;
										String victors = "";
										String competitors = model.stays.get(table);
										String[] competitionArray = competitors.split(",");
										for(String competitor: competitionArray)
										{
											competitor = competitor.trim();
											System.out.println("Trying to get points for : "+competitor);
											System.out.println("Points for " + competitor + ": "+model.calculatePoints(model.hands.get(competitor+","+table)));
											
												if (model.calculatePoints(model.hands.get(competitor+","+table)) > dealerScore && model.calculatePoints(model.hands.get(competitor+","+table)) < 22)
												{
													competitor = competitor.replace("Second Hand","");
													if (!victors.contains(competitor))
													{
														if (victors.equals(""))
															victors = competitor;
														else
															victors = victors + ", " + competitor;
													}
												}
											
										}	
										if (victors.equals(""))
										{
											out.println("<br><center><h1> Dealer won</h1></center>");

										}
										else 
										{
											out.println("<br><center><h1>"+victors+" won</h1></center>");

										}
									}
									for (String userHands:model.hands.keySet())
									{
										if (userHands.contains(","+table))
										{
											if (userHands.contains(user+","+table))
											{
												//The new user's
												String theHand = model.hands.get(userHands);
												String unEditedHand = theHand;
												theHand.replace(",", " ");

												theHand = theHand.replace("spades", "<font size=13>&#9824;</font>");
												theHand = theHand.replace("hearts", "<font size=13>&#9829;</font>");
												theHand = theHand.replace("clubs", "<font size=13>&#9827;</font>");
												theHand = theHand.replace("diamonds", "<font size=13>&#9830;</font>");
												theHand = theHand.replaceAll(",", "</tr><tr><td></td><td>");

												String theUserName = userHands.replace(","+table,"");
												out.println("<center><br><table><tr><td><h1>"+theUserName+"("+model.calculatePoints(unEditedHand)+" points)<h1> </td> <td>" + theHand +"</td></tr></table></center>");
											}
											else
											{
												//everyone elses hand
												String theHand = model.hands.get(userHands);
												String unEditedHand = theHand;

												theHand = theHand.replace("spades", "<font size=13>&#9824;</font>");
												theHand = theHand.replace("hearts", "<font size=13>&#9829;</font>");
												theHand = theHand.replace("clubs", "<font size=13>&#9827;</font>");
												theHand = theHand.replace("diamonds", "<font size=13>&#9830;</font>");
												theHand = theHand.replaceAll(",", "</td></tr><tr><td></td><td>");
												String theUserName = userHands.replace(","+table,"");
												out.println("<center><br><table><tr><td><h2>"+theUserName + "("+model.calculatePoints(unEditedHand)+" points)</h2></td> <td>" + theHand + "</td></tr></table></center>");
											}
										}
									}

								}
								else
								{
									String name = user;
									if (name.contains("Second Hand"))
										name.replace("Second Hand", "");
									out.println("<H2>Welcome "+name+"</H2>");
									out.println("<br>");

									if (model.stays.get(table).contains("Dealer"))
									{		
										
										currDate = new Date();

										long diff = currDate.getTime() - model.roomRestartTimes.get(table).getTime();

										long diffSeconds = diff / 1000 % 60;
										if (diffSeconds<60)
										{
											int diffy = 60-(int)diffSeconds;
											out.println("<center>There are "+ diffy + " seconds until table restarts for next round!</center>");	
											//display winner(s),
											int dealerScore = model.calculatePoints(model.hands.get("Dealer,"+table));
											if (dealerScore > 21)
												dealerScore = 0;
											String victors = "";
											String competitors = model.stays.get(table);
											String[] competitionArray = competitors.split(",");
											for(String competitor: competitionArray)
											{
												competitor = competitor.trim();
												System.out.println("Points for " + competitor + ": "+model.calculatePoints(model.hands.get(competitor+","+table)));
												
													if (model.calculatePoints(model.hands.get(competitor+","+table)) > dealerScore && model.calculatePoints(model.hands.get(competitor+","+table)) < 22)
													{
														competitor = competitor.replace("Second Hand","");
														if (!victors.contains(competitor))
														{
															if (victors.equals(""))
																victors = competitor;
															else
																victors = victors + ", " + competitor;
														}
													}
												
											}
											if (victors.equals(""))
											{
												out.println("<br><center><h1> Dealer won </h1></center>");

											}
											else 
											{
												out.println("<br><center><h1>"+victors+" won </h1></center>");

											}
										}
										else
										{
											out.println("<center>Get ready for next round!</center>");		

										}
										for (String userHands:model.hands.keySet())
										{
											if (userHands.contains(","+table))
											{
												if (userHands.contains(user+","+table))
												{
													//The new user's hand
													String theHand = model.hands.get(userHands);

													String unEditedHand = theHand;
													theHand.replace(",", " ");

													theHand = theHand.replace("spades", "<font size=13>&#9824;</font>");
													theHand = theHand.replace("hearts", "<font size=13>&#9829;</font>");
													theHand = theHand.replace("clubs", "<font size=13>&#9827;</font>");
													theHand = theHand.replace("diamonds", "<font size=13>&#9830;</font>");
													theHand = theHand.replaceAll(",", "</tr><tr><td></td><td>");

													String theUserName = userHands.replace(","+table,"");
													out.println("<center><br><table><tr><td><h1>"+theUserName+"("+model.calculatePoints(unEditedHand)+" points)</h1></td> <td>" + theHand +"</td></tr></table></center>");

												}
												else
												{
													String theHand = model.hands.get(userHands);
													String[] cards = theHand.split(",");
													theHand = "";
													for(String card : cards)
														theHand = theHand + ", "+card;
													String unEditedHand = theHand;

													theHand = theHand.replace("spades", "<font size=13>&#9824;</font>");
													theHand = theHand.replace("hearts", "<font size=13>&#9829;</font>");
													theHand = theHand.replace("clubs", "<font size=13>&#9827;</font>");
													theHand = theHand.replace("diamonds", "<font size=13>&#9830;</font>");
													theHand = theHand.replaceAll(",", "</td></tr><tr><td></td><td>");
													String theUserName = userHands.replace(","+table,"");
													out.println("<center><br><table><tr><td><h2>"+theUserName + "("+model.calculatePoints(unEditedHand)+" points)</h2></td> <td>" + theHand + "</td></tr></table></center>");
													if (model.stays.get(table).contains(theUserName))
													{
														out.println("<center>"+theUserName + " stays</center>");	
													}
												}
											}
										}
									}
										
									
								}


							}
							if (model.roomsPhase.get(table) == 0)
								out.println("<html><head><meta http-equiv=\"refresh\" content=\"3;url=/?join="+user+"&table="+table+"\" /></head><body><center><br><a href=\"/?join="+user+"&table="+table+"\">Click here to play again!</a>");
							
							out.println("</center></body></html>");
							out.flush();
							remote.close();
							break;

						}
						else if (thisLine.contains("GET /?username=Dealer")||thisLine.contains("GET /?username=dealer")||(thisLine.contains("GET /?username=")&&thisLine.contains("&")))
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
							user = user.replaceAll("\\+", " ");
							user = user.replaceAll("%20", " ");
							user = user.replaceAll("%2B", " ");

							// Send the response
							// Send the headers
							out.println("HTTP/1.0 200 OK");
							out.println("Content-Type: text/html");
							out.println("Server: Bot");
							// this blank line signals the end of the headers
							out.println("");
							// Send the HTML page
							out.println("<html><head><meta http-equiv=\"refresh\" content=\"3;url=/?username="+user+"\" /></head><body><H1>Lobby</H1>");
							String name = user;
							if (name.contains("Second Hand"))
								name.replace("Second Hand", "");
							out.println("<H2>Welcome "+name+"</H2>");
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