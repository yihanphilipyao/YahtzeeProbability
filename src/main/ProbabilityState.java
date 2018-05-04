package main;

import java.util.Arrays;

/**
 * Object class for individual roll states and their probabilities.
 * @author Yihan Philip Yao (philipyao@live.ca)
 * @version 0.4
 * @since 0.1
 */

public class ProbabilityState {
	
	private int numOfDice;
	private int numOfFaces;
	private boolean firstRoll;
	private int[] sequence;
	private int[] repeatedValues;
	private int[] repeatedSubsets;
	private int numOfRepeatedValues;
	
	/**
	 * Object Constructor.
	 * @since 0.1
	 * 
	 * @param numOfDice Number of dice rolled.
	 * @param numOfFaces Number of faces per die.
	 * @param firstRoll If the current roll is the initial roll (changes probability calculations).
	 * @param sequence The sequence of numbers which represent the state of the dice.
	 * @throws InvalidArgumentException Validation checks for <code>sequence</code> matching
	 * 			<code>numOfDice</code> and <code>numOfFaces</code>.
	 */
	
	public ProbabilityState(int numOfDice, int numOfFaces, boolean firstRoll, int[] sequence) throws InvalidArgumentException {
		
		if (numOfDice != sequence.length || numOfFaces < sequence[sequence.length-1]) {
			throw new InvalidArgumentException("Sequence does not match parameters given.");
		}
		
		this.numOfDice = numOfDice;
		this.numOfFaces = numOfFaces;
		this.firstRoll = firstRoll;
		this.sequence = sequence;
		
		repeatedValues = findConcatDuplicates(sequence, true);
		repeatedSubsets = findConcatDuplicates(repeatedValues, firstRoll);
		
		numOfRepeatedValues = 0;
		if (!firstRoll && sequence[0] == 1) numOfRepeatedValues = 1;
		for (int i = 0; i < repeatedValues.length; i++) {
			numOfRepeatedValues += repeatedValues[i] - 1;
		}
	}
	
	/**
	 * Compressing arrays by recording the count of matching elements.
	 * @since 0.1
	 * 
	 * @param original Original array.
	 * @param firstRoll If the current roll is the initial roll.
	 * @return Compressed array.
	 */
	
	private int[] findConcatDuplicates(int[] original, boolean firstRoll) {
		int length = 0;
		int[] longArray = original.clone();
		Arrays.fill(longArray, 1);
		
		for (int i = 1 + (!firstRoll && sequence[0] == 1 ? 1 : 0); i < original.length; i++) {
			if (original[i] == original[i-1]) {
				longArray[length]++;
			} else {
				length++;
			}
		}
		
		return Arrays.copyOf(longArray, ++length);
	}
	
	/**
	 * @return Original sequence.
	 * @since 0.1
	 */
	
	public int[] getSequence() {
		return sequence;
	}
	
	/**
	 * @return Number of repeated values array.
	 * @since 0.1
	 */
	
	public int[] getRepeatedValues() {
		return repeatedValues;
	}
	
	/**
	 * @return Number of identically sized subsets array.
	 * @since 0.1
	 */
	
	public int[] getRepeatedSubsets() {
		return repeatedSubsets;
	}
	
	/**
	 * @return Total number of repeated values.
	 * @since 0.1
	 */
	
	public int getNumOfRepeatedValues() {
		return numOfRepeatedValues;
	}
	
	/**
	 * @return If the current roll is the initial roll (changes probability calculations).
	 * @since 0.1
	 */
	
	public boolean isFirstRoll() {
		return firstRoll;
	}
	
	/**
	 * Calculates numerator of probability such that rolls of the same <code>numOfDice</code> and
	 * <code>numOfFaces</code> have common denominator.
	 * <p>
	 * Calculates according to n!/[(A!B!C!...!)*(a!b!c!...!)] * (f-1)!/(r-n+f)! for first roll and
	 * n!/[(A!B!C!...!)*(a!b!c!...!)] * (f-1)!/(r-n+f-1)! for other rolls, where n represents the number of
	 * dice, A,B,C... represent the number of times an item is repeated in the set, a,b,c... represent the
	 * number of identically sized subsets in the set, f represents the number of faces per die, and r
	 * represents the number of repeated items (excluding the initial unique item and for subsequent rolls,
	 * all desired items).
	 * @since 0.1
	 * 
	 * @return Integer numerator for probability of roll.
	 */
	
	public long getNumerator() {
		long numeratorCoefficient, denominatorCoefficient = 1;
		long rollProbability;
		
		numeratorCoefficient = factorial((long) numOfDice);
		
		for (int i = 0; i < repeatedValues.length; i++) {
			denominatorCoefficient *= factorial((long) repeatedValues[i]);
		}
		for (int i = 0; i < repeatedSubsets.length; i++) {
			denominatorCoefficient *= factorial((long) repeatedSubsets[i]);
		}
		
		if (firstRoll) {
			rollProbability = factorial((long) numOfFaces - 1, (long) numOfRepeatedValues - numOfDice + numOfFaces);
		} else {
			rollProbability = factorial((long) numOfFaces - 1, (long) numOfRepeatedValues - numOfDice + numOfFaces - 1);
		}
		
		if (numeratorCoefficient % denominatorCoefficient != 0) {
			System.out.println("Interesting");
		}
		
		return (numeratorCoefficient / denominatorCoefficient) * rollProbability;
	}
	
	/**
	 * Common denominator for rolls of the same <code>numOfDice</code> and <code>numOfFaces</code>.
	 * @since 0.1
	 * 
	 * @return Lowest integer denominator for all rolls.
	 */
	
	public long getDenominator() {
		if (firstRoll) {
			return (long) Math.pow(numOfFaces, numOfDice - 1);
		} else {
			return (long) Math.pow(numOfFaces, numOfDice);
		}
	}
	
	/**
	 * Simple recursive factorial function.
	 * @since 0.1
	 * 
	 * @param number Integer to be taken the factorial of.
	 * @return Result of factorial.
	 */
	
	private long factorial(long number) {
		if (number == 0 || number == 1) {
			return 1;
		} else {
			return number*factorial(number-1);
		}
	}
	
	/**
	 * Efficient recursive factorial function given factorial division.
	 * @since 0.1
	 * 
	 * @param start Numerator of factorial division.
	 * @param end Denominator of factorial division.
	 * @return Result of factorial division.
	 */
	
	private long factorial(long start, long end) {
		if (start == end) {
			return 1;
		} else {
			return start*factorial(start-1, end);
		}
	}

}
