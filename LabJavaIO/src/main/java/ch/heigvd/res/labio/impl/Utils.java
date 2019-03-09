package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {

    ArrayList<String> listString = new ArrayList<String>();
    StringBuilder sb = new StringBuilder();

    for(int i = 0; i < lines.length(); ++i){
      String charString = Character.toString(lines.charAt(i));
      sb.append(charString);

      if(charString.contains("\n")){
        listString.add(sb.toString());
        sb.setLength(0); //Clear the String builder
      } else if(charString.contains("\r")){
          if(i + 1 < lines.length() && Character.toString(lines.charAt(i + 1)).contains("\n")) { //Possibility to have a \n after the \r
            sb.append("\n");
            ++i;
          }
          listString.add(sb.toString());
          sb.setLength(0); //Clear the String builder
      } else if(i + 1 == lines.length()){
        listString.add(sb.toString());
      }
    }

    if(!Pattern.matches(".*(?:[\n\r].*)+", lines)){
      listString.add(0,"");
    }else if(listString.size() < 2){
      listString.add("");
    }
    //Convert the list to an String array.
    String[] stringArray = new String[listString.size()];
    listString.toArray(stringArray);
    return stringArray;

  }

}
