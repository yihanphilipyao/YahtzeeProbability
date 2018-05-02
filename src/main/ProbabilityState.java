package main;

import java.util.Arrays;

public class ProbabilityState {
	
	private int numOfDice;
	private int numOfFaces;
	private boolean firstRoll;
	private int[] sequence;
	private int[] repeatedValues;
	private int[] repeatedSubsets;
	private int numOfRepeatedValues;
	
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
	
	public int[] getSequence() {
		return sequence;
	}
	
	public int[] getRepeatedValues() {
		return repeatedValues;
	}
	
	public int[] getRepeatedSubsets() {
		return repeatedSubsets;
	}
	
	public int getNumOfRepeatedValues() {
		return numOfRepeatedValues;
	}
	
	public boolean isFirstRoll() {
		return firstRoll;
	}
	
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
	
	public long getDenominator() {
		if (firstRoll) {
			return (long) Math.pow(numOfFaces, numOfDice - 1);
		} else {
			return (long) Math.pow(numOfFaces, numOfDice);
		}
	}
	
	private long factorial(long number) {
		if (number == 0 || number == 1) {
			return 1;
		} else {
			return number*factorial(number-1);
		}
	}
	
	private long factorial(long start, long end) {
		if (start == end) {
			return 1;
		} else {
			return start*factorial(start-1, end);
		}
	}

}
