package de.unisiegen.gtitool.core.exceptions.symbol;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Symbol;


/**
 * The <code>SymbolEmptyNameException</code> is used if the {@link Symbol} is
 * not correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class SymbolEmptyNameException extends SymbolException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6707356857017937577L;


  /**
   * Allocates a new <code>SymbolEmptyNameException</code>.
   */
  public SymbolEmptyNameException ()
  {
    super ();
    // Message and Description
    setMessage ( Messages.getString ( "SymbolException.EmptyNameMessage" ) ); //$NON-NLS-1$
    setDescription ( Messages
        .getString ( "SymbolException.EmptyNameDescription" ) ); //$NON-NLS-1$
  }
}
