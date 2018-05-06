package main;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Object class for finding the probability set for the Markov chain
 * @author Yihan Philip Yao (philipyao@live.ca)
 * @version 0.5
 * @since 0.4
 */

public class ProbabilitySet {
	
	private long[] probabilitySet;
	private long denominator;
	private ArrayList<int[]> stateList;
	private ProbabilityState[] probabilityList;
	
	/**
	 * Object Constructor.
	 * @since 0.4
	 * 
	 * @param totalNumOfDice Total (initial) number of dice rolled.
	 * @param remainingNumOfDice Number of dice rolled this roll.
	 * @param numOfFaces Number of faces per die.
	 */
	
	public ProbabilitySet(int totalNumOfDice, int remainingNumOfDice, int numOfFaces) {
		boolean firstRoll = totalNumOfDice == remainingNumOfDice;
		stateList = RollState.getStateList(remainingNumOfDice, numOfFaces, firstRoll);
		
		probabilityList = new ProbabilityState[stateList.size()];
		probabilitySet = new long[remainingNumOfDice+1];
		Arrays.fill(probabilitySet, 0);
		
		long probabilityCheck = 0;
		
		for (int[] state : stateList) {
			try {
				ProbabilityState curState = new ProbabilityState(remainingNumOfDice, numOfFaces, firstRoll, state);
				int index = 0;
				if (state[0] == 1) {
					index = curState.getRepeatedValues()[0];
				} else if (curState.getRepeatedValues()[0] > totalNumOfDice - remainingNumOfDice){
					index = curState.getRepeatedValues()[0] - (totalNumOfDice - remainingNumOfDice);
				}
				if (index == 1 && firstRoll) {
					index = 0;
				}
				long numerator = curState.getNumerator();
				probabilitySet[index] += numerator;
				probabilityCheck += numerator;
			
				probabilityList[stateList.indexOf(state)] = curState;
			} catch(InvalidArgumentException e) {
				probabilitySet = null;
				stateList = null;
				probabilityList = null;
				System.out.println("Something went wrong");
			}
			
		}
		
		denominator = probabilityList[0].getDenominator();
		
		if (probabilityCheck != denominator) {
			probabilitySet = null;
			stateList = null;
			probabilityList = null;
			System.out.println("Something went wrong");
		}
	}
	
	/**
	 * Combining probabilities from individual states to aggregate wholes.
	 * <p>
	 * Finding the index by the number of 1s in the state (how much closer to the goal) or, in edge cases,
	 * based on the most prevalent value in the non-initial state if they are more than the previous roll.
	 * @since 0.4
	 * 
	 * @return Array of probabilities with indexes corresponding to increase in desired dice.
	 */
	
	public double[] getProbabilitySet() {
		double[] probabilitySetReal = new double[probabilitySet.length];
		for (int i = 0; i < probabilitySet.length; i++) {
			probabilitySetReal[i] = (double) probabilitySet[i] / denominator;
		}
		return probabilitySetReal;
	}
	
	/**
	 * @return Array of probability numerators with indexes corresponding to increase in desired dice.
	 * @since 0.5
	 */
	
	public long[] getIntegerProbabilitySet() {
		return probabilitySet;
	}
	
	/**
	 * @return List of all roll states.
	 * @since 0.4
	 */
	
	public ArrayList<int[]> getStateList () {
		return stateList;
	}

	/**
	 * @return Array of all probabilities corresponding to the roll states
	 * @since 0.4
	 */
	
	public ProbabilityState[] getProbabilityList () {
		return probabilityList;
	}

}
