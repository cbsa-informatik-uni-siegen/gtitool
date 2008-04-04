package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.StartNonterminalSymbolChangedListener;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.startnonterminalsymbol.StartNonterminalSymbolParseable;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled start {@link NonterminalSymbol} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledNonterminalSymbolParserPanel.java 532 2008-02-04
 *          23:54:55Z fehler $
 */
public final class StyledStartNonterminalSymbolParserPanel extends
    StyledParserPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 7263996318450114482L;


  /**
   * The parsed {@link NonterminalSymbol} must be in this
   * {@link NonterminalSymbolSet}.
   */
  private NonterminalSymbolSet nonterminalSymbolSet = null;


  /**
   * Allocates a new {@link StyledStartNonterminalSymbolParserPanel}.
   */
  public StyledStartNonterminalSymbolParserPanel ()
  {
    super ( new StartNonterminalSymbolParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object newObject )
      {
        fireStartNonterminalSymbolChanged ( ( NonterminalSymbol ) newObject );
      }
    } );
  }


  /**
   * Adds the given {@link StartNonterminalSymbolChangedListener}.
   * 
   * @param listener The {@link StartNonterminalSymbolChangedListener}.
   */
  public final synchronized void addStartNonterminalSymbolChangedListener (
      StartNonterminalSymbolChangedListener listener )
  {
    this.listenerList.add ( StartNonterminalSymbolChangedListener.class,
        listener );
  }


  /**
   * Checks the given {@link NonterminalSymbol}.
   * 
   * @param nonterminalSymbol The {@link NonterminalSymbol} to check.
   * @return The input {@link NonterminalSymbol} or null, if the
   *         {@link NonterminalSymbol} is not in the
   *         {@link NonterminalSymbolSet}.
   */
  private final NonterminalSymbol checkStartNonterminalSymbol (
      NonterminalSymbol nonterminalSymbol )
  {
    ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();

    if ( ( this.nonterminalSymbolSet != null ) && ( nonterminalSymbol != null ) )
    {
      if ( !this.nonterminalSymbolSet.contains ( nonterminalSymbol ) )
      {
        exceptionList.add ( new ParserException ( nonterminalSymbol
            .getParserOffset ().getStart (), nonterminalSymbol
            .getParserOffset ().getEnd (), Messages.getString (
            "TerminalPanel.StartSymbolNoNonterminalSymbol", //$NON-NLS-1$
            nonterminalSymbol.getName (), this.nonterminalSymbolSet ) ) );
      }
    }

    if ( exceptionList.size () > 0 )
    {
      setException ( exceptionList );
      return null;
    }

    return nonterminalSymbol;
  }


  /**
   * Let the listeners know that the start {@link NonterminalSymbol} has
   * changed.
   * 
   * @param newStartNonterminalSymbol The new start {@link NonterminalSymbol}.
   */
  private final void fireStartNonterminalSymbolChanged (
      NonterminalSymbol newStartNonterminalSymbol )
  {
    NonterminalSymbol checkedStartNonterminalSymbol = checkStartNonterminalSymbol ( newStartNonterminalSymbol );
    StartNonterminalSymbolChangedListener [] listeners = this.listenerList
        .getListeners ( StartNonterminalSymbolChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ]
          .startNonterminalSymbolChanged ( checkedStartNonterminalSymbol );
    }
  }


  /**
   * Returns the start {@link NonterminalSymbol} for the program text within the
   * document.
   * 
   * @return The start {@link NonterminalSymbol} for the program text.
   */
  public final NonterminalSymbol getStartNonterminalSymbol ()
  {
    try
    {
      NonterminalSymbol nonterminalSymbol = ( NonterminalSymbol ) getParsedObject ();
      return checkStartNonterminalSymbol ( nonterminalSymbol );
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
    NonterminalSymbol startNonterminalSymbol = ( NonterminalSymbol ) super
        .parse ();
    return checkStartNonterminalSymbol ( startNonterminalSymbol );
  }


  /**
   * Removes the given {@link StartNonterminalSymbolChangedListener}.
   * 
   * @param listener The {@link StartNonterminalSymbolChangedListener}.
   */
  public final synchronized void removeNonterminalSymbolChangedListener (
      StartNonterminalSymbolChangedListener listener )
  {
    this.listenerList.remove ( StartNonterminalSymbolChangedListener.class,
        listener );
  }


  /**
   * Sets the {@link NonterminalSymbolSet}. The parsed
   * {@link NonterminalSymbol} must be in this {@link NonterminalSymbolSet}.
   * 
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet} to set.
   */
  public final void setNonterminalSymbolSet (
      NonterminalSymbolSet nonterminalSymbolSet )
  {
    this.nonterminalSymbolSet = nonterminalSymbolSet;
  }


  /**
   * Sets the start {@link NonterminalSymbol} of the document.
   * 
   * @param symbol The input start {@link NonterminalSymbol}.
   */
  public final void setText ( NonterminalSymbol symbol )
  {
    getEditor ().setText ( symbol.toString () );
  }
}
