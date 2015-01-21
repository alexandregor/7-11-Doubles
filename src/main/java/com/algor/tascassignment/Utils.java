package com.algor.tascassignment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {

	private final static String INCORRECT_COMMAND_NAME = "Incorrect command name ";
	private final static String INCORRECT_NUMBER_OF_ARGUMENTS = "Incorrect number of arguments for ";
	private final static String INCORRECT_PARAMETER = "Incorrect parameter ";
	private final static String DUPLICATE_PLAYER_NAME = "Duplicate player name ";

	static void printRules() {

		try {
			InputStream is = SevenElevenDoublesGame.class.getClassLoader().getResourceAsStream("Rules.txt");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
			String ruleString = null;
			while ( (ruleString = bufferedReader.readLine()) != null) {
				System.out.println(ruleString);
			}
		} catch (Exception e) {
			System.err.println("Error while getting access to the rules file. The game cannot be run.");
			e.printStackTrace();
			System.exit(100);
		}
	}

	static void messageOfIncorrectCommandName(String command) {
		System.out.println(INCORRECT_COMMAND_NAME + "'" + command + "'");
	}

	static void messageOfIncorrectNumberOfArguments(String command) {
		System.out.println(INCORRECT_NUMBER_OF_ARGUMENTS + "'" + command + "'");
	}

	public static void messageOfIncorrectParameter(String parameter, String command) {
		System.out.println(INCORRECT_PARAMETER + "'" + parameter + "'" + " for command '" + command + "'");
	}

	public static void messageOfDuplicatePlayerName(String playerName, String command) {
		System.out.println(DUPLICATE_PLAYER_NAME + "'" + playerName + "'" + " for command '" + command + "'");
	}

}
