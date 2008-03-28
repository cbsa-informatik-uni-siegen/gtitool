package de.unisiegen.gtitool.core.exceptions.machine;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.exceptions.StatesInvolvedException;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


/**
 * The {@link MachineStateStartException} is used, if no start state is defined
 * or more than one start state is defined.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class MachineStateStartException extends MachineException
    implements StatesInvolvedException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6464106217286292160L;


  /**
   * The list of {@link State}s.
   */
  private ArrayList < State > stateList = new ArrayList < State > ();


  /**
   * Allocates a new {@link MachineStateStartException}.
   * 
   * @param stateList The list of {@link State}s.
   */
  public MachineStateStartException ( ArrayList < State > stateList )
  {
    // StateList
    if ( stateList == null )
    {
      throw new NullPointerException ( "state list is null" ); //$NON-NLS-1$
    }
    this.stateList = stateList;
    // StateList
    if ( stateList.size () == 1 )
    {
      throw new IllegalArgumentException ( "no exception: one start state" ); //$NON-NLS-1$
    }
    // Message and description
    if ( stateList.size () == 0 )
    {
      setPrettyMessage ( Messages
          .getPrettyString ( "MachineStateStartException.NoStartStateMessage" ) ); //$NON-NLS-1$
      setPrettyDescription ( Messages
          .getPrettyString ( "MachineStateStartException.NoStartStateDescription" ) ); //$NON-NLS-1$
    }
    else
    {
      setPrettyMessage ( Messages
          .getPrettyString ( "MachineStateStartException.MoreThanOneStartStateMessage" ) ); //$NON-NLS-1$
      PrettyString prettyString = new PrettyString ();
      for ( int i = 0 ; i < stateList.size () ; i++ )
      {
        prettyString.addPrettyToken ( new PrettyToken ( Messages.QUOTE,
            Style.NONE ) );
        prettyString.addPrettyPrintable ( stateList.get ( i ) );
        prettyString.addPrettyToken ( new PrettyToken ( Messages.QUOTE,
            Style.NONE ) );
        if ( i < stateList.size () - 2 )
        {
          prettyString.addPrettyToken ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
        }
        if ( i == stateList.size () - 2 )
        {
          prettyString.addPrettyToken ( new PrettyToken ( " " //$NON-NLS-1$
              + Messages.getString ( "And" ) + " ", Style.NONE ) ); //$NON-NLS-1$ //$NON-NLS-2$
        }
      }
      setPrettyDescription ( Messages.getPrettyString (
          "MachineStateStartException.MoreThanOneStartStateDescription", true, //$NON-NLS-1$
          prettyString ) );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see StatesInvolvedException#getState()
   */
  public final ArrayList < State > getState ()
  {
    return this.stateList;
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
    result.append ( "States:      " ); //$NON-NLS-1$
    for ( int i = 0 ; i < this.stateList.size () ; i++ )
    {
      if ( i > 0 )
      {
        result.append ( ", " ); //$NON-NLS-1$
      }
      result.append ( this.stateList.get ( i ).getName () );
    }
    return result.toString ();
  }
}
