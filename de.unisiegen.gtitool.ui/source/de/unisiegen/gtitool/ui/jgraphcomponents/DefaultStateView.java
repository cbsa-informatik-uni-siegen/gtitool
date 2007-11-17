package de.unisiegen.gtitool.ui.jgraphcomponents;


import java.util.ArrayList;

import org.jgraph.graph.DefaultGraphCell;

import de.unisiegen.gtitool.core.entities.State;


/**
 * TODO
 * 
 * @author Benjamin Mies
 * @version $Id: NewDialog.java 119 2007-11-10 12:07:30Z fehler
 */
public class DefaultStateView extends DefaultGraphCell
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -2780335257122860579L;


  /** The {@link State} represented by this view */
  State state;


  /** List with all objects which should be removed if this view is deleted */
  private ArrayList < DefaultGraphCell > removeList;


  /**
   * Create a new {@link DefaultStateView}
   * 
   * @param pState the State represented by this view
   * @param pUserObject the name of this State
   */
  public DefaultStateView ( State pState, Object pUserObject )
  {
    super ( pUserObject );
    this.state = pState;
    this.removeList = new ArrayList < DefaultGraphCell > ();
    this.removeList.add ( this );
  }


  /**
   * Get the {@link State} of this view
   * 
   * @return the {@link State} of this view
   */
  public State getState ()
  {
    return this.state;
  }


  /**
   * Add a new Transition to this state view
   * 
   * @param transition
   */
  public void addTransition ( DefaultTransitionView transition )
  {
    this.removeList.add ( transition );
  }


  /**
   * Get the Objects to remove if this view is deleted
   * 
   * @return Array containing all Objects to remove if this view is deleted
   */
  public Object [] getRemoveObjects ()
  {
    return this.removeList.toArray ();
  }

}
