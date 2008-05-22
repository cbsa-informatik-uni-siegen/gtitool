package de.unisiegen.gtitool.ui.jgraph;


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
 * @version $Id:DefaultStateView.java 910 2008-05-16 00:31:21Z fehler $
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
   * The width is not defined value.
   */
  public static final double WIDTH_NOT_DEFINED = Double.MIN_VALUE;


  /**
   * The height is not defined value.
   */
  public static final double HEIGHT_NOT_DEFINED = Double.MIN_VALUE;


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
    double x = getPositionX ();
    double y = getPositionY ();
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
    double x = getPositionX ();
    double y = getPositionY ();

    StatePositionChangedListener [] listeners = this.listenerList
        .getListeners ( StatePositionChangedListener.class );

    if ( ( this.xValue != x ) || ( this.yValue != y ) )
    {

      if ( this.ignoreStateMove )
      {
        this.ignoreStateMove = false;
        this.xValue = x;
        this.yValue = y;
        return;
      }

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
    Rectangle2D bounds = GraphConstants.getBounds ( getAttributes () );
    Element newElement = new Element ( "StateView" ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "x", bounds.getX () ) ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "y", bounds.getY () ) ); //$NON-NLS-1$
    newElement.addElement ( this.state.getElement () );
    return newElement;
  }


  /**
   * Returns the height.
   * 
   * @return The height.
   */
  public final double getHeight ()
  {
    Rectangle2D bounds = GraphConstants.getBounds ( getAttributes () );
    if ( bounds == null )
    {
      return HEIGHT_NOT_DEFINED;
    }
    return bounds.getHeight ();
  }


  /**
   * Returns the x position.
   * 
   * @return The x position.
   */
  public final double getPositionX ()
  {
    Rectangle2D bounds = GraphConstants.getBounds ( getAttributes () );
    if ( bounds == null )
    {
      return POSITION_NOT_DEFINED;
    }
    return bounds.getX ();
  }


  /**
   * Returns the y position.
   * 
   * @return The y position.
   */
  public final double getPositionY ()
  {
    Rectangle2D bounds = GraphConstants.getBounds ( getAttributes () );
    if ( bounds == null )
    {
      return POSITION_NOT_DEFINED;
    }
    return bounds.getY ();
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
   * Returns the width.
   * 
   * @return The width.
   */
  public final double getWidth ()
  {
    Rectangle2D bounds = GraphConstants.getBounds ( getAttributes () );
    if ( bounds == null )
    {
      return WIDTH_NOT_DEFINED;
    }
    return bounds.getWidth ();
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
    return ( this.initialXPosition != getPositionX () )
        || ( this.initialYPosition != getPositionY () );
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

    Rectangle2D bounds = GraphConstants.getBounds ( getAttributes () );
    bounds.setRect ( x, y, bounds.getWidth (), bounds.getHeight () );
    GraphConstants.setBounds ( getAttributes (), bounds );
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
    this.initialXPosition = getPositionX ();
    this.initialYPosition = getPositionY ();
  }
}
