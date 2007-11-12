package de.unisiegen.gtitool.core.parser;


import java.io.Reader;


/**
 * The interface of all parseable objects.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Parseable
{

  /**
   * Returns a new {@link ParserInterface}.
   * 
   * @param pReader The input {@link Reader}.
   * @return A new {@link ParserInterface}.
   * @see Parseable#newParser(Reader)
   */
  public ParserInterface newParser ( Reader pReader );



  /**
   * Returns a new {@link ParserInterface}.
   * 
   * @param pScanner The input {@link ScannerInterface}.
   * @return A new {@link ParserInterface}.
   * @see Parseable#newParser(ScannerInterface)
   */
  public ParserInterface newParser ( ScannerInterface pScanner );


  /**
   * Returns a new {@link ScannerInterface}.
   * 
   * @param pReader The input {@link Reader}.
   * @return A new {@link ScannerInterface}.
   * @see Parseable#newScanner(Reader)
   */
  public ScannerInterface newScanner ( Reader pReader );
}
