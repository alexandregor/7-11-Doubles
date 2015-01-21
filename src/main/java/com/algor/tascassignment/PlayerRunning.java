package com.algor.tascassignment;

public class PlayerRunning implements Runnable {

	private Player player;
	private SevenElevenDoubles game;

	public PlayerRunning(SevenElevenDoubles game, Player player) {
		this.player = player;
		this.game = game;
	}

	@Override
	public void run() {
		runPlayer();
	}

	synchronized private void runPlayer() {

		while (game.isGameActive() && player.isActivePlayer()) {

			synchronized(game) {

				if (player.isRolling()) {
					game.roll(player);
				}

				try {
					game.notifyAll();
					if(game.playersInGame() > 1 ) {
						game.wait();
					}
				} catch (InterruptedException e) {
					System.out.println( "waiting for player '" + player.getName() + "' was interrupted unexpectedly");
					e.printStackTrace();
				}
			}

			while(player.getDrinksMore() > 0 && (player.getDrinksDone() < game.getMax())) {
				try {
					Thread.sleep( player.getTimePerDrink()*1000);
				} catch (InterruptedException e) {
					System.out.println( "Sleep after roll for player '" + player.getName() + "'  was interrupted unexpectedly");
					e.printStackTrace();
				}
				player.decreaseDrinks();
				System.out.println(player.getName() + " in run player is done drinking\n");
			}
			if(player.getDrinksDone() >= game.getMax()) {
				player.setActivePlayer(false);
				System.out.println(player.getName() + " says: I've had too many. I need to stop'.\n");
			}
		}

		if (! player.isActivePlayer()) {
			game.removePlayer(player);
		}

	}
}


