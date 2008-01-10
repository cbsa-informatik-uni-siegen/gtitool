package de.unisiegen.gtitool.core.parser;


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
   * @param pText The input {@link String}.
   * @return A new {@link GTIParser}.
   * @see Parseable#newParser(String)
   */
  public GTIParser newParser ( String pText );


  /**
   * Returns a new {@link GTIParser}.
   * 
   * @param gtiScanner The input {@link GTIScanner}.
   * @return A new {@link GTIParser}.
   * @see Parseable#newParser(GTIScanner)
   */
  public GTIParser newParser ( GTIScanner gtiScanner );


  /**
   * Returns a new {@link GTIScanner}.
   * 
   * @param text The input {@link String}.
   * @return A new {@link GTIScanner}.
   * @see Parseable#newScanner(String)
   */
  public GTIScanner newScanner ( String text );
}
