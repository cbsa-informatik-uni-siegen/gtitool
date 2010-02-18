package de.unisiegen.gtitool.core.entities;


import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.exceptions.state.StateException;


/**
 * TODO
 */
public class LR1State extends DefaultState
{

  public LR1State ( Alphabet alphabet, boolean startState, LR1ItemSet lr1Items )
      throws StateException
  {
    super ( alphabet, new DefaultAlphabet (), makeStateString ( lr1Items ),
        startState, true );

    this.lr1Items = lr1Items;
    this.setId ( this.hashCode () );
  }


  public LR1ItemSet getLR1Items ()
  {
    return this.lr1Items;
  }


  private static String makeStateString ( LR1ItemSet items )
  {
    String ret = "";
    TreeSet < LR1Item > set = items.get ();

    Iterator < LR1Item > it = set.iterator ();

    while ( it.hasNext () )
    {
      ret += it.next ().toString ();

      if ( it.hasNext () )
        ret += ", ";
    }

    return ret;
  }


  public int hashCode ()
  {
    return this.toString ().hashCode ();
  }


  private LR1ItemSet lr1Items;
}
