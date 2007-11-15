package de.unisiegen.gtitool.core.parser;


import java.io.Reader;

import de.unisiegen.gtitool.core.parser.scanner.GTIScanner;


/**
 * The interface of all parseable objects.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Parseable
{

  /**
   * Returns a new {@link GTIParser}.
   * 
   * @param pReader The input {@link Reader}.
   * @return A new {@link GTIParser}.
   * @see Parseable#newParser(Reader)
   */
  public GTIParser newParser ( Reader pReader );


  /**
   * Returns a new {@link GTIParser}.
   * 
   * @param pGTIScanner The input {@link GTIScanner}.
   * @return A new {@link GTIParser}.
   * @see Parseable#newParser(GTIScanner)
   */
  public GTIParser newParser ( GTIScanner pGTIScanner );


  /**
   * Returns a new {@link GTIScanner}.
   * 
   * @param pReader The input {@link Reader}.
   * @return A new {@link GTIScanner}.
   * @see Parseable#newScanner(Reader)
   */
  public GTIScanner newScanner ( Reader pReader );
}
