package de.unisiegen.gtitool.core.exceptions.symbol;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


/**
 * The {@link SymbolIllegalNameException} is used if the {@link Symbol} is not
 * correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class SymbolIllegalNameException extends SymbolException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 9152320551893885255L;


  /**
   * The illegal name of the {@link Symbol}.
   */
  private String illegalName;


  /**
   * Allocates a new {@link SymbolIllegalNameException}.
   * 
   * @param illegalName The illegal name of the {@link Symbol}.
   */
  public SymbolIllegalNameException ( String illegalName )
  {
    super ();
    // IllegalName
    if ( illegalName == null )
    {
      throw new NullPointerException ( "illegal name is null" ); //$NON-NLS-1$
    }
    this.illegalName = illegalName;
    // Message and Description
    setMessage ( Messages.getString ( "SymbolException.IllegalNameMessage" ) ); //$NON-NLS-1$
    setDescription ( new PrettyString ( new PrettyToken ( Messages.getString (
        "SymbolException.IllegalNameDescription", illegalName ), Style.NONE ) ) ); //$NON-NLS-1$
  }


  /**
   * Returns the illegal name of the {@link Symbol}.
   * 
   * @return The illegal name of the {@link Symbol}.
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
