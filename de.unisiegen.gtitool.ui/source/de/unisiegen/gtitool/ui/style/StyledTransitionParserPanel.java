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
public final class StyledTransitionParserPanel extends
    StyledParserPanel < Transition >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6870722718951231990L;


  /**
   * Allocates a new {@link StyledTransitionParserPanel}.
   */
  public StyledTransitionParserPanel ()
  {
    super ( new TransitionParseable () );
    super
        .addParseableChangedListener ( new ParseableChangedListener < Transition > ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void parseableChanged ( Transition newTransition )
          {
            fireTransitionChanged ( newTransition );
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
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final Transition checkParsedObject ( Transition transition )
  {
    return transition;
  }


  /**
   * Let the listeners know that the {@link Transition} has changed.
   * 
   * @param newTransition The new {@link Transition}.
   */
  private final void fireTransitionChanged ( Transition newTransition )
  {
    Transition checkedTransition = checkParsedObject ( newTransition );
    TransitionChangedListener [] listeners = this.listenerList
        .getListeners ( TransitionChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].transitionChanged ( checkedTransition );
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
    Transition transition = ( Transition ) super.parse ();
    return checkParsedObject ( transition );
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
}
