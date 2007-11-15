package de.unisiegen.gtitool.core.parser.symbol;


import java.io.Reader;

import java_cup.runtime.lr_parser;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.parser.GTIParser;
import de.unisiegen.gtitool.core.parser.Parseable;
import de.unisiegen.gtitool.core.parser.scanner.GTIScanner;


/**
 * The {@link Symbol} {@link Parseable} class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class SymbolParseable implements Parseable
{

  /**
   * Returns a new {@link GTIParser}.
   * 
   * @param pReader The input {@link Reader}.
   * @return A new {@link GTIParser}.
   * @see Parseable#newParser(Reader)
   */
  public final GTIParser newParser ( Reader pReader )
  {
    return newParser ( newScanner ( pReader ) );
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
    final lr_parser parser = new SymbolParser ( pGTIScanner );
    return new GTIParser ()
    {

      public Symbol parse () throws Exception
      {
        return ( Symbol ) parser.parse ().value;
      }
    };
  }


  /**
   * Returns a new {@link GTIScanner}.
   * 
   * @param pReader The input {@link Reader}.
   * @return A new {@link GTIScanner}.
   * @see Parseable#newScanner(Reader)
   */
  public final GTIScanner newScanner ( Reader pReader )
  {
    if ( pReader == null )
    {
      throw new NullPointerException ( "reader is null" ); //$NON-NLS-1$
    }
    return new SymbolScanner ( pReader );
  }
}
