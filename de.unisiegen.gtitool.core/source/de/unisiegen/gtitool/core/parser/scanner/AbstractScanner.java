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
 * @version $Id$
 */
public abstract class AbstractScanner implements GTIScanner
{

  /**
   * Returns the {@link Style} of the given {@link Symbol}.
   * 
   * @param pSymbol The {@link Symbol}.
   * @return The {@link Style} of the given {@link Symbol}.
   * @see GTIScanner#getStyleBySymbol(Symbol)
   */
  public final Style getStyleBySymbol ( Symbol pSymbol )
  {
    return getStyleBySymbolId ( pSymbol.sym );
  }


  /**
   * Returns the {@link Style} of the given symbol id.
   * 
   * @param pSymbolId The symbol id.
   * @return The {@link Style} of the given symbol id.
   */
  public abstract Style getStyleBySymbolId ( int pSymbolId );


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
    return nextSymbol ();
  }


  /**
   * Returns the {@link Symbol} with the given parameters.
   * 
   * @param pId The id of the {@link Symbol}.
   * @param pLeft The left index of the {@link Symbol}.
   * @param pRight The right index of the {@link Symbol}.
   * @param pValue The value of the {@link Symbol}.
   * @return The {@link Symbol} with the given parameters.
   */
  public final Symbol symbol ( int pId, int pLeft, int pRight, Object pValue )
  {
    return new Symbol ( pId, pLeft, pRight, pValue );
  }
}