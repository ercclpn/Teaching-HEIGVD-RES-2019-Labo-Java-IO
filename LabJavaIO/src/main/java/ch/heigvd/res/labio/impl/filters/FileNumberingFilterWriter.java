package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;


/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private int nbLine = 1;
  private Boolean detectR = false;
  private Boolean firstCall = true;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    write(str.toCharArray(),off,len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {

    for(int i = off; i < off + len ; ++i){
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {

    if(firstCall){
      String lineValue = nbLine++ + "\t";
      super.write(lineValue,0,lineValue.length());
      firstCall = false;
    }

    String cString = Character.toString((char)c);

    if(cString.contentEquals("\r")){
      detectR = true;
    }else if(cString.contentEquals("\n")){
      cString = "\n" + nbLine++ + "\t";
      detectR = false;
    }else if(detectR){
      detectR = false;
      cString = nbLine++ + "\t" + cString;
    }

    super.write(cString,0,cString.length());
  }
}
