package de.unisiegen.gtitool.core.entities;


/**
 * Base class for {@link ShiftAction} and {@link CancleOutAction}
 */
public abstract class ShiftActionBase implements Action
{
  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Action#getReduceAction()
   */
  public Production getReduceAction ()
  {
    throw new RuntimeException (
        "ShiftAction cannot be used as a reduce action" ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( Action o )
  {
    // ShiftActions are above AcceptActions but below ReduceActions
    if ( o instanceof ShiftActionBase )
      return 0;
    if ( o instanceof AcceptAction )
      return 1;
    return -1;
  }
}
