package de.unisiegen.gtitool.core.entities;


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

  public LR0ItemSet getLR0Items()
  {
    return this.lr0Items;
  }

  private static String makeStateString ( LR0ItemSet items )
  {
    String ret = "";
    for ( LR0Item item : items )
      ret += item.toString () + '\n';
    return ret;
  }
  
  private LR0ItemSet lr0Items;
}
