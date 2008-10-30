package de.unisiegen.gtitool.core.exceptions.grammar;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.exceptions.ProductionInvolvedException;
import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * The {@link GrammarDuplicateProductionException} is used, if there is a
 * {@link Production} that exists more than one time.
 * 
 * @author Benjamin Mies
 * @version $Id$
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

    // production list
    if ( productionList == null )
    {
      throw new NullPointerException ( "production list is null" ); //$NON-NLS-1$
    }
    this.productionList = productionList;

    // message
    setPrettyMessage ( Messages
        .getPrettyString ( "GrammarDuplicatProductionException.Message" ) ); //$NON-NLS-1$

    // description
    setPrettyDescription ( Messages.getPrettyString (
        "GrammarDuplicatProductionException.Description", productionList.get ( //$NON-NLS-1$
            0 ).toPrettyString () ) );
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
