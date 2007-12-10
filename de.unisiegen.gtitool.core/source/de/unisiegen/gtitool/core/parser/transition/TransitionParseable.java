package de.unisiegen.gtitool.core.parser.transition;


import java.io.StringReader;

import java_cup.runtime.lr_parser;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.parser.GTIParser;
import de.unisiegen.gtitool.core.parser.Parseable;
import de.unisiegen.gtitool.core.parser.scanner.GTIScanner;


/**
 * The {@link Transition} {@link Parseable} class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class TransitionParseable implements Parseable
{

  /**
   * Returns a new {@link GTIParser}.
   * 
   * @param pText The input {@link String}.
   * @return A new {@link GTIParser}.
   * @see Parseable#newParser(String)
   */
  public final GTIParser newParser ( String pText )
  {
    return newParser ( newScanner ( pText ) );
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
    final lr_parser parser = new TransitionParser ( pGTIScanner );
    return new GTIParser ()
    {

      public Transition parse () throws Exception
      {
        return ( Transition ) parser.parse ().value;
      }
    };
  }


  /**
   * Returns a new {@link GTIScanner}.
   * 
   * @param pText The input {@link String}.
   * @return A new {@link GTIScanner}.
   * @see Parseable#newScanner(String)
   */
  public final GTIScanner newScanner ( String pText )
  {
    if ( pText == null )
    {
      throw new NullPointerException ( "text is null" ); //$NON-NLS-1$
    }
    return new TransitionScanner ( new StringReader ( pText ) );
  }
}
