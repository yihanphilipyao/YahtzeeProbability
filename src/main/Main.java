package main;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {

        try {
            ProbabilityState curState = new ProbabilityState(5, 6, true, new int[]{1, 1, 1, 2, 3});
            
            System.out.print(Arrays.toString(curState.getRepeatedValues()));
            System.out.print(Arrays.toString(curState.getRepeatedSubsets()));
            
            System.out.printf("%8d/%d\n\n", curState.getNumerator(), curState.getDenominator());
        } catch (InvalidArgumentException e) {
            System.out.println(e.getMessage());
        }
        
		ArrayList<int[]> stateList = RollState.getStateList(3, 6, false);
		
		for (int[] state : stateList) {
			System.out.print(Arrays.toString(state));
			
			try {
				ProbabilityState curState = new ProbabilityState(3, 6, false, state);

				System.out.print(Arrays.toString(curState.getRepeatedValues()));
				System.out.print(Arrays.toString(curState.getRepeatedSubsets()));
				
				System.out.printf("\t%d/%d\n", curState.getNumerator(), curState.getDenominator());
			} catch (InvalidArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
		
		ProbabilitySet probabilitySet = new ProbabilitySet(5, 3, 6);
		System.out.println(Arrays.toString(probabilitySet.getIntegerProbabilitySet()));
		
		MarkovStateMachine markovState = new MarkovStateMachine(5, 6, 3);
		System.out.println(Arrays.deepToString(markovState.getMarkovMatrix()));

	}

}
