package eecs285.proj4.Yahtzee;

import java.util.*;

public class Scoreboard {
	
	private String name;
	private int total = 0;
	private int[] points = new int[13];
	private boolean[] available = new boolean[13];
	
	public Scoreboard(String _name)
	{
		name = _name;
		Arrays.fill(available, true);
	}
	
	public void insert_new_score(int index, int value)
	{
		total += value;
		available[index] = false;
		points[index] = value;
	}
	
	public String get_name()
	{
		return name;
	}
	
	public int get_score()
	{
		return total;
	}
	
	public final int[] get_possible_scores(int dice[])
	{
		int[] possible_scores = new int[13];
		
		possible_scores[0] = calculate_upper(dice, available[0], 1);
		possible_scores[1] = calculate_upper(dice, available[1], 2);
		possible_scores[2] = calculate_upper(dice, available[2], 3);
		possible_scores[3] = calculate_upper(dice, available[3], 4);
		possible_scores[4] = calculate_upper(dice, available[4], 5);
		possible_scores[5] = calculate_upper(dice, available[5], 6);
		possible_scores[6] = calculate_ofAKind(dice, available[6], 3);
		possible_scores[7] = calculate_ofAKind(dice, available[7], 4);
		possible_scores[8] = calculate_fh(dice, available[8]);
		possible_scores[9] = calculate_ss(dice, available[9]);
		possible_scores[10] = calculate_ls(dice, available[10]);
		possible_scores[11] = calculate_chance(dice, available[11]);
		possible_scores[12] = calculate_ofAKind(dice, available[12], 5);
		return possible_scores;
	}
	
	public boolean check_bonus(){
		int tot = 0;
		for(int i=0;i<6;i++){
			tot += points[i];
		}
		if(tot< 63) return false;
		
		total+=35;
		return true;
	}
	private int calculate_upper(int dice[], boolean avail, int index)
	{
		if(!avail)
			return -1;
		
		int score = 0;
		
		for(int roll : dice)
		{
			if(roll == index)
				score += index;
		}
		
		return score;
	}
	
	private int calculate_ofAKind(int dice[], boolean avail, int index)
	{
		if(!avail)
			return -1;
		
		int score = 0;
		for(int i = 0; i < 6; i++)
		{
			int count = 0;
			for(int roll : dice)
			{
				if(roll == (i+1))
					count++;
			}
			if(count >= index)
			{
				score = calculate_chance(dice, true);
				break;
			}
		}
		
		if(score != 0 && index == 5)
			return 50;
		return score;
	}
	
	private int calculate_fh(int dice[], boolean avail)
	{
		if(!avail)
			return -1;
		int score = 0;
		boolean toak = false;
		
		int index[] = new int[2];
		index[1] = -1;
		
		for(int i = 0; i < 2; i++)
		{
			for(int j = 0; j < 6; j++)
			{
				index[i] = j;
				if(index[0] == index[1])
					continue;
				int count = 0;
				for(int roll : dice)
				{
					if(roll == (j+1))
						count++;
				}
				if(count == (3-i))
				{
					toak = true;
					if(i == 1)
						score = 25;
					break;
				}
			}
			if(!toak)
				break;
		}
		
		return score;
	}
	
	private int calculate_ss(int dice[], boolean avail)
	{
		if(!avail)
			return -1;
		int score = 0;
		
		Set<Integer> set = new TreeSet<Integer>();
		Set<Integer> check1 = new TreeSet<Integer>();
		Set<Integer> check2 = new TreeSet<Integer>();
		Set<Integer> check3 = new TreeSet<Integer>();
		for(int i = 1; i <=6; i++)
		{
			if(i < 5)
				check1.add(new Integer(i));
			if(i > 2)
				check2.add(new Integer(i));
			if(i > 1 && i < 6)
				check3.add(new Integer(i));
		}
		for(int roll : dice)
			set.add(new Integer(roll));
		
		if(set.equals(check1) || set.equals(check2) 
			|| set.equals(check3) || (calculate_ls(dice, true) > 0))
			score = 30;
		
		return score;
	}
	
	private int calculate_ls(int dice[], boolean avail)
	{
		if(!avail)
			return -1;
		
		int score = 0;
		
		Set<Integer> set = new TreeSet<Integer>();
		for(int roll : dice)
			set.add(new Integer(roll));
		
		if(set.size() == 5)
			score = 40;
		
		if(set.contains(1) && set.contains(6))
			score = 0;
		
		return score;
	}
	
	private int calculate_chance(int dice[], boolean avail)
	{
		if(!avail)
			return -1;
		
		int score = 0;
		
		for(int roll : dice)
			score += roll;
		
		return score;
	}
}






