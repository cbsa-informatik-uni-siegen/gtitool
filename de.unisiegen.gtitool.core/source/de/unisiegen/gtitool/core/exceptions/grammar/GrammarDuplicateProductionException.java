package de.unisiegen.gtitool.core.exceptions.grammar;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.exceptions.ProductionInvolvedException;


/**
 * The {@link GrammarDuplicateProductionException} is used, if there is a
 * {@link Production} that exists more than one time.
 * 
 * @author Benjamin Mies
 * @version $Id: MachineAllSymbolsException.java 639 2008-03-14 11:43:47Z fehler $
 */
public final class GrammarDuplicateProductionException extends GrammarException
    implements ProductionInvolvedException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -222332690729733006L;


  /**
   * The {@link Production}.
   */
  private Production production;


  /**
   * Allocates a new {@link GrammarDuplicateProductionException}.
   * 
   * @param production The {@link Production}.
   */
  public GrammarDuplicateProductionException ( Production production )
  {
    super ();
    // Production
    if ( production == null )
    {
      throw new NullPointerException ( "production is null" ); //$NON-NLS-1$
    }
    this.production = production;

    setMessage ( Messages
        .getString ( "GrammarDuplicatProductionException.Message" ) ); //$NON-NLS-1$
    setDescription ( Messages.getPrettyString (
        "GrammarDuplicatProductionException.Description", production ) ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see ProductionInvolvedException#getProductions()
   */
  public final ArrayList < Production > getProductions ()
  {
    ArrayList < Production > result = new ArrayList < Production > ( 1 );
    result.add ( this.production );
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
    result.append ( "Production: " ); //$NON-NLS-1$
    result.append ( this.production.toString () );
    return result.toString ();
  }
}
