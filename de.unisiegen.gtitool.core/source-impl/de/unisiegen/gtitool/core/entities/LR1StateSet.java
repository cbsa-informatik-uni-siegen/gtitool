package de.unisiegen.gtitool.core.entities;


import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;


/**
 * TODO
 */
public class LR1StateSet implements Iterable < LR1State >
{

  public LR1StateSet ()
  {
    this.rep = new TreeSet < LR1State > ( new Comparator < LR1State > ()
    {

      public int compare ( final LR1State o1, final LR1State o2 )
      {
        return o1.getLR1Items ().compareTo ( o2.getLR1Items () );
      }
    } );
  }


  public void add ( final LR1State state )
  {
    this.rep.add ( state );
  }


  public void add ( final LR1StateSet states )
  {
    this.rep.addAll ( states.rep );
  }


  public class AddPair
  {

    public AddPair ( final boolean isNew, final LR1State state )
    {
      this.isNew = isNew;

      this.state = state;
    }


    public boolean getIsNew ()
    {
      return this.isNew;
    }


    public LR1State getState ()
    {
      return this.state;
    }


    private final boolean isNew;


    private final LR1State state;

  }


  /**
   * TODO
   *
   * @param newState
   * @return
   */
  public boolean addIfNonExistant ( final LR1State newState )
  {
    if ( this.contains ( newState ) )
      return false;

    this.add ( newState );

    return true;
  }


  public AddPair addOrReturn ( final LR1State newState )
  {
    final LR1State ceiling = this.rep.ceiling ( newState );

    if ( ceiling != null && ceiling.equals ( newState ) )
      return new AddPair ( false, ceiling );

    this.add ( newState );

    return new AddPair ( true, newState );
  }


  public boolean contains ( final LR1State state )
  {
    return this.rep.contains ( state );
  }


  public boolean isEmpty ()
  {
    return this.rep.isEmpty ();
  }


  /**
   * TODO
   * 
   * @return
   * @see java.lang.Iterable#iterator()
   */
  public Iterator < LR1State > iterator ()
  {
    return this.rep.iterator ();
  }


  private TreeSet < LR1State > rep;

}
