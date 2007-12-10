package de.unisiegen.gtitool.core.exceptions.transition;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>TransitionSymbolOnlyOneTimeException</code> is used, if the
 * {@link Symbol} which should be added to the {@link Transition} is already a
 * {@link Symbol} in the {@link Transition}.
 * 
 * @author Christian Fehler
 * @version $Id: TransitionSymbolNotInAlphabetException.java 120 2007-11-10
 *          13:01:38Z fehler $
 */
public final class TransitionSymbolOnlyOneTimeException extends
    TransitionException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2829905559325782948L;


  /**
   * Allocates a new <code>TransitionSymbolOnlyOneTimeException</code>.
   * 
   * @param pTransition The {@link Transition}.
   * @param pSymbolList The {@link Symbol}s.
   */
  public TransitionSymbolOnlyOneTimeException ( Transition pTransition,
      ArrayList < Symbol > pSymbolList )
  {
    super ( pTransition, pSymbolList );
    // Message and Description
    setMessage ( Messages
        .getString ( "TransitionSymbolOnlyOneTimeException.Message" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString (
        "TransitionSymbolOnlyOneTimeException.Description", pSymbolList //$NON-NLS-1$
            .get ( 0 ).getName (), pTransition.getStateBegin ().getName (),
        pTransition.getStateEnd ().getName () ) );
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
