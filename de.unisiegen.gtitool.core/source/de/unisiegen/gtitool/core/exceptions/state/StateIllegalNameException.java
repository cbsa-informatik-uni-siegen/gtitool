package de.unisiegen.gtitool.core.exceptions.state;


import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


/**
 * The {@link StateIllegalNameException} is used if the {@link State} is not
 * correct.
 * 
 * @author Christian Fehler
 * @version $Id: StateIllegalNameException.java 946 2008-05-30 14:27:24Z fehler
 *          $
 */
public final class StateIllegalNameException extends StateException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -5970932920929547448L;


  /**
   * The illegal name of the {@link State}.
   */
  private String illegalName;


  /**
   * Allocates a new {@link StateIllegalNameException}.
   * 
   * @param illegalName The illegal name of the {@link State}.
   */
  public StateIllegalNameException ( String illegalName )
  {
    super ();
    // IllegalName
    if ( illegalName == null )
    {
      throw new NullPointerException ( "illegal name is null" ); //$NON-NLS-1$
    }
    this.illegalName = illegalName;
    // Message and description
    setPrettyMessage ( Messages
        .getPrettyString ( "StateException.IllegalNameMessage" ) ); //$NON-NLS-1$
    setPrettyDescription ( new PrettyString ( new PrettyToken (
        Messages.getString (
            "StateException.IllegalNameDescription", illegalName ), Style.NONE ) ) ); //$NON-NLS-1$
  }


  /**
   * Returns the illegal name of the {@link State}.
   * 
   * @return The illegal name of the {@link State}.
   * @see #illegalName
   */
  public final String getIllegalName ()
  {
    return this.illegalName;
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
  public String toString ()
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder result = new StringBuilder ( super.toString () );
    result.append ( lineBreak );
    result.append ( "Name:        " ); //$NON-NLS-1$
    result.append ( this.illegalName );
    return result.toString ();
  }
}
