package de.unisiegen.gtitool.core.entities;


import javax.jws.soap.SOAPBinding.Style;

import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;


/**
 * TODO
 */
public abstract class LRState extends DefaultState
{

  /**
   * TODO
   */
  private static final long serialVersionUID = -5759387584587327694L;


  /**
   * TODO
   * 
   * @param alphabet
   * @param stateName
   * @param startState
   * @throws StateException
   */
  public LRState ( final Alphabet alphabet, final String stateName,
      final boolean startState ) throws StateException
  {
    super ( alphabet, new DefaultAlphabet (), stateName, startState, true );

    this.setId ( this.hashCode () );

    this.index = -1;

    this.stateName = stateName;
  }


  public abstract LRItemSet getItems ();


  public void setIndex ( final int index )
  {
    this.index = index;

    try
    {
      this.setName ( "I" + this.index );
    }
    catch ( StateException e )
    {
      e.printStackTrace ();

      System.exit ( 1 );
    }
  }


  public int getIndex ()
  {
    return this.index;
  }


  public String shortName ()
  {
    return "I" + this.index; //$NON-NLS-1$
  }


  public int hashCode ()
  {
    return this.toString ().hashCode ();
  }


  public int compareTo ( State other )
  {
    return this.stateName.compareTo ( ( ( LRState ) other ).stateName );
  }


  private int index;


  private String stateName;
}
