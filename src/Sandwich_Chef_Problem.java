import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * This class proposes a solution to the infamous Sandwich Chef Problem
 * @author Dare Balogun 101062340
 *
 */
public class Sandwich_Chef_Problem {
	
	// Declare all our sandwich ingredients
	public enum Ingredient { Bread, PeanutButter, Jam }
	
	// Declare a static table
	public static final Table table = new Table();
	
	public static class Table {
		// Table must have at most 2 ingredients at any time
		private ArrayList<Ingredient> contents = new ArrayList<Ingredient>(2);
		
		// Initially empty
		private boolean empty = true;
		
		
		/**
		 * Synchronized method that the agent calls to put 2 ingredients on table
		 * @param one the first ingredient to be put on the table
		 * @param two the second ingredient to be put on the table
		 */
		public synchronized void put(Ingredient one, Ingredient two) {
			while (!empty) {
				try {
					wait();
				} catch (InterruptedException e) {
					return;
				}
			}
			
			// Table is empty so wait to slow it down
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Then put the 2 ingredients in the ArrayList and print results
			contents = new ArrayList<Ingredient>(2);
			contents.add(one);
			contents.add(two);
			
			// Signal that the table is now full
			empty = false;
			System.out.println("Agent: Placed " + one.name() + " and " + two.name() + " on the table\n");
			notifyAll();
		}
		
		
		/**
		 * Synchronized method that only returns the 2 items on the table if the ingredient passed in is not one of them
		 * @param ingredient The ingredient of the Chef that calls the method
		 * @return ArrayList of the items currently on the table
		 */
		public synchronized ArrayList<Ingredient> get(Ingredient ingredient){
			while (empty || contents.contains(ingredient)) {
				try {
					wait();
				} catch (InterruptedException e) {
					return null;
				}
			}
			
			// If the table is not empty and the table does not contain the ingredient passes in
			// Wait a bit to slow things down
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Return the contents, notify any waiting processes and return
			ArrayList<Ingredient> items = contents;
			contents = null;
			empty = true;
			System.out.println("Chef " + ingredient.name() + " just consumed a sandwich!\n");
			notifyAll();
			return items;
		}
	}

	/**
	 * The agent class has infinite supply of all 3 ingredients and it continously supplies 2 of them randomly
	 * @author Dare Balogun 101062340
	 *
	 */
	public static class Agent extends Thread {
		private static ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>(Arrays.asList(Ingredient.Bread, Ingredient.Jam, Ingredient.PeanutButter));
		
		@Override
		public void run() {
			while (true) {
				// Shuffle ingredients
				Collections.shuffle(ingredients);
				table.put(ingredients.get(0), ingredients.get(1));
			}
		}
	}
	
	/**
	 * The Chef class has has infinite supply of one ingredient and it continously gets ingredients from the table
	 * @author Dare Balogun 101062340
	 *
	 */
	public class Chef extends Thread {
		
		// Each chef has an ingredient of infinite supply
		private Ingredient ingredient;
		
		// Each chef must be initialized to one of the 3 ingredients
		public Chef (String ingredient) {
			
			// Determine which ingredient this chef has infinite supply of
			if (ingredient == "bread") {	
				this.ingredient = Ingredient.Bread;
			} else if (ingredient == "peanut butter") {
				this.ingredient = Ingredient.PeanutButter;
			} else if (ingredient == "jam") {
				this.ingredient = Ingredient.Jam;
			} else {
				throw new IllegalArgumentException("The Chef constructor must be passed an ingredient that is either 'bread', 'peanut butter' or 'jam'");
			}
		}
		
		@Override
		public void run() {
			while (true) {
				table.get(ingredient);
			}
		}
	}
	
	public static void main(String[] args) {
		Sandwich_Chef_Problem s = new Sandwich_Chef_Problem();
		
		// Create all the threads for the agent and the chefs
		Chef peanut_butter = s.new Chef("peanut butter");
		Chef jam = s.new Chef("jam");
		Chef bread = s.new Chef("bread");
		Agent agent = new Agent();
		
		System.out.println("Sandwich Chef Problem Starting....\n");
		agent.start();
		peanut_butter.start();
		jam.start();
		bread.start();
		
	}
}
