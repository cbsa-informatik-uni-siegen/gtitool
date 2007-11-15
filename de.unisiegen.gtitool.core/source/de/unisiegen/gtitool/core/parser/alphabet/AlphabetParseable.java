package de.unisiegen.gtitool.core.parser.alphabet;


import java.io.StringReader;

import java_cup.runtime.lr_parser;
import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.parser.GTIParser;
import de.unisiegen.gtitool.core.parser.Parseable;
import de.unisiegen.gtitool.core.parser.scanner.GTIScanner;


/**
 * The {@link Alphabet} {@link Parseable} class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class AlphabetParseable implements Parseable
{

  /**
   * Returns a new {@link GTIParser}.
   * 
   * @param pText The input {@link String}.
   * @return A new {@link GTIParser}.
   * @see Parseable#newParser(String)
   */
  public final GTIParser newParser ( String pText )
  {
    return newParser ( newScanner ( pText ) );
  }


  /**
   * Returns a new {@link GTIParser}.
   * 
   * @param pGTIScanner The input {@link GTIScanner}.
   * @return A new {@link GTIParser}.
   * @see Parseable#newParser(GTIScanner)
   */
  public final GTIParser newParser ( GTIScanner pGTIScanner )
  {
    if ( pGTIScanner == null )
    {
      throw new NullPointerException ( "scanner is null" ); //$NON-NLS-1$
    }
    final lr_parser parser = new AlphabetParser ( pGTIScanner );
    return new GTIParser ()
    {

      public Alphabet parse () throws Exception
      {
        return ( Alphabet ) parser.parse ().value;
      }
    };
  }


  /**
   * Returns a new {@link GTIScanner}.
   * 
   * @param pText The input {@link String}.
   * @return A new {@link GTIScanner}.
   * @see Parseable#newScanner(String)
   */
  public final GTIScanner newScanner ( String pText )
  {
    if ( pText == null )
    {
      throw new NullPointerException ( "text is null" ); //$NON-NLS-1$
    }
    return new AlphabetScanner ( new StringReader ( pText ) );
  }
}
