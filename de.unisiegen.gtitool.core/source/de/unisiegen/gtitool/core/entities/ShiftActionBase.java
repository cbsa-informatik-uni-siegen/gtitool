package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


/**
 * Base class for {@link ShiftAction} and {@link CancelOutAction}
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


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#addPrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void addPrettyStringChangedListener (
      @SuppressWarnings ( "unused" ) PrettyStringChangedListener listener )
  {
    // will never change
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#removePrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void removePrettyStringChangedListener (
      @SuppressWarnings ( "unused" ) PrettyStringChangedListener listener )
  {
    // Will never change
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    return new PrettyString ( new PrettyToken ( toString (), Style.NONE ) );
  }
}
