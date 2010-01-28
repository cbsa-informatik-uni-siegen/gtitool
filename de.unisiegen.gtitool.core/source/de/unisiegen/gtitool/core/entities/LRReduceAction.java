package de.unisiegen.gtitool.core.entities;


/**
 * TODO
 *
 */
public class LRReduceAction implements LRAction
{

  public LRReduceAction(Production production)
  {
    this.production = production;
  }
  /**
   * TODO
   *
   * @return
   * @see de.unisiegen.gtitool.core.entities.LRAction#getReduceAction()
   */
  public Production getReduceAction ()
  {
    return production;
  }

  /**
   * TODO
   *
   * @return
   * @see de.unisiegen.gtitool.core.entities.LRAction#getTransitionType()
   */
  public TransitionType getTransitionType ()
  {
    return LRAction.TransitionType.REDUCE;
  }

  private Production production;
}
