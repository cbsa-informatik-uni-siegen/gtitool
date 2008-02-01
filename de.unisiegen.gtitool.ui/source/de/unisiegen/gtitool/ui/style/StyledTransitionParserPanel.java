package de.unisiegen.gtitool.ui.style;


import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.listener.TransitionChangedListener;
import de.unisiegen.gtitool.core.parser.transition.TransitionParseable;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
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
   * Allocates a new <code>StyledTransitionParserPanel</code>.
   */
  public StyledTransitionParserPanel ()
  {
    super ( new TransitionParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object newObject )
      {
        fireTransitionChanged ( ( Transition ) newObject );
      }
    } );
  }


  /**
   * Adds the given {@link TransitionChangedListener}.
   * 
   * @param listener The {@link TransitionChangedListener}.
   */
  public final synchronized void addTransitionChangedListener (
      TransitionChangedListener listener )
  {
    this.listenerList.add ( TransitionChangedListener.class, listener );
  }


  /**
   * Let the listeners know that the {@link Transition} has changed.
   * 
   * @param newTransition The new {@link Transition}.
   */
  private final void fireTransitionChanged ( Transition newTransition )
  {
    TransitionChangedListener [] listeners = this.listenerList
        .getListeners ( TransitionChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].transitionChanged ( newTransition );
    }
  }


  /**
   * Returns the {@link Transition} for the program text within the document.
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
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#parse()
   */
  @Override
  public final Transition parse ()
  {
    return ( Transition ) super.parse ();
  }


  /**
   * Removes the given {@link TransitionChangedListener}.
   * 
   * @param listener The {@link TransitionChangedListener}.
   */
  public final synchronized void removeTransitionChangedListener (
      TransitionChangedListener listener )
  {
    this.listenerList.remove ( TransitionChangedListener.class, listener );
  }


  /**
   * Sets the {@link Symbol}s which should be highlighted.
   * 
   * @param symbols The {@link Symbol}s which should be highlighted.
   */
  public final void setHighlightedSymbol ( Iterable < Symbol > symbols )
  {
    setHighlightedParseableEntity ( symbols );
  }


  /**
   * Sets the {@link Symbol}s which should be highlighted.
   * 
   * @param symbols The {@link Symbol}s which should be highlighted.
   */
  public final void setHighlightedSymbol ( Symbol ... symbols )
  {
    Entity [] entities = new Entity [ symbols.length ];
    for ( int i = 0 ; i < symbols.length ; i++ )
    {
      entities [ i ] = symbols [ i ];
    }
    setHighlightedParseableEntity ( entities );
  }


  /**
   * Sets the {@link Symbol} which should be highlighted.
   * 
   * @param symbol The {@link Symbol} which should be highlighted.
   */
  public final void setHighlightedSymbol ( Symbol symbol )
  {
    setHighlightedParseableEntity ( symbol );
  }


  /**
   * Sets the {@link Transition} of the document.
   * 
   * @param transition The input {@link Transition}.
   */
  public final void setTransition ( Transition transition )
  {
    if ( transition == null )
    {
      getEditor ().setText ( "" ); //$NON-NLS-1$
    }
    else
    {
      getEditor ().setText ( transition.toString () );
    }
  }
}
