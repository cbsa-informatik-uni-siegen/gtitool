package de.unisiegen.gtitool.core.parser.regexAlphabet;


import java.io.StringReader;

import java_cup.runtime.lr_parser;
import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultRegexAlphabet;
import de.unisiegen.gtitool.core.parser.GTIParser;
import de.unisiegen.gtitool.core.parser.Parseable;
import de.unisiegen.gtitool.core.parser.scanner.GTIScanner;


/**
 * The {@link Alphabet} {@link Parseable} class.
 * 
 * @author Christian Fehler
 * @version $Id: RegexAlphabetParseable.java 1043 2008-06-27 00:09:58Z fehler $
 */
public final class RegexAlphabetParseable implements Parseable
{

  /**
   * Returns a new {@link GTIParser}.
   * 
   * @param gtiScanner The input {@link GTIScanner}.
   * @return A new {@link GTIParser}.
   * @see Parseable#newParser(GTIScanner)
   */
  public final GTIParser newParser ( GTIScanner gtiScanner )
  {
    if ( gtiScanner == null )
    {
      throw new NullPointerException ( "scanner is null" ); //$NON-NLS-1$
    }
    final lr_parser parser = new RegexAlphabetParser ( gtiScanner );
    return new GTIParser ()
    {

      public DefaultRegexAlphabet parse () throws Exception
      {
        return ( DefaultRegexAlphabet ) parser.parse ().value;
      }
    };
  }


  /**
   * Returns a new {@link GTIParser}.
   * 
   * @param text The input {@link String}.
   * @return A new {@link GTIParser}.
   * @see Parseable#newParser(String)
   */
  public final GTIParser newParser ( String text )
  {
    return newParser ( newScanner ( text ) );
  }


  /**
   * Returns a new {@link GTIScanner}.
   * 
   * @param text The input {@link String}.
   * @return A new {@link GTIScanner}.
   * @see Parseable#newScanner(String)
   */
  public final GTIScanner newScanner ( String text )
  {
    if ( text == null )
    {
      throw new NullPointerException ( "text is null" ); //$NON-NLS-1$
    }
    return new RegexAlphabetScanner ( new StringReader ( text ) );
  }
}
