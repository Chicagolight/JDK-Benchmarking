package flow_control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StringConstants
{
   public static final String HVOW = "HVOW";
   public static final String PJWW = "PJWW";
   public static final String FHEX = "FHEX";
   public static final String GSZY = "GSZY";
   public static final String XTOR = "XTOR";

   public static final String HVOX = "HVOX";
   public static final String PJWX = "PJWX";
   public static final String XHEX = "XHEX";
   public static final String XSZY = "XSZY";
   public static final String XTOX = "XTOX";

   public static final String[] WORD_LIST = {
         HVOW, PJWW, FHEX, GSZY, XTOR, HVOX, PJWX, XHEX, XSZY, XTOX
   };

   public static List<String> getRandomWords(int numWords) {
      Random random = new Random();
      List<String> randomWords = new ArrayList<>();
      for (int i = 0; i < numWords; i++) {
         randomWords.add(WORD_LIST[random.nextInt(WORD_LIST.length)]);
      }
      return randomWords;
   }

   public static String concatStringConstants() {
      StringBuilder builder = new StringBuilder();
      for (String word : WORD_LIST) {
         builder.append(word).append('|');
      }
      builder.deleteCharAt(builder.length() - 1); // remove last pipe character
      return builder.toString();
   }
}
