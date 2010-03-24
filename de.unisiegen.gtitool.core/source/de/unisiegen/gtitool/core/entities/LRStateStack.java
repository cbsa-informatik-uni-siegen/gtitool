package de.unisiegen.gtitool.core.entities;


import java.util.Iterator;


/**
 * TODO
 */
public interface LRStateStack extends Entity < LRStateStack >,
    Iterable < LRState >
{

  /**
   * Returns an iterator for this stack
   * 
   * @return the iterator
   * @see java.lang.Iterable#iterator()
   */
  public Iterator < LRState > iterator ();


  /**
   * Returns the top elements and removes it
   * 
   * @return the former top element
   */
  public LRState pop ();


  /**
   * Pushes a new element
   * 
   * @param state
   */
  public void push ( LRState state );
}
