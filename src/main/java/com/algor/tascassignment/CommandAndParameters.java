package com.algor.tascassignment;

import java.util.ArrayList;

public class CommandAndParameters {

	private String command = "";
		private ArrayList<String> parameters = new ArrayList<String>();

		CommandAndParameters() {}

		CommandAndParameters(String command, ArrayList<String> parameters) {
			this.command = command;
			this.parameters = parameters;
		}

		static CommandAndParameters parseCommand(String sourceCommand) {
			if(sourceCommand == null) return new CommandAndParameters();

			sourceCommand = sourceCommand.trim();
			if (sourceCommand.length() == 0) {
				System.out.println("Error: no command inserted");
				return new CommandAndParameters();
			}

			String[] commandAndArgs = sourceCommand.split("\\s+");
			String command = commandAndArgs[0].toLowerCase();

			ArrayList<String> parameters = new ArrayList<String>();
			for (int i = 1; i < commandAndArgs.length; i++) {
				parameters.add(commandAndArgs[i]);
			}

			return new CommandAndParameters(command, parameters);
		}

		String getCommand() {
			return command;
		}

		ArrayList<String> getParameters() {
			return parameters;
		}
	}

