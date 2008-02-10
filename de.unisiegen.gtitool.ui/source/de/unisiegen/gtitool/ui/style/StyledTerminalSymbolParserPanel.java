package de.unisiegen.gtitool.ui.style;


import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.listener.TerminalSymbolChangedListener;
import de.unisiegen.gtitool.core.parser.terminalsymbol.TerminalSymbolParseable;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link TerminalSymbol} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledTerminalSymbolParserPanel.java 532 2008-02-04 23:54:55Z
 *          fehler $
 */
public final class StyledTerminalSymbolParserPanel extends StyledParserPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6955506932808118867L;


  /**
   * Allocates a new {@link StyledTerminalSymbolParserPanel}.
   */
  public StyledTerminalSymbolParserPanel ()
  {
    super ( new TerminalSymbolParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object newObject )
      {
        fireTerminalSymbolChanged ( ( TerminalSymbol ) newObject );
      }
    } );
  }


  /**
   * Adds the given {@link TerminalSymbolChangedListener}.
   * 
   * @param listener The {@link TerminalSymbolChangedListener}.
   */
  public final synchronized void addTerminalSymbolChangedListener (
      TerminalSymbolChangedListener listener )
  {
    this.listenerList.add ( TerminalSymbolChangedListener.class, listener );
  }


  /**
   * Let the listeners know that the {@link TerminalSymbol} has changed.
   * 
   * @param newTerminalSymbol The new {@link TerminalSymbol}.
   */
  private final void fireTerminalSymbolChanged (
      TerminalSymbol newTerminalSymbol )
  {
    TerminalSymbolChangedListener [] listeners = this.listenerList
        .getListeners ( TerminalSymbolChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].terminalSymbolChanged ( newTerminalSymbol );
    }
  }


  /**
   * Returns the {@link TerminalSymbol} for the program text within the
   * document.
   * 
   * @return The {@link TerminalSymbol} for the program text.
   */
  public final TerminalSymbol getTerminalSymbol ()
  {
    try
    {
      return ( TerminalSymbol ) getParsedObject ();
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
  public final TerminalSymbol parse ()
  {
    return ( TerminalSymbol ) super.parse ();
  }


  /**
   * Removes the given {@link TerminalSymbolChangedListener}.
   * 
   * @param listener The {@link TerminalSymbolChangedListener}.
   */
  public final synchronized void removeTerminalSymbolChangedListener (
      TerminalSymbolChangedListener listener )
  {
    this.listenerList.remove ( TerminalSymbolChangedListener.class, listener );
  }


  /**
   * Sets the {@link TerminalSymbol} of the document.
   * 
   * @param terminalSymbol The input {@link TerminalSymbol}.
   */
  public final void setTerminalSymbol ( TerminalSymbol terminalSymbol )
  {
    getEditor ().setText ( terminalSymbol.toString () );
  }
}
