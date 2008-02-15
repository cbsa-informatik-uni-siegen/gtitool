package de.unisiegen.gtitool.core.exceptions.terminalsymbol;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link TerminalSymbolIllegalNameException} is used if the
 * {@link TerminalSymbol} is not correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class TerminalSymbolIllegalNameException extends
    TerminalSymbolException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 7733912010873253913L;


  /**
   * The illegal name of the {@link TerminalSymbol}.
   */
  private String illegalName;


  /**
   * Allocates a new {@link TerminalSymbolIllegalNameException}.
   * 
   * @param illegalName The illegal name of the {@link TerminalSymbol}.
   */
  public TerminalSymbolIllegalNameException ( String illegalName )
  {
    super ();
    // IllegalName
    if ( illegalName == null )
    {
      throw new NullPointerException ( "illegal name is null" ); //$NON-NLS-1$
    }
    this.illegalName = illegalName;
    // Message and Description
    setMessage ( Messages
        .getString ( "TerminalSymbolException.IllegalNameMessage" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString (
        "TerminalSymbolException.IllegalNameDescription", illegalName ) ); //$NON-NLS-1$
  }


  /**
   * Returns the illegal name of the {@link TerminalSymbol}.
   * 
   * @return The illegal name of the {@link TerminalSymbol}.
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
  public final String toString ()
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder result = new StringBuilder ( super.toString () );
    result.append ( lineBreak );
    result.append ( "Name:        " ); //$NON-NLS-1$
    result.append ( this.illegalName );
    return result.toString ();
  }
}