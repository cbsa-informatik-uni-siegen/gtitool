package de.unisiegen.gtitool.core.parser.state;


import java.io.StringReader;

import java_cup.runtime.lr_parser;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.parser.GTIParser;
import de.unisiegen.gtitool.core.parser.Parseable;
import de.unisiegen.gtitool.core.parser.scanner.GTIScanner;


/**
 * The {@link State} {@link Parseable} class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StateParseable implements Parseable
{

  /**
   * Returns a new {@link GTIParser}.
   * 
   * @param gtiScanner The input {@link GTIScanner}.
   * @return A new {@link GTIParser}.
   * @see Parseable#newParser(GTIScanner)
   */
  public final GTIParser newParser ( GTIScanner gtiScanner )
  {
    if ( gtiScanner == null )
    {
      throw new NullPointerException ( "scanner is null" ); //$NON-NLS-1$
    }
    final lr_parser parser = new StateParser ( gtiScanner );
    return new GTIParser ()
    {

      public State parse () throws Exception
      {
        return ( State ) parser.parse ().value;
      }
    };
  }


  /**
   * Returns a new {@link GTIParser}.
   * 
   * @param text The input {@link String}.
   * @return A new {@link GTIParser}.
   * @see Parseable#newParser(String)
   */
  public final GTIParser newParser ( String text )
  {
    return newParser ( newScanner ( text ) );
  }


  /**
   * Returns a new {@link GTIScanner}.
   * 
   * @param text The input {@link String}.
   * @return A new {@link GTIScanner}.
   * @see Parseable#newScanner(String)
   */
  public final GTIScanner newScanner ( String text )
  {
    if ( text == null )
    {
      throw new NullPointerException ( "text is null" ); //$NON-NLS-1$
    }
    return new StateScanner ( new StringReader ( text ) );
  }
}
