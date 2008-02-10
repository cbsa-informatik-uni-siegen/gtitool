package de.unisiegen.gtitool.ui.style;


import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.listener.NonterminalSymbolChangedListener;
import de.unisiegen.gtitool.core.parser.nonterminalsymbol.NonterminalSymbolParseable;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link NonterminalSymbol} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledNonterminalSymbolParserPanel.java 532 2008-02-04
 *          23:54:55Z fehler $
 */
public final class StyledNonterminalSymbolParserPanel extends StyledParserPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -238479279154469378L;


  /**
   * Allocates a new {@link StyledNonterminalSymbolParserPanel}.
   */
  public StyledNonterminalSymbolParserPanel ()
  {
    super ( new NonterminalSymbolParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object newObject )
      {
        fireNonterminalSymbolChanged ( ( NonterminalSymbol ) newObject );
      }
    } );
  }


  /**
   * Adds the given {@link NonterminalSymbolChangedListener}.
   * 
   * @param listener The {@link NonterminalSymbolChangedListener}.
   */
  public final synchronized void addNonterminalSymbolChangedListener (
      NonterminalSymbolChangedListener listener )
  {
    this.listenerList.add ( NonterminalSymbolChangedListener.class, listener );
  }


  /**
   * Let the listeners know that the {@link NonterminalSymbol} has changed.
   * 
   * @param newNonterminalSymbol The new {@link NonterminalSymbol}.
   */
  private final void fireNonterminalSymbolChanged (
      NonterminalSymbol newNonterminalSymbol )
  {
    NonterminalSymbolChangedListener [] listeners = this.listenerList
        .getListeners ( NonterminalSymbolChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].nonterminalSymbolChanged ( newNonterminalSymbol );
    }
  }


  /**
   * Returns the {@link NonterminalSymbol} for the program text within the
   * document.
   * 
   * @return The {@link NonterminalSymbol} for the program text.
   */
  public final NonterminalSymbol getNonterminalSymbol ()
  {
    try
    {
      return ( NonterminalSymbol ) getParsedObject ();
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
  public final NonterminalSymbol parse ()
  {
    return ( NonterminalSymbol ) super.parse ();
  }


  /**
   * Removes the given {@link NonterminalSymbolChangedListener}.
   * 
   * @param listener The {@link NonterminalSymbolChangedListener}.
   */
  public final synchronized void removeNonterminalSymbolChangedListener (
      NonterminalSymbolChangedListener listener )
  {
    this.listenerList
        .remove ( NonterminalSymbolChangedListener.class, listener );
  }


  /**
   * Sets the {@link NonterminalSymbol} of the document.
   * 
   * @param symbol The input {@link NonterminalSymbol}.
   */
  public final void setNonterminalSymbol ( NonterminalSymbol symbol )
  {
    getEditor ().setText ( symbol.toString () );
  }
}
