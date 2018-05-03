package main;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {

        try {
            ProbabilityState curState = new ProbabilityState(5, 6, true, new int[]{1, 1, 1, 2, 3});
            
            System.out.print(Arrays.toString(curState.getRepeatedValues()));
            System.out.print(Arrays.toString(curState.getRepeatedSubsets()));
            
            System.out.printf("%8d/%d\n", curState.getNumerator(), curState.getDenominator());
        } catch (InvalidArgumentException e) {
            System.out.println(e.getMessage());
        }
        
        
        RollState initial = new RollState(5, 6, true);
		ArrayList<int[]> stateList = initial.getStateList();
		
		for (int[] state : stateList) {
			System.out.print(Arrays.toString(state));
			
			try {
				ProbabilityState curState = new ProbabilityState(5, 6, initial.isFirstRoll(), state);

				System.out.print(Arrays.toString(curState.getRepeatedValues()));
				System.out.print(Arrays.toString(curState.getRepeatedSubsets()));
				
				System.out.printf("\t%d/%d\n", curState.getNumerator(), curState.getDenominator());
			} catch (InvalidArgumentException e) {
				System.out.println(e.getMessage());
			}
		}

	}

}
