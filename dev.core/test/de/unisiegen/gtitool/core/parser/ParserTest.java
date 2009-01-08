package de.unisiegen.gtitool.core.parser;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultRegexAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.entities.regex.LeafNode;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.parser.alphabet.AlphabetParseable;
import de.unisiegen.gtitool.core.parser.regex.RegexParseable;
import de.unisiegen.gtitool.core.parser.regexAlphabet.RegexAlphabetParseable;
import de.unisiegen.gtitool.core.parser.symbol.SymbolParseable;
import de.unisiegen.gtitool.core.parser.transition.TransitionParseable;
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
    String alphabetText = "Z,[a-z],[0-9], A, B";
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
    String regexText = "(a|b)*aa#";
    try
    {
      RegexNode regex = ( RegexNode ) regexParseable.newParser ( regexText )
          .parse ();

      DefaultRegex conv = new DefaultRegex ( new DefaultRegexAlphabet (
          new DefaultSymbol ( "a" ), new DefaultSymbol ( "b" ),
          new DefaultSymbol ( "c" ), new DefaultSymbol ( "d" ) ) );
      conv.setRegexNode ( regex, regexText );
      System.out.println ( regex );
      String firstpos = "";
      for ( RegexNode current : regex.firstPos () )
      {
        if ( firstpos.length () > 0 )
        {
          firstpos += ";";
        }

        if ( current instanceof LeafNode )
        {
          firstpos += ( ( LeafNode ) current ).getPosition ();
        }
      }
      System.out.println ( "FirstPos: {" + firstpos + "}" );

      String lastpos = "";
      for ( RegexNode current : regex.lastPos () )
      {
        if ( lastpos.length () > 0 )
        {
          lastpos += ";";
        }
        lastpos += ( ( LeafNode ) current ).getPosition ();
      }
      System.out.println ( "LastPos: {" + lastpos + "}" );

      RegexNode coreSyntax = regex.toCoreSyntax ();
      System.out.println ( coreSyntax.toString () );

      String followPos = "";
      for ( Integer current : conv.followPos ( 1 ) )
      {
        if ( followPos.length () > 0 )
        {
          followPos += ";";
        }
        followPos += current;
      }
      System.out.println ( "FollowPos(1): {" + followPos + "}" );
    }
    catch ( Exception e )
    {
      e.printStackTrace ();
    }

    // Transitions
    try
    {
      String transitionText = "{2,Z, [3-6],A,[a-z]}";
      TransitionParseable tp = new TransitionParseable ();
      DefaultTransition t = (DefaultTransition) tp.newParser ( transitionText ).parse ();
      System.out.println (t.toString ());
    }
    catch ( Exception e )
    {
      e.printStackTrace ();
    }

    // Transitions
    try
    {
      String regexAlphabetString = "{1,[a-z],2 }";
      RegexAlphabetParseable rap = new RegexAlphabetParseable ();
      DefaultRegexAlphabet t = (DefaultRegexAlphabet) rap.newParser ( regexAlphabetString ).parse ();
      System.out.println (t.toString ());
    }
    catch ( Exception e )
    {
      e.printStackTrace ();
    }
  }

}
