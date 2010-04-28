package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


/**
 * TODO
 */
public class RejectAction implements Action
{

  /**
   * {@inheritDoc}
   * 
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
   * @see de.unisiegen.gtitool.core.entities.Action#getTransitionType()
   */
  public TransitionType getTransitionType ()
  {
    return Action.TransitionType.REJECTED;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( Action o )
  {
    // RejectActions are below everything
    if ( o instanceof RejectAction )
      return 0;
    return -1;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#addPrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void addPrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#removePrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void removePrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    return new PrettyString ( new PrettyToken ( Messages
        .getString ( "RejectAction" ), Style.NONE ) ); //$NON-NLS-1$
  }
  
  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    return "BLABLABLA";
  }

}
