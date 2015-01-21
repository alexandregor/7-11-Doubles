package com.algor.tascassignment;

public class Player {

	SevenElevenDoubles game = null;

	private String name;

	volatile private int drinksDone;
	volatile private int drinksMore;

	private int timePerDrink;

	volatile private boolean isRolling = false;
	volatile private boolean isActivePlayer = true;

	public boolean isActivePlayer() {
		return isActivePlayer;
	}

	public void setActivePlayer(boolean isActive) {
		this.isActivePlayer = isActive;
	}

	public void setRolling() {
		this.isRolling = true;
	}

	public void resetRolling() {
		this.isRolling = false;
	}

	public Player(SevenElevenDoubles game, String name, int timePerDrink) {
		this.game = game;
		this.name = name;
		this.timePerDrink = timePerDrink;
	}

	public String getName() {
		return name;
	}

	public int getDrinksDone() {
		return drinksDone;
	}

	public void setDrinksDone(int drinksDone) {
		this.drinksDone = drinksDone;
	}

	public int getDrinksMore() {
		return drinksMore;
	}

	public void setDrinksMore(int drinksMore) {
		this.drinksMore = drinksMore;
	}

	public int getTimePerDrink() {
		return timePerDrink;
	}

	public boolean isRolling() {
		return isRolling;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", drinksDone=" + drinksDone
				+ ", drinksMore=" + drinksMore + ", timePerDrink="
				+ timePerDrink + "]";
	}

	public void decreaseDrinks() {
		drinksDone++;
		drinksMore--;
		if (drinksMore == 0) {
			game.decreaseDrinkers();
		}
		StringBuilder message = new StringBuilder(getName()).append( " finished 1 drink");
		if(drinksMore > 0) {
			message.append(", but still has ").append(drinksMore).append(" drinks to finish");
		}
		System.out.println(message);
	}

	public void increaseDrinks() {
		if ( drinksMore == 0 )  {
			game.increaseDrinkers();
		}
		drinksMore++;
	}

}
