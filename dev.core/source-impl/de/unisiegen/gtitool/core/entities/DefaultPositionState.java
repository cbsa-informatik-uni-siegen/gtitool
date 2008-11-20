package de.unisiegen.gtitool.core.entities;


import java.util.HashSet;

import de.unisiegen.gtitool.core.exceptions.state.StateException;


/**
 * TODO
 */
public class DefaultPositionState extends DefaultState
{

  /**
   * TODO
   */
  private static final long serialVersionUID = -7451051647706513194L;


  /**
   * TODO
   */
  private HashSet < Integer > positions;


  /**
   * TODO
   */
  private boolean mark;


  /**
   * TODO
   * 
   * @param name
   * @param positions
   * @throws StateException
   */
  public DefaultPositionState ( String name, HashSet < Integer > positions )
      throws StateException
  {
    super ( name );
    this.positions = positions;
    this.mark = false;
  }


  /**
   * Returns the positions.
   * 
   * @return The positions.
   * @see #positions
   */
  public HashSet < Integer > getPositions ()
  {
    return this.positions;
  }


  /**
   * Returns the mark.
   * 
   * @return The mark.
   * @see #mark
   */
  public boolean isMarked ()
  {
    return this.mark;
  }


  /**
   * Marks the State
   */
  public void mark ()
  {
    this.mark = true;
  }
  
  /**
   * TODO
   *
   * @param other
   * @return
   * @see de.unisiegen.gtitool.core.entities.DefaultState#equals(java.lang.Object)
   */
  @Override
  public boolean equals ( Object other )
  {
    if(other instanceof DefaultPositionState) {
      DefaultPositionState positionState = ( DefaultPositionState ) other;
      return this.positions.equals ( positionState.getPositions () );
    }
    return false;
  }
  
}
