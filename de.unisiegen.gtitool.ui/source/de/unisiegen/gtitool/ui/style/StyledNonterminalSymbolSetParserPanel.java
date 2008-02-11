package de.unisiegen.gtitool.ui.style;


import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.NonterminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.parser.nonterminalsymbolset.NonterminalSymbolSetParseable;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link NonterminalSymbolSet} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledNonterminalSymbolSetParserPanel.java 532 2008-02-04
 *          23:54:55Z fehler $
 */
public final class StyledNonterminalSymbolSetParserPanel extends
    StyledParserPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -349502367082489237L;


  /**
   * Allocates a new {@link StyledNonterminalSymbolSetParserPanel}.
   */
  public StyledNonterminalSymbolSetParserPanel ()
  {
    super ( new NonterminalSymbolSetParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object newObject )
      {
        fireNonterminalSymbolSetChanged ( ( NonterminalSymbolSet ) newObject );
      }
    } );
  }


  /**
   * Adds the given {@link NonterminalSymbolSetChangedListener}.
   * 
   * @param listener The {@link NonterminalSymbolSetChangedListener}.
   */
  public final synchronized void addNonterminalSymbolSetChangedListener (
      NonterminalSymbolSetChangedListener listener )
  {
    this.listenerList
        .add ( NonterminalSymbolSetChangedListener.class, listener );
  }


  /**
   * Let the listeners know that the {@link NonterminalSymbolSet} has changed.
   * 
   * @param newNonterminalSymbolSet The new {@link NonterminalSymbolSet}.
   */
  private final void fireNonterminalSymbolSetChanged (
      NonterminalSymbolSet newNonterminalSymbolSet )
  {
    NonterminalSymbolSetChangedListener [] listeners = this.listenerList
        .getListeners ( NonterminalSymbolSetChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].nonterminalSymbolSetChanged ( newNonterminalSymbolSet );
    }
  }


  /**
   * Returns the {@link NonterminalSymbolSet} for the program text within the
   * document.
   * 
   * @return The {@link NonterminalSymbolSet} for the program text.
   */
  public final NonterminalSymbolSet getNonterminalSymbolSet ()
  {
    try
    {
      return ( NonterminalSymbolSet ) getParsedObject ();
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
  public final NonterminalSymbolSet parse ()
  {
    return ( NonterminalSymbolSet ) super.parse ();
  }


  /**
   * Removes the given {@link NonterminalSymbolSetChangedListener}.
   * 
   * @param listener The {@link NonterminalSymbolSetChangedListener}.
   */
  public final synchronized void removeNonterminalSymbolSetChangedListener (
      NonterminalSymbolSetChangedListener listener )
  {
    this.listenerList.remove ( NonterminalSymbolSetChangedListener.class,
        listener );
  }


  /**
   * Sets the {@link NonterminalSymbol}s which should be highlighted.
   * 
   * @param nonterminalSymbols The {@link NonterminalSymbol}s which should be
   *          highlighted.
   */
  public final void setHighlightedNonterminalSymbol (
      Iterable < NonterminalSymbol > nonterminalSymbols )
  {
    setHighlightedParseableEntity ( nonterminalSymbols );
  }


  /**
   * Sets the {@link NonterminalSymbol}s which should be highlighted.
   * 
   * @param nonterminalSymbols The {@link NonterminalSymbol}s which should be
   *          highlighted.
   */
  public final void setHighlightedNonterminalSymbol (
      NonterminalSymbol ... nonterminalSymbols )
  {
    Entity [] entities = new Entity [ nonterminalSymbols.length ];
    for ( int i = 0 ; i < nonterminalSymbols.length ; i++ )
    {
      entities [ i ] = nonterminalSymbols [ i ];
    }
    setHighlightedParseableEntity ( entities );
  }


  /**
   * Sets the {@link NonterminalSymbol} which should be highlighted.
   * 
   * @param nonterminalSymbol The {@link NonterminalSymbol} which should be
   *          highlighted.
   */
  public final void setHighlightedNonterminalSymbol (
      NonterminalSymbol nonterminalSymbol )
  {
    setHighlightedParseableEntity ( nonterminalSymbol );
  }


  /**
   * Sets the {@link NonterminalSymbolSet} of the document.
   * 
   * @param nonterminalSymbolSet The input {@link NonterminalSymbolSet}.
   */
  public final void setNonterminalSymbolSet (
      NonterminalSymbolSet nonterminalSymbolSet )
  {
    getEditor ().setText ( nonterminalSymbolSet.toString () );
  }
}
