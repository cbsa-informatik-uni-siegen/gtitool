package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.exceptions.state.StateException;


/**
 * TODO
 */
public abstract class LRState extends DefaultState
{

  /**
   * TODO
   * 
   * @param alphabet
   * @param stateName
   * @param startState
   * @throws StateException
   */
  public LRState ( Alphabet alphabet, String stateName, boolean startState )
      throws StateException
  {
    super ( alphabet, new DefaultAlphabet (), stateName, startState, true );

    this.setId ( this.hashCode () );
  }


  public abstract LRItemSet getItems ();


  public int hashCode ()
  {
    return this.toString ().hashCode ();
  }
}
