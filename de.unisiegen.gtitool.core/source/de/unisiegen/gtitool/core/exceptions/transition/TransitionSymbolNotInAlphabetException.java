package de.unisiegen.gtitool.core.exceptions.transition;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link TransitionSymbolNotInAlphabetException} is used, if the
 * {@link Symbol} which should be added to the {@link Transition} is not in the
 * {@link Alphabet} of the {@link Transition}.
 * 
 * @author Christian Fehler
 * @version $Id: TransitionSymbolNotInAlphabetException.java 120 2007-11-10
 *          13:01:38Z fehler $
 */
public final class TransitionSymbolNotInAlphabetException extends
    TransitionException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2829905559325782948L;


  /**
   * The {@link Alphabet}.
   */
  private Alphabet alphabet;


  /**
   * Allocates a new {@link TransitionSymbolNotInAlphabetException}.
   * 
   * @param transition The {@link Transition}.
   * @param alphabet The {@link Alphabet}.
   * @param symbolList The {@link Symbol}s.
   */
  public TransitionSymbolNotInAlphabetException ( Transition transition,
      Alphabet alphabet, ArrayList < Symbol > symbolList )
  {
    super ( transition, symbolList );
    // Alphabet
    if ( alphabet == null )
    {
      throw new NullPointerException ( "alphabet is null" ); //$NON-NLS-1$
    }
    this.alphabet = alphabet;
    // Message and Description
    setMessage ( Messages
        .getString ( "TransitionSymbolNotInAlphabetException.Message" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString (
        "TransitionSymbolNotInAlphabetException.Description", symbolList.get ( //$NON-NLS-1$
            0 ).getName (), alphabet.toString () ) );
  }


  /**
   * Returns the {@link Alphabet}.
   * 
   * @return The {@link Alphabet}.
   * @see #alphabet
   */
  public final Alphabet getAlphabet ()
  {
    return this.alphabet;
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


  /**
   * {@inheritDoc}
   * 
   * @see Throwable#toString()
   */
  @Override
  public final String toString ()
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder result = new StringBuilder ( super.toString () );
    result.append ( lineBreak );
    result.append ( "Alphabet:    " ); //$NON-NLS-1$
    result.append ( this.alphabet );
    return result.toString ();
  }
}
