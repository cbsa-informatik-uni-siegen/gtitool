package de.unisiegen.gtitool.core.entities;


import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.exceptions.state.StateException;


/**
 * TODO
 */
public class LR0State extends DefaultState
{

  public LR0State ( Alphabet alphabet, boolean startState, LR0ItemSet lr0Items )
      throws StateException
  {
    super ( alphabet, new DefaultAlphabet (), makeStateString ( lr0Items ),
        startState, true );

    this.lr0Items = lr0Items;
  }


  public LR0ItemSet getLR0Items ()
  {
    return this.lr0Items;
  }


  public boolean equals ( Object other )
  {
    if ( ! ( other instanceof LR0State ) )
      return false;

    return ( ( LR0State ) other ).getLR0Items ().equals ( getLR0Items () );
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


  private LR0ItemSet lr0Items;
}
