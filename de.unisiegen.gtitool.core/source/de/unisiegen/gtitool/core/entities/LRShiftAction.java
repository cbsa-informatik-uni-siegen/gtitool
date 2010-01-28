package de.unisiegen.gtitool.core.entities;


/**
 * TODO
 *
 */
public class LRShiftAction implements LRAction
{

  /**
   * TODO
   *
   * @return
   * @see de.unisiegen.gtitool.core.entities.LRAction#getReduceAction()
   */
  public Production getReduceAction ()
  {
    return null;
  }

  /**
   * TODO
   *
   * @return
   * @see de.unisiegen.gtitool.core.entities.LRAction#getTransitionType()
   */
  public TransitionType getTransitionType ()
  {
    return LRAction.TransitionType.SHIFT;
  }

}
