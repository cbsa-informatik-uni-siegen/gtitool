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
 * @version $Id: GrammarDuplicateProductionException.java 695 2008-03-28
 *          18:02:32Z fehler $
 */
public final class GrammarDuplicateProductionException extends GrammarException
    implements ProductionInvolvedException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -222332690729733006L;


  /**
   * The {@link Production} list.
   */
  private ArrayList < Production > productionList;


  /**
   * Allocates a new {@link GrammarDuplicateProductionException}.
   * 
   * @param productionList The {@link Production} list.
   */
  public GrammarDuplicateProductionException (
      ArrayList < Production > productionList )
  {
    super ();
    // Production
    if ( productionList == null )
    {
      throw new NullPointerException ( "production is null" ); //$NON-NLS-1$
    }
    this.productionList = productionList;
    // Message and description
    setPrettyMessage ( Messages
        .getPrettyString ( "GrammarDuplicatProductionException.Message" ) ); //$NON-NLS-1$
    setPrettyDescription ( Messages
        .getPrettyString (
            "GrammarDuplicatProductionException.Description", productionList.get ( 0 ) ) ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see ProductionInvolvedException#getProduction()
   */
  public final ArrayList < Production > getProduction ()
  {
    return this.productionList;
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
    result.append ( "Productions: " ); //$NON-NLS-1$
    result.append ( this.productionList.toString () );
    return result.toString ();
  }
}
