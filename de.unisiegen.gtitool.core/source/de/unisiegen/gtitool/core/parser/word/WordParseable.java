package de.unisiegen.gtitool.core.parser.word;


import java.io.StringReader;

import java_cup.runtime.lr_parser;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.parser.GTIParser;
import de.unisiegen.gtitool.core.parser.Parseable;
import de.unisiegen.gtitool.core.parser.scanner.GTIScanner;


/**
 * The {@link Word} {@link Parseable} class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class WordParseable implements Parseable
{

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
    final lr_parser parser = new WordParser ( gtiScanner );
    return new GTIParser ()
    {

      public Word parse () throws Exception
      {
        return ( Word ) parser.parse ().value;
      }
    };
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
    return new WordScanner ( new StringReader ( text ) );
  }
}
