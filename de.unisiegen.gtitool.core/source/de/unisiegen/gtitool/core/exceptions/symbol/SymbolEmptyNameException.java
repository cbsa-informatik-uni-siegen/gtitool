package de.unisiegen.gtitool.core.exceptions.symbol;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link SymbolEmptyNameException} is used if the {@link Symbol} is not
 * correct.
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
   * Allocates a new {@link SymbolEmptyNameException}.
   */
  public SymbolEmptyNameException ()
  {
    super ();
    // Message and Description
    setMessage ( Messages.getString ( "SymbolException.EmptyNameMessage" ) ); //$NON-NLS-1$
    setDescription ( Messages
        .getPrettyString ( "SymbolException.EmptyNameDescription" ) ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see CoreException#getType()
   */
  @Override
  public final ErrorType getType ()
  {
    return ErrorType.ERROR;
  }
}
