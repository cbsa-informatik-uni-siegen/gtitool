package de.unisiegen.gtitool.core.parser.scanner;


import java.io.IOException;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.style.Style;


/**
 * The scanner interface.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface GTIScanner extends Scanner
{

  /**
   * Returns the {@link Style} of the given {@link Symbol}.
   * 
   * @param symbol The {@link Symbol}.
   * @return The {@link Style} of the given {@link Symbol}.
   * @see GTIScanner#getStyleBySymbol(Symbol)
   */
  public Style getStyleBySymbol ( Symbol symbol );


  /**
   * Returns the next {@link Symbol}.
   * 
   * @return The next {@link Symbol}.
   * @throws IOException If an io error occurs.
   * @throws ScannerException If an scanner error occurs.
   */
  public Symbol nextSymbol () throws IOException, ScannerException;


  /**
   * Restarts the scanner with the given {@link String}.
   * 
   * @param text The input {@link String}.
   */
  public void restart ( String text );
}
