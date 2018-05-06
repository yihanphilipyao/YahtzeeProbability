package main;

import java.util.ArrayList;

/**
 * Object class for finding the probability set for the Markov chain
 * @author Yihan Philip Yao (philipyao@live.ca)
 * @version 0.5
 * @since 0.5
 */

public class MarkovStateMachine {
	
	private ArrayList<ProbabilitySet> probabilitySets;
	private double[][] markovMatrix;
	private int numOfDice;
	private int numOfFaces;
	private int numOfRolls;
	
	/**
	 * Object Constructor.
	 * @since 0.5
	 * 
	 * @param numOfDice Number of dice rolled.
	 * @param numOfFaces Number of faces per die.
	 * @param numOfRolls Number of roll attempts.
	 */
	public MarkovStateMachine(int numOfDice, int numOfFaces, int numOfRolls) {
		this(numOfDice, numOfFaces);
		this.numOfRolls = numOfRolls;
	}
	
	public MarkovStateMachine(int numOfDice, int numOfFaces) {
		this.numOfDice = numOfDice;
		this.numOfFaces = numOfFaces;
		markovMatrix = new double[numOfDice][numOfDice];
		
		probabilitySets = new ArrayList<ProbabilitySet>();
		
		int j = 0;
		ProbabilitySet probabilitySet = new ProbabilitySet(numOfDice, numOfDice, numOfFaces);
		probabilitySets.add(probabilitySet);
		markovMatrix[j] = probabilitySet.getProbabilitySet();
		
		for (int i = numOfDice - 2; i > 0; i--) {
			probabilitySet = new ProbabilitySet(numOfDice, i, numOfFaces);
			probabilitySets.add(probabilitySet);
			System.arraycopy(probabilitySet.getProbabilitySet(), 0, markovMatrix[++j], j, probabilitySet.getProbabilitySet().length);
		}

		System.arraycopy(new double[]{1}, 0, markovMatrix[++j], j, 1);
		
		diagonalize();
	}

	private void diagonalize() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @return Probability Markov matrix, giving probabilities that any state will move to another.
	 * @since 0.5
	 */
	
	public double[][] getMarkovMatrix() {
		return markovMatrix;
	}

}
