package de.unisiegen.gtitool.core.parser.alphabet;


import java.io.Reader;

import java_cup.runtime.lr_parser;
import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.parser.Parseable;
import de.unisiegen.gtitool.core.parser.ParserInterface;
import de.unisiegen.gtitool.core.parser.ScannerInterface;


/**
 * The {@link Alphabet} {@link Parseable} class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class AlphabetParseable implements Parseable
{

  /**
   * Allocates a new <code>AlphabetParseable</code>.
   */
  public AlphabetParseable ()
  {
    // Do nothing
  }


  /**
   * Returns a new {@link ParserInterface}.
   * 
   * @param pReader The input {@link Reader}.
   * @return A new {@link ParserInterface}.
   * @see Parseable#newParser(Reader)
   */
  public final ParserInterface newParser ( Reader pReader )
  {
    return newParser ( newScanner ( pReader ) );
  }


  /**
   * Returns a new {@link ParserInterface}.
   * 
   * @param pScanner The input {@link ScannerInterface}.
   * @return A new {@link ParserInterface}.
   * @see Parseable#newParser(ScannerInterface)
   */
  public final ParserInterface newParser ( ScannerInterface pScanner )
  {
    if ( pScanner == null )
    {
      throw new NullPointerException ( "scanner is null" ); //$NON-NLS-1$
    }
    final lr_parser parser = new AlphabetParser ( pScanner );
    return new ParserInterface ()
    {

      public Alphabet parse () throws Exception
      {
        return ( Alphabet ) parser.parse ().value;
      }
    };
  }


  /**
   * Returns a new {@link ScannerInterface}.
   * 
   * @param pReader The input {@link Reader}.
   * @return A new {@link ScannerInterface}.
   * @see Parseable#newScanner(Reader)
   */
  public final ScannerInterface newScanner ( Reader pReader )
  {
    if ( pReader == null )
    {
      throw new NullPointerException ( "reader is null" ); //$NON-NLS-1$
    }
    return new AlphabetScanner ( pReader );
  }
}
