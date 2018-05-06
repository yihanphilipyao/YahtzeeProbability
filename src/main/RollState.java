package main;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for finding all roll states for a certain number of dice and faces.
 * @author Yihan Philip Yao (philipyao@live.ca)
 * @version 0.5
 * @since 0.3
 */

public class RollState {
	
	private static ArrayList<int[]> stateList = new ArrayList<int[]>();
	
	/**
	 * Generate list of all roll states.
	 * @since 0.3
	 * 
	 * @param numOfDice Number of dice rolled.
	 * @param numOfFaces Number of faces per die.
	 * @param firstRoll If the current roll is the initial roll.
	 * @return List of integer arrays representing states.
	 */
	
	public static ArrayList<int[]> getStateList(int numOfDice, int numOfFaces, boolean firstRoll) {
		stateList.clear();
		
		int[] initialSequence = new int[numOfDice];
		Arrays.fill(initialSequence, 0);
		generateStates(numOfDice, numOfFaces, firstRoll, 1, 0, numOfDice, initialSequence);
		
		return stateList;
	}
	
	/**
	 * Recursive state generation.
	 * <p>
	 * Recursive generation of integer arrays representing the probability states. For initial rolls,
	 * states must start with at least ceil(n/f) 1s, with n representing the remaining number of dice to be
	 * rolled and f representing the number of faces on each die, and at most n 1s. From there, the
	 * remaining elements must be at least ceil(n/f) and at most the quantity of the previous element
	 * (otherwise it would move up in priority and be one of the previous elements). For other rolls,
	 * states have no bounds for or depending on 1s but remaining elements must follow the same rules
	 * stated.
	 * @since 0.3
	 * 
	 * @param numOfDice Number of dice remaining to be filled.
	 * @param numOfFaces Number of faces per die.
	 * @param helper Decides what rule set the current element follows.
	 * @param nextFace The next element to be listed.
	 * @param nextPos The next starting position to fill elements.
	 * @param prevSetSize The quantity of the precious element.
	 * @param existingSequence The current sequence of elements.
	 */
	
	private static void generateStates(int numOfDice, int numOfFaces, boolean helper, int nextFace, int nextPos, int prevSetSize, int[] existingSequence) {
		if (numOfDice == 0) {
			stateList.add(existingSequence);
			return;
		}
		int i = (int) Math.ceil((double) numOfDice/numOfFaces);
		if (!helper) i = 0;
		for (; i <= prevSetSize && i <= numOfDice; i++) {
			int[] newSequence = existingSequence.clone();
			Arrays.fill(newSequence, nextPos, nextPos + i, nextFace);
			if (helper) {
				generateStates(numOfDice - i, numOfFaces, true, nextFace + 1, nextPos + i, i, newSequence);
			} else {
				generateStates(numOfDice - i, numOfFaces, true, nextFace + 1, nextPos + i, numOfDice - i, newSequence);
			}
		}
	}

}
