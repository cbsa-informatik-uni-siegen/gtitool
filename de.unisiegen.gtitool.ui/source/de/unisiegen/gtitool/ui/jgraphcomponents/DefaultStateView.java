package de.unisiegen.gtitool.ui.jgraphcomponents;


import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * TODO
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class DefaultStateView extends DefaultGraphCell implements Storable
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


  /**
   * {@inheritDoc}
   * 
   * @see Storable#getElement()
   */
  public final Element getElement ()
  {
    Rectangle2D bounds = GraphConstants.getBounds ( this.getAttributes () );
    double x = bounds.getX ();
    double y = bounds.getY ();
    Element newElement = new Element ( "DefaultStateView" ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "x", String.valueOf ( x ) ) ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "y", String.valueOf ( y ) ) ); //$NON-NLS-1$
    newElement.addElement ( this.state.getElement () );
    return newElement;
  }
}
