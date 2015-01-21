package com.algor.tascassignment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

public class SevenElevenDoublesImpl implements SevenElevenDoubles {

	private int speed = 2;
	private int max = 5;

	private int currentlyDrinking = 0;

	volatile private boolean isActive = true;

	private Random dice1Generator = null;
	private Random dice2Generator = null;
	private Random drinkerGenerator = null;

	ArrayList<Player> players = new ArrayList<Player>();
	HashSet<String> playerNames = new HashSet<String>();

	boolean runCommand(String command, ArrayList<String> parameters) {

		if (command == null) {
			Utils.messageOfIncorrectCommandName(command);
			return false;
		}

		COMMAND partToRun;
		try {
			partToRun = COMMAND.valueOf(command.toUpperCase());
		} catch(IllegalArgumentException iae) {
			Utils.messageOfIncorrectCommandName(command);
			return false;
		}

		return partToRun.runCommand(this, parameters);

	}

	@Override
	public boolean printRules() {
		Utils.printRules();
		return false;
	}

	@Override
	public void startGame() {

		System.out.println("Game parameters" + toString());

		long seed = (new Date()).getTime();
		dice1Generator = new Random( seed );
		dice2Generator = new Random( seed - 125000000 );
		drinkerGenerator = new Random( seed + 125000000 );

		ArrayList<Thread> threads = new ArrayList<Thread>();
		players.get(0).setRolling();
		for (Player player : players) {
			PlayerRunning playerRunning = new PlayerRunning(this, player);
			Thread rollingThread = new Thread(playerRunning);
			threads.add(rollingThread);
		}

		for (Thread thread : threads) {
			thread.start();
		}

		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				System.out.println("The thread " + thread + " was interrupted unexpectedly");
				e.printStackTrace();
			}
		}

		StringBuilder winnerMessage = new StringBuilder( players.get(0).getName()).append(" is the winner");
		StringBuilder message = new StringBuilder("====STATUS====\n").append("The game is over. ").append(winnerMessage).append(".\n");
		System.out.println(message);

		System.out.println(winnerMessage.append("!"));

	}

	@Override
	public boolean add(ArrayList<String> params) {
		String commandName = "ADD";

		if(params == null || params.size() < 2) {
			Utils.messageOfIncorrectNumberOfArguments(commandName);
			return false;
		}

		String playerName = params.get(0);
		int timePerDrink = 0;
		if (playerNames.contains(playerName.toUpperCase())) {
			Utils.messageOfDuplicatePlayerName(playerName, commandName);
		} else {
			String timePerDrinkStr = params.get(1);
			try {
				timePerDrink = Integer.parseInt(timePerDrinkStr);
			} catch(NumberFormatException nfe) {
				Utils.messageOfIncorrectParameter(timePerDrinkStr, commandName);
			}
		}

		Player player = null;
		if(timePerDrink > 0) {
			playerNames.add(playerName.toUpperCase());
			player = new Player(this, playerName, timePerDrink);
			players.add(player);
			System.out.println(playerName +", who can finish a drink in " + timePerDrink + " seconds, has joined the game." );
		}

		return false;
	}

	@Override
	/**
	 * The method can be moved to Utilites
	 */
	public boolean speed(ArrayList<String> params) {
		String commandName = "SPEED";
		if(params == null || params.size() < 1) {
			Utils.messageOfIncorrectNumberOfArguments(commandName);
		}

		String speedStr = params.get(0);
		try {
			setSpeed(Integer.parseInt(speedStr));
			System.out.println("Pause time between rolls is " + getSpeed() + " seconds");
		} catch(NumberFormatException nfe) {
			Utils.messageOfIncorrectParameter(speedStr, commandName);
		}

		return false;
	}

	@Override
	public boolean max(ArrayList<String> params) {
		String commandName = "MAX";

		if(params == null || params.size() < 1) {
			Utils.messageOfIncorrectNumberOfArguments(commandName);
		}

		String maxStr = params.get(0);
		try {
			setMax(Integer.parseInt(maxStr));
			System.out.println("Maximum number of drinks is " + getMax());
		} catch(NumberFormatException nfe) {
			Utils.messageOfIncorrectParameter(maxStr, commandName);
		}

		return false;
	}

	@Override
	public boolean start(ArrayList<String> params) {
		boolean retValue = true;
		if(players.size() < 2) {
			System.out.println("At least 2 players required to start game");
			retValue = false;
		}

		return retValue;
	}

	@Override
	public int getSpeed() {
		return speed;
	}

	private void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public int getMax() {
		return max;
	}

	private void setMax(int max) {
		this.max = max;
	}

	@Override
	synchronized public int playersInGame() {
		return players.size();
	}

	@Override
	public boolean isGameActive() {
		return isActive;
	}

	private void finishGame() {
		isActive = false;
	}

	@Override
	public String toString() {
		return "SevenElevenDoublesImpl [speed=" + speed + ", max=" + max
				+ ", currentlyDrinking=" + currentlyDrinking + ", playerNames=" + playerNames + ", players=" + players + "]";
	}

	@Override
	synchronized public void roll(Player player) {
		int dice1 = dice1Generator.nextInt(6) + 1;
		int dice2 = dice2Generator.nextInt(6) + 1;

		if(players.size() < 2) {
			finishGame();
			return;
		}

		StringBuilder message = new StringBuilder("====STATUS====\n");
		message.append("There are " + players.size() + " players\n");
		message.append("It is " + player.getName() + "'s turn\n");
		for(Player playerFromList : players) {
			message.append(playerFromList.getName() + " has had " + playerFromList.getDrinksDone() + " drinks");
			if(playerFromList.getDrinksMore() > 0) {
				message.append(" and is currently drinking " + playerFromList.getDrinksMore() + " more");
			}
			message.append("\n");
		}
		System.out.println(message);

		boolean winner = false;
		message = (new StringBuilder( player.getName())).append(" rolled" );

		if (dice1 == dice2) {
			message.append(" double " + dice1 + "'s");
			winner = true;
		} else {
			int sum = dice1 + dice2;
			message.append(" a " + (dice1 + dice2));
			if(sum == 7 || sum == 11) {
				winner = true;
			}
		}

		System.out.println(message.append("\n"));

		player.resetRolling();
		int nextRoller = 0;
		String nextRollerName = player.getName();

		if(winner) {
			chooseDrinker(player);
		} else {
			nextRollerName = changeRoller(player);
		}
		nextRoller = findByName(nextRollerName);
		players.get(nextRoller).setRolling();

		try {
			notifyAll();
			Thread.sleep(getSpeed()*1000);
		} catch (InterruptedException e) {
			System.out.println( "Sleep after roll was interrupted unexpectedly");
			e.printStackTrace();
		}

	}

	private int findByName(String playerName) {

		int order = 0;
		for (; order < players.size(); order++) {
			if(players.get(order).getName().equals(playerName)) {
				break;
			}
		}
		return order;
	}

	private void chooseDrinker(Player player) {
		int playerNo = findByName(player.getName());
		int arrayLen = players.size();
		int drinker = (playerNo + drinkerGenerator.nextInt(arrayLen - 1) + 1) % arrayLen;

		StringBuilder message = (new StringBuilder( player.getName())).append(" says '" +  players.get(drinker).getName() + ", drink'\n");
		System.out.println(message);
		players.get(drinker).increaseDrinks();
	}

	private String changeRoller(Player player) {
		String retValue = player.getName();

		if(currentlyDrinking > 0) {
			return retValue;
		}

		int order = (findByName(retValue) + 1) % players.size();
		return players.get(order).getName();
	}

	@Override
	synchronized public void removePlayer(Player player) {
		players.remove(player);
		notifyAll();
	}

	@Override
	synchronized public void decreaseDrinkers() {
		currentlyDrinking--;
	}

	@Override
	synchronized public void increaseDrinkers() {
		currentlyDrinking++;
	}
}
