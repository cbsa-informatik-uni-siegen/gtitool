package de.unisiegen.gtitool.core.entities;



/**
 * TODO
 *
 */
public class LRAcceptAction implements LRAction
{
  public TransitionType getTransitionType ()
  {
    return LRAction.TransitionType.ACCEPT;
  }
  
  
  public Production getReduceAction()
  {
    return null;
  }
}
