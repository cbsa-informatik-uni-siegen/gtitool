package de.unisiegen.gtitool.core.exceptions.transition;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>TransitionSymbolNotInAlphabetException</code> is used, if the
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
   * Allocates a new <code>TransitionSymbolNotInAlphabetException</code>.
   * 
   * @param pTransition The {@link Transition}.
   * @param pAlphabet The {@link Alphabet}.
   * @param pSymbol The {@link Symbol}.
   */
  public TransitionSymbolNotInAlphabetException ( Transition pTransition,
      Alphabet pAlphabet, Symbol pSymbol )
  {
    super ( pTransition, pSymbol );
    // Alphabet
    if ( pAlphabet == null )
    {
      throw new NullPointerException ( "alphabet is null" ); //$NON-NLS-1$
    }
    this.alphabet = pAlphabet;
    // Message and Description
    setMessage ( Messages
        .getString ( "TransitionSymbolNotInAlphabetException.Message" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString (
        "TransitionSymbolNotInAlphabetException.Description", pSymbol //$NON-NLS-1$
            .getName (), pAlphabet.toString () ) );
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
