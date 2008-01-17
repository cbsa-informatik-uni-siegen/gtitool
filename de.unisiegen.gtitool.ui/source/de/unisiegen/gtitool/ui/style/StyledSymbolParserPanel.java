package de.unisiegen.gtitool.ui.style;


import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.listener.SymbolChangedListener;
import de.unisiegen.gtitool.core.parser.symbol.SymbolParseable;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link Symbol} panel class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StyledSymbolParserPanel extends StyledParserPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8301131779540090916L;


  /**
   * Allocates a new <code>StyledSymbolParserPanel</code>.
   */
  public StyledSymbolParserPanel ()
  {
    super ( new SymbolParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object newObject )
      {
        fireSymbolChanged ( ( Symbol ) newObject );
      }
    } );
  }


  /**
   * Adds the given {@link SymbolChangedListener}.
   * 
   * @param listener The {@link SymbolChangedListener}.
   */
  public final synchronized void addSymbolChangedListener (
      SymbolChangedListener listener )
  {
    this.listenerList.add ( SymbolChangedListener.class, listener );
  }


  /**
   * Let the listeners know that the {@link Symbol} has changed.
   * 
   * @param newSymbol The new {@link Symbol}.
   */
  private final void fireSymbolChanged ( Symbol newSymbol )
  {
    SymbolChangedListener [] listeners = this.listenerList
        .getListeners ( SymbolChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].symbolChanged ( newSymbol );
    }
  }


  /**
   * Returns the {@link Symbol} for the program text within the document.
   * 
   * @return The {@link Symbol} for the program text.
   */
  public final Symbol getSymbol ()
  {
    try
    {
      return ( Symbol ) getParsedObject ();
    }
    catch ( Exception exc )
    {
      return null;
    }
  }


  /**
   * Removes the given {@link SymbolChangedListener}.
   * 
   * @param listener The {@link SymbolChangedListener}.
   */
  public final synchronized void removeSymbolChangedListener (
      SymbolChangedListener listener )
  {
    this.listenerList.remove ( SymbolChangedListener.class, listener );
  }


  /**
   * Sets the {@link Symbol} of the document.
   * 
   * @param symbol The input {@link Symbol}.
   */
  public final void setSymbol ( Symbol symbol )
  {
    getEditor ().setText ( symbol.toString () );
  }
}
