package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


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


  /**
   * Returns the state's lr items
   * 
   * @return the items
   */
  public abstract LRItemSet getItems ();


  /**
   * Set this state's index
   * 
   * @param index
   */
  public void setIndex ( final int index )
  {
    this.index = index;

    try
    {
      this.setName ( strShortName () );
    }
    catch ( StateException e )
    {
      e.printStackTrace ();

      System.exit ( 1 );
    }
  }


  /**
   * Get this state's index
   * 
   * @return the index
   */
  public int getIndex ()
  {
    return this.index;
  }


  /**
   * Get a PrettyString version of the short name
   * 
   * @return the pretty string
   */
  public PrettyString shortName ()
  {
    return new PrettyString ( new PrettyToken ( strShortName (), Style.TOKEN ) );
  }


  /**
   * Get the short name (i.e. the number of the state)
   * 
   * @return the name
   */
  private String strShortName ()
  {
    return new Integer ( this.index ).toString ();
  }


  /**
   * {@inheritDoc} Use the string's hash.
   * 
   * @see de.unisiegen.gtitool.core.entities.DefaultState#hashCode()
   */
  @Override
  public int hashCode ()
  {
    return this.toString ().hashCode ();
  }


  /**
   * Compare states by strings {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.DefaultState#compareTo(de.unisiegen.gtitool.core.entities.State)
   */
  @Override
  public int compareTo ( final State other )
  {
    return this.stateName.compareTo ( ( ( LRState ) other ).stateName );
  }


  /**
   * The state's unique index, starts with 0
   */
  private int index;


  /**
   * The state's name
   */
  private String stateName;
}
