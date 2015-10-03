package Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class BlackJackVariables {
	public HashMap<String,Integer> rooms = new HashMap<String,Integer>();
	public HashMap<String,Integer> roomsPhase = new HashMap<String,Integer>();
	public ArrayList<String> deck = new ArrayList<String>();
	public HashMap<String,String> hands = new HashMap<String,String>();
	
	public String serveCards(ArrayList<String> deckOfCards){
		if (deckOfCards.size() > 1)
		{
			String cards = ("" + deckOfCards.get(deckOfCards.size()-1) + "," + deckOfCards.get(deckOfCards.size()-2));
			deckOfCards.remove(deckOfCards.size()-1);
			deckOfCards.remove(deckOfCards.size()-1);
			return cards;
		}
		else
			return "";
	}
	public void createDeck(ArrayList<String> deck){
		deck.clear();
		for (int suit = 0; suit < 5; suit ++){
			for (int num = 1; num < 14; num ++)
			{
				if (num == 1)
				{
					if (suit == 1)
						deck.add("Ace" + " of clubs");
					else if (suit == 2)
						deck.add("Ace" + " of spades");
					else if (suit == 3)
						deck.add("Ace" + " of diamonds");
					else if (suit == 4)
						deck.add("Ace" + " of hearts");
				}
				else if (num == 11)
				{
					if (suit == 1)
						deck.add("Jack" + " of clubs");
					else if (suit == 2)
						deck.add("Jack" + " of spades");
					else if (suit == 3)
						deck.add("Jack" + " of diamonds");
					else if (suit == 4)
						deck.add("Jack" + " of hearts");
				}
				else if (num == 12)
				{
					if (suit == 1)
						deck.add("Queen" + " of clubs");
					else if (suit == 2)
						deck.add("Queen" + " of spades");
					else if (suit == 3)
						deck.add("Queen" + " of diamonds");
					else if (suit == 4)
						deck.add("Queen" + " of hearts");
				}
				else if (num == 13)
				{
					if (suit == 1)
						deck.add("King" + " of clubs");
					else if (suit == 2)
						deck.add("King" + " of spades");
					else if (suit == 3)
						deck.add("King" + " of diamonds");
					else if (suit == 4)
						deck.add("King" + " of hearts");
				}
				else
				{
					if (suit == 1)
						deck.add( num + " of clubs");
					else if (suit == 2)
						deck.add( num + " of spades");
					else if (suit == 3)
						deck.add( num + " of diamonds");
					else if (suit == 4)
						deck.add( num + " of hearts");
				}

			}
		}
	}
	public void shuffleDeck(ArrayList<String> deck){
		Collections.shuffle(deck);
	}
	public BlackJackVariables()
	{
		rooms.put("Carleton Room", 0);
		roomsPhase.put("Carleton Room", 0);
		createDeck(deck);
	}

}
