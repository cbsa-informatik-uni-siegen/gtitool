package de.unisiegen.gtitool.core.parser;


import java.io.StringReader;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.parser.alphabet.AlphabetParseable;


/**
 * The test class of the parser.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings (
{ "all" } )
public class ParserTest
{

  public static void main ( String [] args )
  {
    AlphabetParseable parseable = new AlphabetParseable ();
    String text = "{a,b,c}";
    try
    {
      Alphabet alphabet = ( Alphabet ) parseable.newParser (
          new StringReader ( text.toString () ) ).parse ();
      System.out.println ( alphabet );
    }
    catch ( Exception e )
    {
      e.printStackTrace ();
    }
  }

}
