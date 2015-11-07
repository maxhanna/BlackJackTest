package Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class BlackJackVariables {
	public HashMap<String,Integer> rooms = new HashMap<String,Integer>();
	public HashMap<String,Integer> roomsPhase = new HashMap<String,Integer>();
	public ArrayList<String> deck = new ArrayList<String>();
	public HashMap<String,String> hands = new HashMap<String,String>();
	public HashMap<String,String> stays = new HashMap<String,String>();
	public HashMap<String,String> splits = new HashMap<String,String>();
	public HashMap<String,Date> roomRestartTimes = new HashMap<String,Date>();
	//person restart times contains the join game time of the user
	public HashMap<String,Date> personRestartTimes = new HashMap<String,Date>();
	public boolean splitPossible(String cards)
	{
		int aceCount = cards.length() - cards.replace("Ace", "").length();
		int kingCount = cards.length() - cards.replace("King", "").length();
		int queenCount = cards.length() - cards.replace("Queen", "").length();
		int jackCount = cards.length() - cards.replace("Jack", "").length();
		int tenCount = cards.length() - cards.replace("10", "").length();
		int nineCount = cards.length() - cards.replace("9", "").length();
		int eightCount = cards.length() - cards.replace("8", "").length();
		int sevenCount = cards.length() - cards.replace("7", "").length();
		int sixCount = cards.length() - cards.replace("6", "").length();
		int fiveCount = cards.length() - cards.replace("5", "").length();
		int fourCount = cards.length() - cards.replace("4", "").length();
		int threeCount = cards.length() - cards.replace("3", "").length();
		int twoCount = cards.length() - cards.replace("2", "").length();
		aceCount = aceCount / 3;
		kingCount = kingCount / 4;
		queenCount = queenCount / 5;
		jackCount = jackCount / 4;
		tenCount = tenCount / 2;
		if ((cards.length() - cards.replace(",", "").length()) > 1)
			return false;
		else if (aceCount > 1 || kingCount > 1 || queenCount > 1
				|| jackCount > 1 || tenCount > 1 || nineCount > 1
				|| eightCount > 1 || sevenCount > 1 || sixCount > 1 
				|| fiveCount > 1 || fourCount > 1 || threeCount > 1
				|| twoCount > 1)
			return true;
		else 
			return false;
	}
	public int calculatePoints(String cards)
	{
		//System.out.println("calculating score for: " + cards);
		int count = 0;
		int aceCount = cards.length() - cards.replace("Ace", "").length();
		int kingCount = cards.length() - cards.replace("King", "").length();
		int queenCount = cards.length() - cards.replace("Queen", "").length();
		int jackCount = cards.length() - cards.replace("Jack", "").length();
		int tenCount = cards.length() - cards.replace("10", "").length();
		int nineCount = cards.length() - cards.replace("9", "").length();
		int eightCount = cards.length() - cards.replace("8", "").length();
		int sevenCount = cards.length() - cards.replace("7", "").length();
		int sixCount = cards.length() - cards.replace("6", "").length();
		int fiveCount = cards.length() - cards.replace("5", "").length();
		int fourCount = cards.length() - cards.replace("4", "").length();
		int threeCount = cards.length() - cards.replace("3", "").length();
		int twoCount = cards.length() - cards.replace("2", "").length();
		aceCount = aceCount / 3;
		kingCount = kingCount / 4;
		queenCount = queenCount / 5;
		jackCount = jackCount / 4;
		tenCount = tenCount / 2;
		/*System.out.println("Aces : " + aceCount + " Kings: " + kingCount + " Queens: " + queenCount
				+ " Jacks: " + jackCount + " 10s: " + tenCount + " 9s: " + nineCount + " 8s: " + eightCount + " 7s: " + sevenCount + " 6s: " + sixCount + " 5s: " + fiveCount + " 4s: " + fourCount + " 3s: " + threeCount + " 2s: " + twoCount);
		*/	
		boolean usedAce = false;
		if (cards.contains("Ace") || cards.contains("ace"))
		{
			if (aceCount > 1)
			{
				count = count + aceCount;
				usedAce = true;
			}
			else
				count = count + 11;
		}
		if (cards.contains("King") || cards.contains("king"))
		{
			count = (kingCount * 10) + count;
			if (aceCount > 0 && count > 21 && !usedAce)
			{
				usedAce = true;
				count = count-10;
			}
		}
		if (cards.contains("Queen") || cards.contains("queen"))
		{
			count = (queenCount * 10) + count;
			if (aceCount > 0 && count > 21 && !usedAce)
			{
				usedAce = true;
				count = count-10;
			}
		}
		if (cards.contains("Jack") || cards.contains("jack"))
		{
			count =  (jackCount * 10) + count;
			if (aceCount > 0 && count > 21 && !usedAce)
			{
				usedAce = true;
				count = count-10;
			}
		}
		if (cards.contains("10"))
		{
			count = (tenCount * 10) + count;
			if (aceCount > 0 && count > 21 && !usedAce)
			{
				usedAce = true;
				count = count-10;
			}
		}
		if (cards.contains("9"))
		{
			count = (nineCount * 9) + count;
			if (aceCount > 0 && count > 21 && !usedAce)
			{
				usedAce = true;
				count = count-10;
			}
		}
		if (cards.contains("8"))
		{
			count = (eightCount * 8) + count;
			if (aceCount > 0 && count > 21 && !usedAce)
			{
				usedAce = true;
				count = count-10;
			}
		}
		if (cards.contains("7"))
		{
			count = (sevenCount * 7) + count;
			if (aceCount > 0 && count > 21 && !usedAce)
			{
				usedAce = true;
				count = count-10;
			}
		}
		if (cards.contains("6"))
		{
			count = (sixCount * 6) + count;
			if (aceCount > 0 && count > 21 && !usedAce)
			{
				usedAce = true;
				count = count-10;
			}
		}
		if (cards.contains("5"))
		{
			count = (fiveCount * 5) + count;
			if (aceCount > 0 && count > 21 && !usedAce)
			{
				usedAce = true;
				count = count-10;
			}
		}
		if (cards.contains("4"))
		{
			count = (fourCount * 4) + count;
			if (aceCount > 0 && count > 21 && !usedAce)
			{
				usedAce = true;
				count = count-10;
			}
		}
		if (cards.contains("3"))
		{
			count = (threeCount * 3) + count;
			if (aceCount > 0 && count > 21 && !usedAce)
			{
				usedAce = true;
				count = count-10;
			}
		}
		if (cards.contains("2"))
		{
			count = (twoCount * 2) + count;
			if (aceCount > 0 && count > 21 && !usedAce)
			{
				usedAce = true;
				count = count-10;
			}
		}

		if ((cards.length() - cards.replace(",", "").length()) > 6)
			count = 21;
		//System.out.println("score : " + count);
		return count;
	}
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
	public String hit(ArrayList<String> deckOfCards){
		if (deckOfCards.size() > 0)
		{
			String card = ("" + deckOfCards.get(deckOfCards.size()-1));
			deckOfCards.remove(deckOfCards.size()-1);
			return card;
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
		stays.put("Carleton Room", "");
		splits.put("Carleton Room", "");
		roomsPhase.put("Carleton Room", 0);
		createDeck(deck);
	}

}
