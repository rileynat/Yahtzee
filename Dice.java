package eecs285.proj4.Yahtzee;

import java.util.Random;

/*
   A class to keep track of the dice
   values and whether or not the dice
   should be rolled.
*/
public class Dice
{
   final private int NUMDICE = 5;
   private int[] die_values;
   private Random randomizer;
   private boolean[] die_locked;

   public Dice(int seed)
   {
      randomizer = new Random(seed);
      die_locked = new boolean[NUMDICE];
      die_values = new int[NUMDICE];
      for (int i = 0; i < NUMDICE; i++) {
         die_locked[i] = false;
         die_values[i] = 6;
      }
   }
   //Updating the values.
   void roll()
   {
      for (int i = 0; i < NUMDICE; i++) {
         if (die_locked[i] == false) {
            die_values[i] = randomizer.nextInt(6) + 1;

         }
      }
   }
   //Lock or unlocking the die.
   void toggle_lock_die(final int die)
   {
      if (die >= 0 && die <= NUMDICE - 1) {
         die_locked[die] = !die_locked[die];
      }
   }
   //Returning the values.
   final int[] get_dice_values()
   {
      return die_values;
   }
   //Check if the die is locked or not
   boolean is_die_locked(final int die_index)
   {
      if (die_index >= 0 && die_index <= NUMDICE - 1) {
         return die_locked[die_index];
      }
      return true;
   }
}
