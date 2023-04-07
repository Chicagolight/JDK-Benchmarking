package flow_control;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static flow_control.StringConstants.*;

public class StringSwitch
{
   public static Boolean evaluateSwitch(String input)
   {
      switch(input)
      {
         case HVOW:
         case PJWW:
         case FHEX:
         case GSZY:
         case XTOR:
            return true;
         case HVOX:
         case PJWX:
         case XHEX:
         case XSZY:
         case XTOX:
            return false;
         default:
            return null;
      }
   }

   public static <T> boolean anyList(final T... elems)
   {
      T first = elems[0];
      for (int i = 1; i < elems.length; i++) {
         if (first.equals(elems[i])) {
            // if the first entry is equal to any other entry, return true
            return true;
         }
      }

      // if the loop completes without finding a match, return false
      return false;
   }
}
