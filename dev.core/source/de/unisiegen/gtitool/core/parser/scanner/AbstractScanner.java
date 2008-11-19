package de.unisiegen.gtitool.core.parser.scanner;


import java.io.IOException;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.style.Style;


/**
 * The abstract scanner class.
 * 
 * @author Christian Fehler
 * @version $Id: AbstractScanner.java 413 2008-01-10 00:54:53Z fehler $
 */
public abstract class AbstractScanner implements GTIScanner
{

  /**
   * Returns the {@link Style} of the given {@link Symbol}.
   * 
   * @param symbol The {@link Symbol}.
   * @return The {@link Style} of the given {@link Symbol}.
   * @see GTIScanner#getStyleBySymbol(Symbol)
   */
  public final Style getStyleBySymbol ( Symbol symbol )
  {
    return getStyleBySymbolId ( symbol.sym );
  }


  /**
   * Returns the {@link Style} of the given symbol id.
   * 
   * @param symbolId The symbol id.
   * @return The {@link Style} of the given symbol id.
   */
  public abstract Style getStyleBySymbolId ( int symbolId );


  /**
   * Returns the next {@link Symbol}.
   * 
   * @return The next {@link Symbol}.
   * @throws IOException
   * @throws ScannerException
   * @see Scanner#next_token()
   */
  public final Symbol next_token () throws IOException, ScannerException
  {
    for ( ; ; )
    {
      // return the next symbol, skipping comments
      Symbol symbol = nextSymbol ();
      if ( symbol != null && getStyleBySymbol ( symbol ) == Style.COMMENT )
      {
        continue;
      }
      return symbol;
    }
  }


  /**
   * Returns the {@link Symbol} with the given parameters.
   * 
   * @param id The id of the {@link Symbol}.
   * @param left The left index of the {@link Symbol}.
   * @param right The right index of the {@link Symbol}.
   * @param value The value of the {@link Symbol}.
   * @return The {@link Symbol} with the given parameters.
   */
  public final Symbol symbol ( int id, int left, int right, Object value )
  {
    return new Symbol ( id, left, right, value );
  }
}
