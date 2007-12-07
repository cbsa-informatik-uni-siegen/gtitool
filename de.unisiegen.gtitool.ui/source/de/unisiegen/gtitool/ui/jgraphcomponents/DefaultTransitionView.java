package de.unisiegen.gtitool.ui.jgraphcomponents;


import java.util.ArrayList;

import org.jgraph.graph.DefaultEdge;

import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreWarningException;


/**
 * This class represents the {@link Transition} in the gui
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class DefaultTransitionView extends DefaultEdge implements Storable
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -3862888215606281756L;


  /**
   * The {@link Transition} represented by this view
   */
  private Transition transition;
  
  /** The source view of this transition  */
  private DefaultStateView sourceView;
  
  /** The target view of this transition */
  private DefaultStateView targetView;


  /**
   * Create a new {@link DefaultTransitionView}
   * 
   * @param pTransition The {@link Transition} represented by this view
   * @param pSourceView the source view
   * @param pTargetView the target view
   * @param pUserObject The name of this Transition
   */
  public DefaultTransitionView ( Transition pTransition, DefaultStateView pSourceView, DefaultStateView pTargetView, Object pUserObject )
  {
    super ( pUserObject );
    this.transition = pTransition;
    this.sourceView = pSourceView;
    this.targetView = pTargetView;
  }
  
  /**
   * Getter for this {@link Transition}
   * 
   * @return the {@link Transition} of this view
   */
  public Transition getTransition ()
  {
    return this.transition;
  }
  
  /**
   * 
   * Getter for the source view
   *
   * @return the source {@link DefaultStateView}
   */
  public DefaultStateView getSourceView(){
    return this.sourceView;
  }
  
  /**
   * 
   * Getter for the target view
   *
   * @return the target {@link DefaultStateView}
   */
  public DefaultStateView getTargetView(){
    return this.targetView;
  }
  
  /**
   * {@inheritDoc}
   * 
   * @see Storable#getElement()
   */
  public final Element getElement ()
  {
    Element newElement = new Element ( "DefaultTransitionView"); //$NON-NLS-1$
    newElement.addElement ( this.transition.getElement () );
    return newElement;
  }


  public ArrayList < StoreWarningException > getWarning ()
  {
    // TODO implement me
    return null;
  }


  public StoreWarningException getWarning ( int pIndex )
  {
    // TODO implement me
    return null;
  }

}
