package com.algor.tascassignment;

import java.util.ArrayList;

public enum COMMAND {

	HELP {
		@Override
		boolean runCommand(SevenElevenDoubles game, ArrayList<String> params) {
			game.printRules();
			return false;
		}
	},

	ADD {
		@Override
		boolean runCommand(SevenElevenDoubles game, ArrayList<String> params) {
			return game.add(params);
		}
	},

	SPEED {
		@Override
		boolean runCommand(SevenElevenDoubles game, ArrayList<String> params) {
			return game.speed(params);
		}
	},

	MAX {
		@Override
		boolean runCommand(SevenElevenDoubles game, ArrayList<String> params) {
			return game.max(params);
		}
	},

	START {
		@Override
		boolean runCommand(SevenElevenDoubles game, ArrayList<String> params) {
			return game.start(params);
		}
	};

	abstract boolean runCommand(SevenElevenDoubles game, ArrayList<String> params);

}
