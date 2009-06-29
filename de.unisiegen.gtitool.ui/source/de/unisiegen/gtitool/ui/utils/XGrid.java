package de.unisiegen.gtitool.ui.utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;


/**
 * A Grid for the x coordinate of the States in the Convert Regex -> ENFA
 */
public class XGrid
{

  /**
   * The x positions of the states
   */
  private HashMap < String, Integer > x_positions = new HashMap < String, Integer > ();


  /**
   * Creates new XGrid
   */
  public XGrid ()
  {
    // Nothing to do here
  }


  /**
   * Moves a state, so that it does not go too far away
   * 
   * @param v The state
   * @param i The new position
   */
  public void moveState ( DefaultStateView v, Integer i )
  {
    Integer n = i;
    ArrayList < Integer > list = new ArrayList < Integer > ();
    Integer was = this.x_positions.remove ( v.getState ().getName () );
    list.addAll ( this.x_positions.values () );
    Collections.sort ( list );
    int last = list.get ( list.size () - 1 ).intValue ();
    if ( n.intValue () - last > 1 )
    {
      n = new Integer ( last + 1 );
    }
    if(!list.contains ( was )) {
      n = was;
    }
    this.x_positions.put ( v.getState ().getName (), n );
  }


  /**
   * Returns the x_positions.
   * 
   * @return The x_positions.
   * @see #x_positions
   */
  public HashMap < String, Integer > getX_positions ()
  {
    return this.x_positions;
  }


  /**
   * Sets the x_positions.
   * 
   * @param x_positions The x_positions to set.
   * @see #x_positions
   */
  public void setX_positions ( HashMap < String, Integer > x_positions )
  {
    this.x_positions = x_positions;
  }
}
