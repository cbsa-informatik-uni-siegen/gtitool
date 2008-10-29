package de.unisiegen.gtitool.core.parser;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.entities.regex.ConcatenationNode;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.entities.regex.TokenNode;
import de.unisiegen.gtitool.core.parser.alphabet.AlphabetParseable;
import de.unisiegen.gtitool.core.parser.regex.RegexParseable;
import de.unisiegen.gtitool.core.parser.symbol.SymbolParseable;
import de.unisiegen.gtitool.core.parser.word.WordParseable;
import de.unisiegen.gtitool.core.regex.DefaultRegex;


/**
 * The test class of the parser.
 * 
 * @author Christian Fehler
 * @author Simon Meurer
 * @version $Id$
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
    String regexText = "ab";
    try
    {
      RegexNode regex = ( RegexNode ) regexParseable.newParser ( regexText )
          .parse ();
      regex = new ConcatenationNode ( regex, new TokenNode ( "#" ) );

      int currentPosition = 1;
      /**for ( RegexNode current : regex.getTokenNodes () )
      {

        if ( current instanceof TokenNode )
        {
          ( ( TokenNode ) current ).setPosition ( currentPosition );
          currentPosition++ ;
        }
      }*/
      System.out.println ( regex );
      String firstpos = "";
      for ( RegexNode current : regex.firstPos () )
      {
        if ( firstpos.length () > 0 )
        {
          firstpos += ";";
        }
        firstpos += ( ( TokenNode ) current ).getPosition ();
      }
      System.out.println ( "FirstPos: {" + firstpos + "}" );

      String lastpos = "";
      for ( RegexNode current : regex.lastPos () )
      {
        if ( lastpos.length () > 0 )
        {
          lastpos += ";";
        }
        lastpos += ( ( TokenNode ) current ).getPosition ();
      }
      System.out.println ( "LastPos: {" + lastpos + "}" );

      DefaultRegex conv = new DefaultRegex (new DefaultAlphabet(new DefaultSymbol("a"),new DefaultSymbol("b"),new DefaultSymbol("c"),new DefaultSymbol("d")), regexText);
      conv.setRegexNode ( regex );
      for ( int i = 1 ; i < currentPosition ; i++ )
      {
        String followPos = "";
        for ( RegexNode current : conv.followPos ( i ) )
        {
          if ( followPos.length () > 0 )
          {
            followPos += ";";
          }
          followPos += ( ( TokenNode ) current ).getPosition ();
        }
        System.out.println ( "FollowPos(" + i + ") = " + followPos );
      }

      RegexNode coreSyntax = regex.toCoreSyntax ();
      System.out.println ( coreSyntax.toString () );
    }
    catch ( Exception e )
    {
      e.printStackTrace ();
    }
    try{
      RegexNode regex = ( RegexNode ) regexParseable.newParser ( "[a-z]" )
          .parse ();
      System.err.println (regex);
      System.err.println (regex.toCoreSyntax ());
    } catch (Exception e){
      e.printStackTrace ();
    }
  }
}
