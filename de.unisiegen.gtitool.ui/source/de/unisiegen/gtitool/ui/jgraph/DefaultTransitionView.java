package de.unisiegen.gtitool.ui.jgraph;


import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * This class represents the {@link Transition} in the gui.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class DefaultTransitionView extends DefaultEdge implements
    Storable
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -3862888215606281756L;


  /**
   * The {@link Transition} represented by this view
   */
  private Transition transition;


  /**
   * The source view of this {@link Transition}.
   */
  private DefaultStateView sourceView;


  /**
   * The target view of this {@link Transition}.
   */
  private DefaultStateView targetView;


  /**
   * Create a new {@link DefaultTransitionView}
   * 
   * @param transition The {@link Transition} represented by this view.
   * @param sourceView The source view.
   * @param targetView The target view.
   */
  public DefaultTransitionView ( Transition transition,
      DefaultStateView sourceView, DefaultStateView targetView )
  {
    super ( transition );
    this.transition = transition;
    this.sourceView = sourceView;
    this.targetView = targetView;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof DefaultTransitionView )
    {
      DefaultTransitionView transitionView = ( DefaultTransitionView ) other;
      return this.transition.equals ( transitionView.getTransition () );
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
    Element newElement = new Element ( "TransitionView" ); //$NON-NLS-1$
    newElement.addElement ( this.transition.getElement () );
    return newElement;
  }


  /**
   * Getter for the source view.
   * 
   * @return The source {@link DefaultStateView}.
   */
  public final DefaultStateView getSourceView ()
  {
    return this.sourceView;
  }


  /**
   * Getter for the target view.
   * 
   * @return The target {@link DefaultStateView}.
   */
  public final DefaultStateView getTargetView ()
  {
    return this.targetView;
  }


  /**
   * Returns the tooltip text for this cell.
   * 
   * @return the tooltip text for this cell.
   */
  public String getToolTipString ()
  {
    return null;
  }


  /**
   * Getter for this {@link Transition}.
   * 
   * @return The {@link Transition} of this view.
   */
  public final Transition getTransition ()
  {
    return this.transition;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.transition.hashCode ();
  }
}
