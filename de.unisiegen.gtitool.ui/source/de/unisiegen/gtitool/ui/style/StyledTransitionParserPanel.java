package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.parser.transition.TransitionParseable;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.listener.TransitionChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link Transition} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledTransitionParserPanel.java 225 2007-11-22 16:55:02Z
 *          fehler $
 */
public final class StyledTransitionParserPanel extends StyledParserPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6870722718951231990L;


  /**
   * The list of {@link TransitionChangedListener}.
   */
  private ArrayList < TransitionChangedListener > transitionChangedListenerList = new ArrayList < TransitionChangedListener > ();


  /**
   * The {@link TransitionChangedListener} for the other
   * <code>StyledTransitionParserPanel</code>.
   */
  private TransitionChangedListener transitionChangedListenerOther;


  /**
   * The {@link TransitionChangedListener} for this
   * <code>StyledTransitionParserPanel</code>.
   */
  private TransitionChangedListener transitionChangedListenerThis;


  /**
   * Allocates a new <code>StyledTransitionParserPanel</code>.
   */
  public StyledTransitionParserPanel ()
  {
    super ( new TransitionParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object pNewObject )
      {
        fireTransitionChanged ( ( Transition ) pNewObject );
      }
    } );
  }


  /**
   * Adds the given {@link TransitionChangedListener}.
   * 
   * @param pListener The {@link TransitionChangedListener}.
   */
  public final synchronized void addTransitionChangedListener (
      TransitionChangedListener pListener )
  {
    this.transitionChangedListenerList.add ( pListener );
  }


  /**
   * Let the listeners know that the {@link Transition} has changed.
   * 
   * @param pNewTransition The new {@link Transition}.
   */
  private final void fireTransitionChanged ( Transition pNewTransition )
  {
    for ( TransitionChangedListener current : this.transitionChangedListenerList )
    {
      current.transitionChanged ( pNewTransition );
    }
  }


  /**
   * Returns the {@link Transition} for the program text within the document.
   * Throws an exception if a parsing error occurred.
   * 
   * @return The {@link Transition} for the program text.
   */
  public final Transition getTransition ()
  {
    try
    {
      return ( Transition ) getParsedObject ();
    }
    catch ( Exception exc )
    {
      return null;
    }
  }


  /**
   * Removes the given {@link TransitionChangedListener}.
   * 
   * @param pListener The {@link TransitionChangedListener}.
   * @return <tt>true</tt> if the list contained the specified element.
   */
  public final synchronized boolean removeTransitionChangedListener (
      TransitionChangedListener pListener )
  {
    return this.transitionChangedListenerList.remove ( pListener );
  }


  /**
   * Sets the {@link Transition} of the document.
   * 
   * @param pTransition The input {@link Transition}.
   */
  public final void setTransition ( Transition pTransition )
  {
    getEditor ().setText ( pTransition.toString () );
  }


  /**
   * Synchronizes this <code>StyledTransitionParserPanel</code> with the given
   * <code>StyledTransitionParserPanel</code>.
   * 
   * @param pStyledTransitionParserPanel The other
   *          <code>StyledTransitionParserPanel</code> which should be
   *          synchronized.
   */
  public final void synchronize (
      final StyledTransitionParserPanel pStyledTransitionParserPanel )
  {
    this.transitionChangedListenerOther = new TransitionChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void transitionChanged ( @SuppressWarnings ( "unused" )
      Transition pNewTransition )
      {
        removeTransitionChangedListener ( StyledTransitionParserPanel.this.transitionChangedListenerThis );
        getEditor ().setText (
            pStyledTransitionParserPanel.getEditor ().getText () );
        addTransitionChangedListener ( StyledTransitionParserPanel.this.transitionChangedListenerThis );
      }
    };
    this.transitionChangedListenerThis = new TransitionChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void transitionChanged ( @SuppressWarnings ( "unused" )
      Transition pNewTransition )
      {
        pStyledTransitionParserPanel
            .removeTransitionChangedListener ( StyledTransitionParserPanel.this.transitionChangedListenerOther );
        pStyledTransitionParserPanel.getEditor ().setText (
            getEditor ().getText () );
        pStyledTransitionParserPanel
            .addTransitionChangedListener ( StyledTransitionParserPanel.this.transitionChangedListenerOther );
      }
    };
    pStyledTransitionParserPanel
        .addTransitionChangedListener ( this.transitionChangedListenerOther );
    addTransitionChangedListener ( this.transitionChangedListenerThis );
  }
}
