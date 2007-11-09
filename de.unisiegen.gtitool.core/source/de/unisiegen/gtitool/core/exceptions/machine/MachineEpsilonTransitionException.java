package de.unisiegen.gtitool.core.exceptions.machine;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;


/**
 * The <code>MachineEpsilonTransitionException</code> is used, if there is a
 * {@link Transition} without a {@link Symbol}.
 * 
 * @author Christian Fehler
 * @version $Id: MachineEpsilonTransitionException.java 90 2007-11-04 16:20:27Z
 *          fehler $
 */
public final class MachineEpsilonTransitionException extends MachineException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -7273516723544742004L;


  /**
   * The {@link Transition}.
   */
  private Transition transition;


  /**
   * Allocates a new <code>MachineEpsilonTransitionException</code>.
   * 
   * @param pTransition The {@link Transition}.
   */
  public MachineEpsilonTransitionException ( Transition pTransition )
  {
    super ();
    // Transition
    if ( pTransition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    this.transition = pTransition;
    setMessage ( Messages
        .getString ( "MachineEpsilonTransitionException.Message" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString (
        "MachineEpsilonTransitionException.Description", this.transition //$NON-NLS-1$
            .getStateBegin ().getName (), this.transition.getStateEnd ()
            .getName () ) );
  }


  /**
   * Returns the {@link State} where the {@link Transition} begins.
   * 
   * @return The {@link State} where the {@link Transition} begins.
   */
  public final State getStateBegin ()
  {
    return this.transition.getStateBegin ();
  }


  /**
   * Returns the {@link State} where the {@link Transition} ends.
   * 
   * @return The {@link State} where the {@link Transition} ends.
   */
  public final State getStateEnd ()
  {
    return this.transition.getStateEnd ();
  }


  /**
   * Returns the {@link Transition}.
   * 
   * @return The {@link Transition}.
   * @see #transition
   */
  public final Transition getTransition ()
  {
    return this.transition;
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
    StringBuilder result = new StringBuilder ();
    result.append ( "Message:     " + getMessage () + lineBreak ); //$NON-NLS-1$
    result.append ( "Description: " + getDescription () + lineBreak ); //$NON-NLS-1$
    result.append ( "Transition:  " //$NON-NLS-1$
        + this.transition.getStateBegin ().getName () + " -> " //$NON-NLS-1$
        + this.transition.getStateEnd ().getName () );
    return result.toString ();
  }
}
