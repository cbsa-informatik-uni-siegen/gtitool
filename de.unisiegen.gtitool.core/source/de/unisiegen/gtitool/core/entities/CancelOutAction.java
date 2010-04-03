package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * Represents the {@link CancelOutAction}
 */
public class CancelOutAction extends ShiftActionBase
{

  /**
   * The {@link Symbol} which was canceled out
   */
  private Symbol symbol;


  /**
   * Allocates a new {@link CancelOutAction}
   */
  public CancelOutAction ()
  {
    this.symbol = new DefaultSymbol ();
  }


  /**
   * Allocates a new {@link CancelOutAction}
   * 
   * @param symbol The {@link Symbol}
   */
  public CancelOutAction ( final Symbol symbol )
  {
    this.symbol = symbol;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Action#getTransitionType()
   */
  public TransitionType getTransitionType ()
  {
    return Action.TransitionType.CANCEL;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    if ( this.symbol.getName ().length () == 0 )
      throw new RuntimeException ( "symbol is empty!" ); //$NON-NLS-1$

    return Messages.getString (
        "Entities.Actions.Match", this.symbol.getName () ); //$NON-NLS-1$
  }
}
