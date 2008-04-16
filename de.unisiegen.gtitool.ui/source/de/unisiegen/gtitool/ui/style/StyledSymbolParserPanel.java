package de.unisiegen.gtitool.ui.style;


import de.unisiegen.gtitool.core.entities.Entity;
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
public final class StyledSymbolParserPanel extends StyledParserPanel < Symbol >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8301131779540090916L;


  /**
   * Allocates a new {@link StyledSymbolParserPanel}.
   */
  public StyledSymbolParserPanel ()
  {
    super ( new SymbolParseable () );
    super
        .addParseableChangedListener ( new ParseableChangedListener < Symbol > ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void parseableChanged ( Symbol newSymbol )
          {
            fireSymbolChanged ( newSymbol );
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
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final Symbol checkParsedObject ( Symbol symbol )
  {
    return symbol;
  }


  /**
   * Let the listeners know that the {@link Symbol} has changed.
   * 
   * @param newSymbol The new {@link Symbol}.
   */
  private final void fireSymbolChanged ( Symbol newSymbol )
  {
    Symbol checkedSymbol = checkParsedObject ( newSymbol );
    SymbolChangedListener [] listeners = this.listenerList
        .getListeners ( SymbolChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].symbolChanged ( checkedSymbol );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#parse()
   */
  @Override
  public final Symbol parse ()
  {
    Symbol symbol = ( Symbol ) super.parse ();
    return checkParsedObject ( symbol );
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
}
