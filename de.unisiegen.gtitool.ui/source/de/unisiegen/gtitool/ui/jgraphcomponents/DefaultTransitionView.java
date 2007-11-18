package de.unisiegen.gtitool.ui.jgraphcomponents;


import org.jgraph.graph.DefaultEdge;

import de.unisiegen.gtitool.core.entities.Transition;


/**
 * TODO
 * 
 * @author Benjamin Mies
 * @version $Id: NewDialog.java 119 2007-11-10 12:07:30Z fehler
 */
public class DefaultTransitionView extends DefaultEdge
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -3862888215606281756L;


  /**
   * The {@link Transition} represented by this view
   */
  private Transition transition;
  
  private DefaultStateView sourceView;
  
  private DefaultStateView targetView;


  /**
   * Create a new {@link DefaultTransitionView}
   * 
   * @param pTransition The {@link Transition} represented by this view
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
  
  public DefaultStateView getSourceView(){
    return this.sourceView;
  }
  
  public DefaultStateView getTargetView(){
    return this.targetView;
  }

}
