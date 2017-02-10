package texttechno.task3.ortograph.newImp;

import java.util.ArrayList;

public class matrixTest {

	public static void main(String[] args) {
		ArrayList<Integer> ab = new ArrayList<>();
		ArrayList<ArrayList<Integer>> all = new ArrayList<>();

		ab.add(1);
		ab.add(2);
		all.add(ab);
		ab = new ArrayList<>();
		ab.add(3);
		ab.add(4);
		all.add(ab);
		System.out.println(all);
		System.out.println(calcRandSumsOfX(all));
		System.out.println(calcRandSumsOfY(all));

	}
	private static ArrayList<Integer> calcRandSumsOfX(ArrayList<ArrayList<Integer>> shrinkMatrix2) {
		ArrayList<Integer> sums = new ArrayList<>();

		for (int i = 0; i < shrinkMatrix2.size(); i++) {
			sums.add(countRowSum(shrinkMatrix2, i));
		}
		return sums;
	}
	
	private static Integer countRowSum(ArrayList<ArrayList<Integer>> matrix, int i) {
		Integer result = 0;
		for (int j = 0; j < matrix.size(); j++) {
			Integer value = matrix.get(j).get(i);
			result+=value;
		}
		return result;
	}
	
	private static ArrayList<Integer> calcRandSumsOfY(ArrayList<ArrayList<Integer>> shrinkMatrix2) {
		ArrayList<Integer> sums = new ArrayList<>();

		for (int i = 0; i < shrinkMatrix2.size(); i++) {
			sums.add(countColSum(shrinkMatrix2, i));
		}
		return sums;
	}
	
	private static Integer countColSum(ArrayList<ArrayList<Integer>> matrix, int i) {
		Integer result = 0;
		for (int j = 0; j < matrix.size(); j++) {
			Integer value = matrix.get(i).get(j);
			result+=value;
		}
		return result;
	}

}
