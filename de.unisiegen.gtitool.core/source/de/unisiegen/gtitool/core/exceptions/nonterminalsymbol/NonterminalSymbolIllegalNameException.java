package de.unisiegen.gtitool.core.exceptions.nonterminalsymbol;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link NonterminalSymbolIllegalNameException} is used if the
 * {@link NonterminalSymbol} is not correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class NonterminalSymbolIllegalNameException extends
    NonterminalSymbolException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 1347927562150079310L;


  /**
   * The illegal name of the {@link NonterminalSymbol}.
   */
  private String illegalName;


  /**
   * Allocates a new {@link NonterminalSymbolIllegalNameException}.
   * 
   * @param illegalName The illegal name of the {@link NonterminalSymbol}.
   */
  public NonterminalSymbolIllegalNameException ( String illegalName )
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
        .getString ( "NonterminalSymbolException.IllegalNameMessage" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString (
        "NonterminalSymbolException.IllegalNameDescription", illegalName ) ); //$NON-NLS-1$
  }


  /**
   * Returns the illegal name of the {@link NonterminalSymbol}.
   * 
   * @return The illegal name of the {@link NonterminalSymbol}.
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
