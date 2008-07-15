package de.unisiegen.gtitool.core.exceptions.transition;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * The {@link TransitionSymbolOnlyOneTimeException} is used, if the
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
   * Allocates a new {@link TransitionSymbolOnlyOneTimeException}.
   * 
   * @param transition The {@link Transition}.
   * @param symbolList The {@link Symbol}s.
   */
  public TransitionSymbolOnlyOneTimeException ( Transition transition,
      ArrayList < Symbol > symbolList )
  {
    super ( transition, symbolList );
    // Message and description
    setPrettyMessage ( Messages
        .getPrettyString ( "TransitionSymbolOnlyOneTimeException.Message" ) ); //$NON-NLS-1$
    setPrettyDescription ( Messages.getPrettyString (
        "TransitionSymbolOnlyOneTimeException.Description", symbolList.get ( 0 ) //$NON-NLS-1$
            .toPrettyString (), transition.getStateBegin ().toPrettyString (),
        transition.getStateEnd ().toPrettyString () ) );
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
