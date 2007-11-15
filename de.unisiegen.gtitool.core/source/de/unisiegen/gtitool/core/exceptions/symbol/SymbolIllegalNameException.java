package de.unisiegen.gtitool.core.exceptions.symbol;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Symbol;


/**
 * The <code>SymbolIllegalNameException</code> is used if the {@link Symbol}
 * is not correct.
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
   * Allocates a new <code>SymbolIllegalNameException</code>.
   * 
   * @param pIllegalName The illegal name of the {@link Symbol}.
   */
  public SymbolIllegalNameException ( String pIllegalName )
  {
    super ();
    // IllegalName
    if ( pIllegalName == null )
    {
      throw new NullPointerException ( "illegal name is null" ); //$NON-NLS-1$
    }
    this.illegalName = pIllegalName;
    // Message and Description
    setMessage ( Messages.getString ( "SymbolException.IllegalNameMessage" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString (
        "SymbolException.IllegalNameDescription", pIllegalName ) ); //$NON-NLS-1$
  }


  /**
   * Returns the illegal name of the {@link Symbol}.
   * 
   * @return The illegal name of the {@link Symbol}.
   * @see #illegalName
   */
  public String getIllegalName ()
  {
    return this.illegalName;
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
