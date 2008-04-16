package de.unisiegen.gtitool.ui.style;


import de.unisiegen.gtitool.core.entities.Entity;
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
public final class StyledTerminalSymbolParserPanel extends
    StyledParserPanel < TerminalSymbol >
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
    super
        .addParseableChangedListener ( new ParseableChangedListener < TerminalSymbol > ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void parseableChanged ( TerminalSymbol newTerminalSymbol )
          {
            fireTerminalSymbolChanged ( newTerminalSymbol );
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
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final TerminalSymbol checkParsedObject (
      TerminalSymbol terminalSymbol )
  {
    return terminalSymbol;
  }


  /**
   * Let the listeners know that the {@link TerminalSymbol} has changed.
   * 
   * @param newTerminalSymbol The new {@link TerminalSymbol}.
   */
  private final void fireTerminalSymbolChanged (
      TerminalSymbol newTerminalSymbol )
  {
    TerminalSymbol checkedTerminalSymbol = checkParsedObject ( newTerminalSymbol );
    TerminalSymbolChangedListener [] listeners = this.listenerList
        .getListeners ( TerminalSymbolChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].terminalSymbolChanged ( checkedTerminalSymbol );
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
    TerminalSymbol terminalSymbol = ( TerminalSymbol ) super.parse ();
    return checkParsedObject ( terminalSymbol );
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
}
