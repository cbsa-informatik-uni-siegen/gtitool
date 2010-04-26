package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * Represents a {@link ReduceAction}
 */
public class ReduceAction extends ReplaceAction
{

  /**
   * Allocates a new {@link ReduceAction}
   * 
   * @param production The {@link Production}
   */
  public ReduceAction ( final Production production )
  {
    super ( production );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Action#getTransitionType()
   */
  @Override
  public TransitionType getTransitionType ()
  {
    return Action.TransitionType.REDUCE;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    // strip the dot from the production
    String prodName = this.production.toString ();
    prodName = prodName.replace ( LRItem.dotString (), "" ); //$NON-NLS-1$

    return Messages.getString ( "Entities.Actions.Reduce" ) + " " + prodName; //$NON-NLS-1$ //$NON-NLS-2$
  }
}
