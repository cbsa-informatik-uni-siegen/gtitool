package de.unisiegen.gtitool.core.exceptions.transition;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;


/**
 * The <code>TransitionSymbolNotInAlphabetException</code> is used, if the
 * {@link Symbol} which should be added to the {@link Transition} is not in the
 * {@link Alphabet} of the {@link Transition}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class TransitionSymbolNotInAlphabetException extends
    TransitionException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2829905559325782948L;


  /**
   * Allocates a new <code>MachineStateStartException</code>.
   * 
   * @param pTransition The {@link Transition}.
   * @param pAlphabet The {@link Alphabet}.
   * @param pSymbol The {@link Symbol}.
   */
  public TransitionSymbolNotInAlphabetException ( Transition pTransition,
      Alphabet pAlphabet, Symbol pSymbol )
  {
    super ( pTransition, pAlphabet, pSymbol );
    // Message and Description
    setMessage ( Messages
        .getString ( "TransitionSymbolNotInAlphabetException.Message" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString (
        "TransitionSymbolNotInAlphabetException.Description", pSymbol //$NON-NLS-1$
            .getName (), pAlphabet.toString () ) );
  }
}
