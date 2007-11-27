package de.unisiegen.gtitool.core.exceptions.state;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>StateIllegalNameException</code> is used if the {@link State} is
 * not correct.
 * 
 * @author Christian Fehler
 * @version $Id$
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
   * Allocates a new <code>StateIllegalNameException</code>.
   * 
   * @param pIllegalName The illegal name of the {@link State}.
   */
  public StateIllegalNameException ( String pIllegalName )
  {
    super ();
    // IllegalName
    if ( pIllegalName == null )
    {
      throw new NullPointerException ( "illegal name is null" ); //$NON-NLS-1$
    }
    this.illegalName = pIllegalName;
    // Message and Description
    setMessage ( Messages.getString ( "StateException.IllegalNameMessage" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString (
        "StateException.IllegalNameDescription", pIllegalName ) ); //$NON-NLS-1$
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
