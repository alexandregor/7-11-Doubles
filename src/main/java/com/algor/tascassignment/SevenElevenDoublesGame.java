package com.algor.tascassignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SevenElevenDoublesGame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Utils.printRules();
		System.out.println("\nWaiting for players...");

		SevenElevenDoublesImpl sevenElevenDoublesGame = getGameParameters();
		sevenElevenDoublesGame.startGame();

	}

	private static SevenElevenDoublesImpl getGameParameters() {

		SevenElevenDoublesImpl sevenElevenDoublesGameInstance = new SevenElevenDoublesImpl();

	    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	    String command = null;
	    ArrayList<String> parameters = null;
	    boolean readyToStartFlag;
	    do {
			String sourceCommand = null;
			try {
				System.out.print("> ");
				sourceCommand = bufferRead.readLine();
			} catch (IOException e) {
				System.err.println("Error while getting command");
				e.printStackTrace();
			}


			CommandAndParameters commandAndParameters = CommandAndParameters.parseCommand(sourceCommand);
			command = commandAndParameters.getCommand();
			parameters = commandAndParameters.getParameters();
			readyToStartFlag = sevenElevenDoublesGameInstance.runCommand(command, parameters);
			System.out.println();

		} while (! readyToStartFlag);

		return sevenElevenDoublesGameInstance;
	}

}
