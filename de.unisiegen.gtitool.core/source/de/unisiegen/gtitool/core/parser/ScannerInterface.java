package de.unisiegen.gtitool.core.parser;


import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Scanner;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;


/**
 * The scanner interface.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface ScannerInterface extends Scanner
{

  /**
   * Returns the {@link PrettyStyle} of the given {@link ParserSymbol}.
   * 
   * @param pParserSymbol The {@link ParserSymbol}.
   * @return The {@link PrettyStyle} of the given {@link ParserSymbol}.
   * @see ScannerInterface#getStyleBySymbol(ParserSymbol)
   */
  public PrettyStyle getStyleBySymbol ( ParserSymbol pParserSymbol );


  /**
   * Returns the next {@link ParserSymbol}.
   * 
   * @return The next {@link ParserSymbol}.
   * @throws IOException If an io error occurs.
   * @throws ScannerException If an scanner error occurs.
   */
  public ParserSymbol nextSymbol () throws IOException, ScannerException;


  /**
   * Restarts the scanner with the given reader.
   * 
   * @param pReader The input reader.
   */
  public void restart ( Reader pReader );
}
