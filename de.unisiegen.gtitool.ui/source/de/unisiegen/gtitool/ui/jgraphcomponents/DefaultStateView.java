package de.unisiegen.gtitool.ui.jgraphcomponents;


import java.awt.geom.Rectangle2D;

import javax.swing.event.EventListenerList;

import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;
import org.jgraph.event.GraphModelEvent.GraphModelChange;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.ui.redoundo.StatePositionChangedListener;


/**
 * This class represents the {@link State} in the gui.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultStateView extends DefaultGraphCell implements
    Storable, Modifyable
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -2780335257122860579L;


  /**
   * The position is not defined value.
   */
  public static final double POSITION_NOT_DEFINED = Double.MIN_VALUE;


  /**
   * The {@link State} represented by this view.
   */
  private State state;


  /**
   * Flag signals that nex state move should be ignored.
   */
  private boolean ignoreStateMove = false;


  /**
   * The initial x position.
   */
  private double initialXPosition = POSITION_NOT_DEFINED;


  /**
   * The puplished x position.
   */
  private double puplishedXPosition = POSITION_NOT_DEFINED;


  /**
   * The actual x value.
   */
  private double xValue = POSITION_NOT_DEFINED;


  /**
   * The actual y value.
   */
  private double yValue = POSITION_NOT_DEFINED;


  /**
   * The initial y position.
   */
  private double initialYPosition = POSITION_NOT_DEFINED;


  /**
   * The puplished y position.
   */
  private double puplishedYPosition = POSITION_NOT_DEFINED;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The {@link DefaultGraphModel} for this model.
   */
  private DefaultGraphModel graphModel;


  /**
   * Creates a new {@link DefaultStateView}.
   * 
   * @param graphModel The {@link DefaultGraphModel}.
   * @param state The {@link State} represented by this view.
   */
  public DefaultStateView ( DefaultGraphModel graphModel, State state )
  {
    super ( state );

    this.graphModel = graphModel;
    this.state = state;

    this.graphModel.addGraphModelListener ( new GraphModelListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void graphChanged ( GraphModelEvent event )
      {
        GraphModelChange graphModelChange = event.getChange ();
        Object [] changed = graphModelChange.getChanged ();
        for ( Object current : changed )
        {
          if ( current instanceof DefaultStateView )
          {
            DefaultStateView defaultStateView = ( DefaultStateView ) current;
            if ( defaultStateView.getState ().equals ( getState () ) )
            {
              fireModifyStatusChanged ();
              fireStatePositionChanged ();
              return;
            }
          }
        }
      }
    } );

    // Reset modify
    resetModify ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#addModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * /** Adds the given {@link StatePositionChangedListener}.
   * 
   * @param listener The {@link StatePositionChangedListener}.
   */
  public final void addStatePositionChangedListener (
      StatePositionChangedListener listener )
  {
    this.listenerList.add ( StatePositionChangedListener.class, listener );
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
   * Let the listeners know that the modify status has changed.
   */
  private final void fireModifyStatusChanged ()
  {
    ModifyStatusChangedListener [] listeners = this.listenerList
        .getListeners ( ModifyStatusChangedListener.class );
    double x = getXPosition ();
    double y = getYPosition ();
    if ( ( this.puplishedXPosition != x ) || ( this.puplishedYPosition != y ) )
    {
      this.puplishedXPosition = x;
      this.puplishedYPosition = y;
      boolean newModifyStatus = isModified ();
      for ( ModifyStatusChangedListener current : listeners )
      {
        current.modifyStatusChanged ( newModifyStatus );
      }
    }
  }


  /**
   * Let the listeners know that the state position has changed.
   */
  private final void fireStatePositionChanged ()
  {
    if ( this.ignoreStateMove )
    {
      this.ignoreStateMove = false;
      return;
    }

    StatePositionChangedListener [] listeners = this.listenerList
        .getListeners ( StatePositionChangedListener.class );
    double x = getXPosition ();
    double y = getYPosition ();
    if ( ( this.xValue != x ) || ( this.yValue != y ) )
    {
      for ( StatePositionChangedListener current : listeners )
      {
        current.statePositionChanged ( this, this.xValue, this.yValue, x, y );
      }
      this.xValue = x;
      this.yValue = y;
    }
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
   * Returns the x position.
   * 
   * @return The x position.
   */
  public final double getXPosition ()
  {
    Rectangle2D bounds = GraphConstants.getBounds ( this.getAttributes () );
    if ( bounds == null )
    {
      return POSITION_NOT_DEFINED;
    }
    return bounds.getX ();
  }


  /**
   * Move this {@link DefaultStateView}.
   * 
   * @param x The new x value.
   * @param y The new y value.
   */
  public void move ( double x, double y )
  {
    this.ignoreStateMove = true;
    Rectangle2D bounds = GraphConstants.getBounds ( this.getAttributes () );
    bounds.setRect ( x, y, bounds.getWidth (), bounds.getHeight () );
    GraphConstants.setBounds ( this.getAttributes (), bounds );
  }


  /**
   * Returns the y position.
   * 
   * @return The y position.
   */
  public final double getYPosition ()
  {
    Rectangle2D bounds = GraphConstants.getBounds ( this.getAttributes () );
    if ( bounds == null )
    {
      return POSITION_NOT_DEFINED;
    }
    return bounds.getY ();
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


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public final boolean isModified ()
  {
    return ( this.initialXPosition != getXPosition () )
        || ( this.initialYPosition != getYPosition () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#removeModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.remove ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#resetModify()
   */
  public final void resetModify ()
  {
    this.initialXPosition = getXPosition ();
    this.initialYPosition = getYPosition ();
  }
}
