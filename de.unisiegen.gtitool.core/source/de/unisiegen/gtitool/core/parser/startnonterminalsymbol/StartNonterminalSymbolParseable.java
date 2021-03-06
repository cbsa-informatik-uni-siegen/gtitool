package de.unisiegen.gtitool.core.parser.startnonterminalsymbol;


import java.io.StringReader;

import java_cup.runtime.lr_parser;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.parser.GTIParser;
import de.unisiegen.gtitool.core.parser.Parseable;
import de.unisiegen.gtitool.core.parser.scanner.GTIScanner;


/**
 * The start {@link NonterminalSymbol} {@link Parseable} class.
 * 
 * @author Christian Fehler
 * @version $Id: StartNonterminalSymbolParseable.java 731 2008-04-04 16:20:30Z
 *          fehler $
 */
public final class StartNonterminalSymbolParseable implements Parseable
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
    final lr_parser parser = new StartNonterminalSymbolParser ( gtiScanner );
    return new GTIParser ()
    {

      public NonterminalSymbol parse () throws Exception
      {
        return ( NonterminalSymbol ) parser.parse ().value;
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
    return new StartNonterminalSymbolScanner ( new StringReader ( text ) );
  }
}
