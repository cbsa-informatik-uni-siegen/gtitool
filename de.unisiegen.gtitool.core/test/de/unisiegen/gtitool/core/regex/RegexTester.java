package de.unisiegen.gtitool.core.regex;


import de.unisiegen.gtitool.core.entities.DefaultRegexAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.parser.regex.RegexParseable;
import de.unisiegen.gtitool.core.regex.DefaultRegex;


/**
 * Tester class for Regex
 */
public class RegexTester
{

  /**
   * Main class for tester
   * 
   * @param args
   */
  public static void main ( String [] args )
  {
    RegexParseable regexParseable = new RegexParseable ();
    String regexText1 = "ac|b*"; //$NON-NLS-1$
    String regexText2 = "(ac)|b*";//$NON-NLS-1$
    RegexNode regex1;
    RegexNode regex2;
    try
    {
      regex1 = ( RegexNode ) regexParseable.newParser ( regexText1 ).parse ();
      DefaultRegex conv1 = new DefaultRegex ( new DefaultRegexAlphabet (
          new DefaultSymbol ( "a" ), new DefaultSymbol ( "b" ),//$NON-NLS-1$ //$NON-NLS-2$
          new DefaultSymbol ( "c" ), new DefaultSymbol ( "d" ) ) );//$NON-NLS-1$ //$NON-NLS-2$
      conv1.setRegexNode ( regex1, regexText1 );
      regex2 = ( RegexNode ) regexParseable.newParser ( regexText2 ).parse ();
      DefaultRegex conv2 = new DefaultRegex ( new DefaultRegexAlphabet (
          new DefaultSymbol ( "a" ), new DefaultSymbol ( "b" ),//$NON-NLS-1$ //$NON-NLS-2$
          new DefaultSymbol ( "c" ), new DefaultSymbol ( "d" ) ) );//$NON-NLS-1$ //$NON-NLS-2$
      conv2.setRegexNode ( regex2, regexText2 );
      System.err.println ( conv1.equals ( conv2 ) );
      while ( !regex1.isMarked () )
      {
        System.err.println ( regex1.getNextNodeForNFA () );
      }
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
    }

    try
    {
      RegexNode regex = ( RegexNode ) regexParseable.newParser (
          "[abcde]" ).parse (); //$NON-NLS-1$
      
      System.err.println ( regex.toPrettyString ().toString () );
    }
    catch ( Exception e )
    {
      e.printStackTrace ();
    }
    
  }
  
  
}
