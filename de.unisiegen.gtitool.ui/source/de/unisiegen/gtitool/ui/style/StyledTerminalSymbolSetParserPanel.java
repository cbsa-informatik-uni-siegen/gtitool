package de.unisiegen.gtitool.ui.style;


import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.TerminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.parser.terminalsymbolset.TerminalSymbolSetParseable;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link TerminalSymbolSet} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledTerminalSymbolSetParserPanel.java 532 2008-02-04
 *          23:54:55Z fehler $
 */
public final class StyledTerminalSymbolSetParserPanel extends StyledParserPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -928492541925565704L;


  /**
   * Allocates a new {@link StyledTerminalSymbolSetParserPanel}.
   */
  public StyledTerminalSymbolSetParserPanel ()
  {
    super ( new TerminalSymbolSetParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object newObject )
      {
        fireTerminalSymbolSetChanged ( ( TerminalSymbolSet ) newObject );
      }
    } );
  }


  /**
   * Adds the given {@link TerminalSymbolSetChangedListener}.
   * 
   * @param listener The {@link TerminalSymbolSetChangedListener}.
   */
  public final synchronized void addTerminalSymbolSetChangedListener (
      TerminalSymbolSetChangedListener listener )
  {
    this.listenerList.add ( TerminalSymbolSetChangedListener.class, listener );
  }


  /**
   * Let the listeners know that the {@link TerminalSymbolSet} has changed.
   * 
   * @param newTerminalSymbolSet The new {@link TerminalSymbolSet}.
   */
  private final void fireTerminalSymbolSetChanged (
      TerminalSymbolSet newTerminalSymbolSet )
  {
    TerminalSymbolSetChangedListener [] listeners = this.listenerList
        .getListeners ( TerminalSymbolSetChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].terminalSymbolSetChanged ( newTerminalSymbolSet );
    }
  }


  /**
   * Returns the {@link TerminalSymbolSet} for the program text within the
   * document.
   * 
   * @return The {@link TerminalSymbolSet} for the program text.
   */
  public final TerminalSymbolSet getTerminalSymbolSet ()
  {
    try
    {
      return ( TerminalSymbolSet ) getParsedObject ();
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
  public final TerminalSymbolSet parse ()
  {
    return ( TerminalSymbolSet ) super.parse ();
  }


  /**
   * Removes the given {@link TerminalSymbolSetChangedListener}.
   * 
   * @param listener The {@link TerminalSymbolSetChangedListener}.
   */
  public final synchronized void removeTerminalSymbolSetChangedListener (
      TerminalSymbolSetChangedListener listener )
  {
    this.listenerList
        .remove ( TerminalSymbolSetChangedListener.class, listener );
  }


  /**
   * Sets the {@link TerminalSymbol}s which should be highlighted.
   * 
   * @param terminalSymbols The {@link TerminalSymbol}s which should be
   *          highlighted.
   */
  public final void setHighlightedTerminalSymbol (
      Iterable < TerminalSymbol > terminalSymbols )
  {
    setHighlightedParseableEntity ( terminalSymbols );
  }


  /**
   * Sets the {@link TerminalSymbol}s which should be highlighted.
   * 
   * @param terminalSymbols The {@link TerminalSymbol}s which should be
   *          highlighted.
   */
  public final void setHighlightedTerminalSymbol (
      TerminalSymbol ... terminalSymbols )
  {
    Entity [] entities = new Entity [ terminalSymbols.length ];
    for ( int i = 0 ; i < terminalSymbols.length ; i++ )
    {
      entities [ i ] = terminalSymbols [ i ];
    }
    setHighlightedParseableEntity ( entities );
  }


  /**
   * Sets the {@link TerminalSymbol} which should be highlighted.
   * 
   * @param terminalSymbol The {@link TerminalSymbol} which should be
   *          highlighted.
   */
  public final void setHighlightedTerminalSymbol ( TerminalSymbol terminalSymbol )
  {
    setHighlightedParseableEntity ( terminalSymbol );
  }


  /**
   * Sets the {@link TerminalSymbolSet} of the document.
   * 
   * @param terminalSymbolSet The input {@link TerminalSymbolSet}.
   */
  public final void setTerminalSymbolSet ( TerminalSymbolSet terminalSymbolSet )
  {
    getEditor ().setText ( terminalSymbolSet.toString () );
  }
}
