package com.algor.tascassignment;

import java.util.ArrayList;

public interface SevenElevenDoubles {

	boolean printRules();

	boolean add(ArrayList<String> params);

	boolean speed(ArrayList<String> params);
	int getSpeed();

	boolean max(ArrayList<String> params);
	int getMax();

	boolean start(ArrayList<String> params);

	void startGame();

	boolean isGameActive();

	void roll(Player player);

	int playersInGame();
	void removePlayer(Player player);

	void decreaseDrinkers();

	void increaseDrinkers();

//	void runPlayer(Player player);

}
