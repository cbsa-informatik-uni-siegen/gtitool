package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.exceptions.state.StateException;


/**
 * TODO
 */
public class LR0State extends DefaultState
{
  public LR0State ( Alphabet alphabet, boolean startState, boolean finalState,
      ArrayList < LR0Item > lr0Items ) throws StateException
  {
      super(alphabet, new DefaultAlphabet(), makeStateString(lr0Items), startState, finalState);
  }
  
  private static String makeStateString(ArrayList<LR0Item> items)
  {
    String ret = "";
    for(LR0Item item : items)
      ret += item.toString() + '\n';
    return ret;
  }
}
