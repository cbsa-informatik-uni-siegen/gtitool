package de.unisiegen.gtitool.core.exceptions.stateset;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.StateSet;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link StateSetMoreThanOneStateException} is used if the {@link StateSet}
 * is not correct.
 * 
 * @author Christian Fehler
 * @version $Id: AlphabetMoreThanOneSymbolException.java 189 2007-11-17
 *          15:55:30Z fehler $
 */
public final class StateSetMoreThanOneStateException extends StateSetException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -2581473982295914649L;


  /**
   * Allocates a new {@link StateSetMoreThanOneStateException}.
   * 
   * @param stateSet The {@link StateSet}.
   * @param stateList The {@link State}s.
   */
  public StateSetMoreThanOneStateException ( StateSet stateSet,
      ArrayList < State > stateList )
  {
    super ( stateSet, stateList );
    // Message and description
    setPrettyMessage ( Messages
        .getPrettyString ( "StateSetException.MoreThanOneStateMessage" ) ); //$NON-NLS-1$
    setPrettyDescription ( Messages.getPrettyString (
        "StateSetException.MoreThanOneStateDescription", //$NON-NLS-1$
        stateList.get ( 0 ), stateSet ) );
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
