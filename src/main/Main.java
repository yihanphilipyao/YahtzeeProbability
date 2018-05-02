package main;

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

	}

}
