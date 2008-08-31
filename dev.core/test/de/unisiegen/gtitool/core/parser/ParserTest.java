package de.unisiegen.gtitool.core.parser;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.entities.regex.ConcatenationNode;
import de.unisiegen.gtitool.core.entities.regex.Converter;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.entities.regex.TokenNode;
import de.unisiegen.gtitool.core.parser.alphabet.AlphabetParseable;
import de.unisiegen.gtitool.core.parser.regex.RegexParseable;
import de.unisiegen.gtitool.core.parser.symbol.SymbolParseable;
import de.unisiegen.gtitool.core.parser.word.WordParseable;


/**
 * The test class of the parser.
 * 
 * @author Christian Fehler
 * @version $Id: ParserTest.java 547 2008-02-10 22:24:57Z fehler $
 */
@SuppressWarnings (
{ "all" } )
public class ParserTest
{

  public static void main ( String [] arguments )
  {
    /*
     * Alphabet
     */
    AlphabetParseable alphabetParseable = new AlphabetParseable ();
    String alphabetText = "{a,b,c}";
    try
    {
      Alphabet alphabet = ( Alphabet ) alphabetParseable.newParser (
          alphabetText ).parse ();
      System.out.println ( alphabet );
    }
    catch ( Exception e )
    {
      e.printStackTrace ();
    }

    /*
     * Symbol
     */
    SymbolParseable symbolParseable = new SymbolParseable ();
    String symbolText = "a";
    try
    {
      Symbol symbol = ( Symbol ) symbolParseable.newParser ( symbolText )
          .parse ();
      System.out.println ( symbol );
    }
    catch ( Exception e )
    {
      e.printStackTrace ();
    }

    /*
     * Word
     */
    WordParseable wordParseable = new WordParseable ();
    String wordText = "abba";
    try
    {
      Word word = ( Word ) wordParseable.newParser ( wordText ).parse ();
      System.out.println ( word );
    }
    catch ( Exception e )
    {
      e.printStackTrace ();
    }

    /*
     * Regex
     */
    RegexParseable regexParseable = new RegexParseable ();
    String regexText = "(a|b)*abb";
    try
    {
      RegexNode regex = ( RegexNode ) regexParseable.newParser ( regexText )
          .parse ();
      regex = new ConcatenationNode(regex, new TokenNode("#"));

      int currentPosition = 1;
      for ( RegexNode current : regex.getTokenNodes () )
      {

        if ( current instanceof TokenNode )
        {
          ( ( TokenNode ) current ).setPosition ( currentPosition );
          currentPosition++ ;
        }
      }
      System.out.println ( regex );
      String firstpos = "";
      for(RegexNode current : regex.firstPos ()) {
        if(firstpos.length ()>0) {
          firstpos+= ";";
        }
        firstpos += ((TokenNode)current).getPosition ();
      }
      System.out.println ( "FirstPos: {" + firstpos + "}");

      String lastpos = "";
      for(RegexNode current : regex.lastPos ()) {
        if(lastpos.length ()>0) {
          lastpos+= ";";
        }
        lastpos += ((TokenNode)current).getPosition ();
      }
      System.out.println ( "LastPos: {" + lastpos + "}");
      
      Converter conv = new Converter(regex);
      for(int i = 1; i < currentPosition ; i++) {
        String followPos = "";
        for(RegexNode current : conv.followPos ( i )) {
          if(followPos.length ()>0) {
            followPos+= ";";
          }
          followPos += ((TokenNode)current).getPosition ();
        }
        System.out.println ("FollowPos(" + i + ") = " + followPos);
      }
    }
    catch ( Exception e )
    {
      e.printStackTrace ();
    }
  }
}
