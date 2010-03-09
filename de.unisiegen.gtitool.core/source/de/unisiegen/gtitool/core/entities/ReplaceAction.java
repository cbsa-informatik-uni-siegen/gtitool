package de.unisiegen.gtitool.core.entities;


/**
 * Base class for {@link ReduceAction} and {@link ReverseReduceAction}
 */
public abstract class ReplaceAction implements Action
{

  /**
   * The {@link Production}
   */
  protected Production production;


  /**
   * Allocates a new {@link ReplaceAction}
   * 
   * @param production The {@link Production}
   */
  public ReplaceAction ( final Production production )
  {
    this.production = production;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Action#getReduceAction()
   */
  public Production getReduceAction ()
  {
    return this.production;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Action#getTransitionType()
   */
  abstract public TransitionType getTransitionType ();


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( Action o )
  {
    // ReduceActions are above everything
    if ( ! ( o instanceof ReduceAction ) )
      return 1;
    return this.production.compareTo ( o.getReduceAction () );
  }

}
