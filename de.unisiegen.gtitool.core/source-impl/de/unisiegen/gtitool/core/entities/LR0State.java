package de.unisiegen.gtitool.core.entities;


import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.exceptions.state.StateException;


/**
 * TODO
 */
public class LR0State extends LRState
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 1L;


  public LR0State ( final Alphabet alphabet, final boolean startState,
      final LR0ItemSet lr0Items ) throws StateException
  {
    super ( alphabet, makeStateString ( lr0Items ), startState );

    this.lr0Items = lr0Items;

  }


  public LR0ItemSet getLR0Items ()
  {
    return this.lr0Items;
  }


  private static String makeStateString ( LR0ItemSet items )
  {
    String ret = "";
    TreeSet < LR0Item > set = items.get ();

    Iterator < LR0Item > it = set.iterator ();

    while ( it.hasNext () )
    {
      ret += it.next ().toString ();

      if ( it.hasNext () )
        ret += ", ";
    }

    return ret;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.LRState#getItems()
   */
  @Override
  public LRItemSet getItems ()
  {
    return this.lr0Items;
  }


  private LR0ItemSet lr0Items;
}
