package de.unisiegen.gtitool.core.exceptions.machine;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.exceptions.SymbolsInvolvedException;
import de.unisiegen.gtitool.core.exceptions.TransitionsInvolvedException;


/**
 * The {@link MachineEpsilonTransitionException} is used, if there is a
 * {@link Transition} without a {@link Symbol}.
 * 
 * @author Christian Fehler
 * @version $Id: MachineEpsilonTransitionException.java 90 2007-11-04 16:20:27Z
 *          fehler $
 */
public final class MachineTransitionStackOperationsException extends
    MachineException implements TransitionsInvolvedException,
    SymbolsInvolvedException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -4864373653454376481L;


  /**
   * The {@link Transition}.
   */
  private Transition transition;


  /**
   * Allocates a new {@link MachineTransitionStackOperationsException}.
   * 
   * @param transition The {@link Transition}.
   */
  public MachineTransitionStackOperationsException ( Transition transition )
  {
    super ();
    // Transition
    if ( transition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    this.transition = transition;
    // Message and Description
    setMessage ( Messages
        .getString ( "MachineTransitionStackOperationsException.Message" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString (
        "MachineTransitionStackOperationsException.Description", //$NON-NLS-1$
        this.transition ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see SymbolsInvolvedException#getSymbol()
   */
  public final ArrayList < Symbol > getSymbol ()
  {
    ArrayList < Symbol > result = new ArrayList < Symbol > ();
    result.addAll ( this.transition.getPushDownWordRead ().get () );
    result.addAll ( this.transition.getPushDownWordWrite ().get () );
    return result;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TransitionsInvolvedException#getTransition()
   */
  public final ArrayList < Transition > getTransition ()
  {
    ArrayList < Transition > result = new ArrayList < Transition > ( 1 );
    result.add ( this.transition );
    return result;
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
    result.append ( "Transition:  " ); //$NON-NLS-1$
    result.append ( this.transition );
    return result.toString ();
  }
}
