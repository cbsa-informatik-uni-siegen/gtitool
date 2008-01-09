package de.unisiegen.gtitool.ui.jgraphcomponents;


import java.awt.geom.Rectangle2D;

import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * This class represents the {@link State} in the gui.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class DefaultStateView extends DefaultGraphCell implements
    Storable
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -2780335257122860579L;


  /**
   * The {@link State} represented by this view.
   */
  private State state;


  /**
   * Creates a new {@link DefaultStateView}.
   * 
   * @param state The {@link State} represented by this view.
   * @param userObject The name of this {@link State}.
   */
  public DefaultStateView ( State state, Object userObject )
  {
    super ( userObject );
    this.state = state;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof DefaultStateView )
    {
      DefaultStateView stateView = ( DefaultStateView ) other;
      return this.state.equals ( stateView.getState () );
    }
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Storable#getElement()
   */
  public final Element getElement ()
  {
    Rectangle2D bounds = GraphConstants.getBounds ( this.getAttributes () );
    Element newElement = new Element ( "StateView" ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "x", bounds.getX () ) ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "y", bounds.getY () ) ); //$NON-NLS-1$
    newElement.addElement ( this.state.getElement () );
    return newElement;
  }


  /**
   * Returns the {@link State} of this view.
   * 
   * @return The {@link State} of this view.
   */
  public final State getState ()
  {
    return this.state;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.state.hashCode ();
  }
}
