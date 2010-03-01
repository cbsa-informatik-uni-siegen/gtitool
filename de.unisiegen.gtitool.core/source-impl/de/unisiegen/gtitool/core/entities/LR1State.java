package de.unisiegen.gtitool.core.entities;


import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.exceptions.state.StateException;


/**
 * TODO
 */
public class LR1State extends LRState
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 1L;


  /**
   * TODO
   * 
   * @param alphabet
   * @param startState
   * @param lr1Items
   * @throws StateException
   */
  public LR1State ( Alphabet alphabet, boolean startState, LR1ItemSet lr1Items )
      throws StateException
  {
    super ( alphabet, makeStateString ( lr1Items ), startState );

    this.lr1Items = lr1Items;
  }


  /**
   * TODO
   * 
   * @return
   */
  public LR1ItemSet getLR1Items ()
  {
    return this.lr1Items;
  }


  /**
   * Get the LR0ItemSet of this LR1 State
   * 
   * @return
   */
  public LR0ItemSet getLR0Part ()
  {
    LR0ItemSet ret = new LR0ItemSet ();
    for ( LRItem item : this.lr1Items )
      ret.add ( new LR0Item ( item.getNonterminalSymbol (), item
          .getProductionWord (), item.getDotPosition () ) );

    return ret;
  }


  /**
   * TODO
   * 
   * @param items
   * @return
   */
  private static String makeStateString ( LR1ItemSet items )
  {
    String ret = ""; //$NON-NLS-1$
    TreeSet < LR1Item > set = items.get ();

    Iterator < LR1Item > it = set.iterator ();

    while ( it.hasNext () )
    {
      ret += it.next ().toString ();

      if ( it.hasNext () )
        ret += ", "; //$NON-NLS-1$
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
    return this.lr1Items;
  }


  /**
   * The LR1 items belonging to this state
   */
  private LR1ItemSet lr1Items;
}
