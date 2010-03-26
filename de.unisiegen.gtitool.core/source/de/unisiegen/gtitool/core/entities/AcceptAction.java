package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


/**
 * The LR Accept action
 */
public class AcceptAction implements Action
{

  /**
   * The transition type
   * 
   * @return ACCEPT
   * @see de.unisiegen.gtitool.core.entities.Action#getTransitionType()
   */
  public TransitionType getTransitionType ()
  {
    return Action.TransitionType.ACCEPT;
  }


  /**
   * The reduce action
   * 
   * @return null (nothing to reduce)
   * @see de.unisiegen.gtitool.core.entities.Action#getReduceAction()
   */
  public Production getReduceAction ()
  {
    throw new RuntimeException (
        "accept action cannot be used as a reduce action" ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( final Action o )
  {
    // AcceptActions are below everything
    if ( o instanceof AcceptAction )
      return 0;
    return -1;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    return Messages.getString ( "Entities.Actions.Accept" ); //$NON-NLS-1$
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
