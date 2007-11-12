package de.unisiegen.gtitool.core.parser;


import java.io.IOException;

import java_cup.runtime.Scanner;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;


/**
 * The abstract scanner class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class AbstractScanner implements ScannerInterface
{

  /**
   * Allocates a new <code>AbstractScanner</code>.
   */
  public AbstractScanner ()
  {
    super ();
  }


  /**
   * Returns the {@link PrettyStyle} of the given {@link ParserSymbol}.
   * 
   * @param pParserSymbol The {@link ParserSymbol}.
   * @return The {@link PrettyStyle} of the given {@link ParserSymbol}.
   * @see ScannerInterface#getStyleBySymbol(ParserSymbol)
   */
  public final PrettyStyle getStyleBySymbol ( ParserSymbol pParserSymbol )
  {
    return getStyleBySymbolId ( pParserSymbol.getId () );
  }


  /**
   * Returns the {@link PrettyStyle} of the given symbol id.
   * 
   * @param pSymbolId The symbol id.
   * @return The {@link PrettyStyle} of the given symbol id.
   */
  public abstract PrettyStyle getStyleBySymbolId ( int pSymbolId );


  /**
   * Returns the next {@link ParserSymbol}.
   * 
   * @return The next {@link ParserSymbol}.
   * @throws IOException
   * @throws ScannerException
   * @see Scanner#next_token()
   */
  public final ParserSymbol next_token () throws IOException, ScannerException
  {
    return nextSymbol ();
  }


  /**
   * Returns the {@link ParserSymbol} with the given parameters.
   * 
   * @param pName The name of the {@link ParserSymbol}.
   * @param pId The id of the {@link ParserSymbol}.
   * @param pLeft The left index of the {@link ParserSymbol}.
   * @param pRight The right index of the {@link ParserSymbol}.
   * @param pValue The value of the {@link ParserSymbol}.
   * @return The {@link ParserSymbol} with the given parameters.
   */
  public final ParserSymbol symbol ( String pName, int pId, int pLeft,
      int pRight, Object pValue )
  {
    return new ParserSymbol ( pName, pId, pLeft, pRight, pValue );
  }
}
