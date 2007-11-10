package de.unisiegen.gtitool.core.exceptions.symbol;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>SymbolException</code> is used if the {@link Symbol} is not
 * correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class SymbolException extends CoreException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8434656882158475737L;


  /**
   * Allocates a new <code>SymbolException</code>.
   */
  public SymbolException ()
  {
    super ();
    // Message and Description
    setMessage ( Messages.getString ( "SymbolException.EmptyNameMessage" ) ); //$NON-NLS-1$
    setDescription ( Messages
        .getString ( "SymbolException.EmptyNameDescription" ) ); //$NON-NLS-1$
  }
}
