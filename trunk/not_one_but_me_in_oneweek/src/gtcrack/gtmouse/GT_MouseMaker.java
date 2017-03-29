package gtcrack.gtmouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GT_MouseMaker {

	public static Map<Integer, List<int[]>> getMouseMove(final int move) {
		final Map<Integer, List<int[]>> map = new HashMap<>();

		final List<int[]> list = new ArrayList<>();
		final int[] start = new int[3];
		start[0] = 10;
		start[1] = 20;
		start[2] = 0;
		list.add(start);

		int total = move;
		int passTime = 0;

		while (true) {
			final int[] ii = new int[3];
			if (total < 10) {
				ii[0] = total;
				ii[1] = 0;
				ii[2] = (int) (Math.random() * 10) + 5;
				passTime += ii[2];
				list.add(ii);
				break;
			}

			ii[0] = (int) (Math.random() * 4) + 1;
			total -= ii[0];
			ii[1] = 0;
			ii[2] = (int) (Math.random() * 10) + 5;
			passTime += ii[2];
			list.add(ii);
		}

		final int[] end = new int[3];
		end[0] = 0;
		end[1] = 0;
		end[2] = (int) (Math.random() * 300) + 81;
		passTime += end[2];
		list.add(end);

		map.put(passTime, list);
		return map;

	}

	public static void main(final String[] args) {
	}

}
/*
 * public Map<Integer, List<List<Integer>>> getMouse(final int move) { final
 * Map<Integer, List<List<Integer>>> map = new HashMap<>();
 * 
 * final List<List<Integer>> list = new ArrayList<>(); final List<Integer> start
 * = new ArrayList<>(); start.add(10); start.add(20); start.add(0);
 * list.add(start);
 * 
 * int total = move; int passTime = 0;
 * 
 * while (true) { final List<Integer> ii = new ArrayList<>(); if (total < 10) {
 * ii.add(total); ii.add(0); final int time = (int) (Math.random() * 10) + 5;
 * ii.add(time); passTime += time; list.add(ii); break; }
 * 
 * final int i = (int) (Math.random() * 4) + 1; total -= i; ii.add(i); passTime
 * += i; ii.add(0); final int time = (int) (Math.random() * 10) + 5; passTime +=
 * time; ii.add(time); list.add(ii); }
 * 
 * final List<Integer> end = new ArrayList<>(); end.add(0); end.add(0); final
 * int time = (int) (Math.random() * 10) + 5; passTime += time; end.add(time);
 * list.add(end);
 * 
 * map.put(passTime, list); return map;
 * 
 * }
 */
