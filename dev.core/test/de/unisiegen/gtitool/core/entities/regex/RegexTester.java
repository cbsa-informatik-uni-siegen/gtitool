package de.unisiegen.gtitool.core.entities.regex;


import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.parser.regex.RegexParseable;
import de.unisiegen.gtitool.core.regex.DefaultRegex;


/**
 * TODO
 */
public class RegexTester
{

  /**
   * TODO
   * 
   * @param args
   */
  public static void main ( String [] args )
  {
    RegexParseable regexParseable = new RegexParseable ();
    String regexText1 = "aa|b";
    String regexText2 = "aa|b";
    RegexNode regex1;
    RegexNode regex2;
    try
    {
      regex1 = ( RegexNode ) regexParseable.newParser ( regexText1 ).parse ();
      DefaultRegex conv1 = new DefaultRegex ( new DefaultAlphabet (
          new DefaultSymbol ( "a" ), new DefaultSymbol ( "b" ),
          new DefaultSymbol ( "c" ), new DefaultSymbol ( "d" ) ), regexText1 );
      conv1.setRegexNode ( regex1, false );
      regex2 = ( RegexNode ) regexParseable.newParser ( regexText2 ).parse ();
      DefaultRegex conv2 = new DefaultRegex ( new DefaultAlphabet (
          new DefaultSymbol ( "a" ), new DefaultSymbol ( "b" ),
          new DefaultSymbol ( "c" ), new DefaultSymbol ( "d" ) ), regexText2 );
      conv2.setRegexNode ( regex2, false );
      System.err.println (conv1.equals ( conv2 ));
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
    }

  }

}
