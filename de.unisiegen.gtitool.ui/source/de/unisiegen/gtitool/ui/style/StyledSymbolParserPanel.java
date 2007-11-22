package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.parser.symbol.SymbolParseable;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.listener.SymbolChangedListener;
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
   * The list of {@link SymbolChangedListener}.
   */
  private ArrayList < SymbolChangedListener > symbolChangedListenerList = new ArrayList < SymbolChangedListener > ();


  /**
   * Allocates a new <code>StyledSymbolParserPanel</code>.
   */
  public StyledSymbolParserPanel ()
  {
    super ( false, new SymbolParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object pNewObject )
      {
        fireSymbolChanged ( ( Symbol ) pNewObject );
      }
    } );
  }


  /**
   * Allocates a new <code>StyledSymbolParserPanel</code>.
   * 
   * @param pReadOnly The read only value.
   */
  public StyledSymbolParserPanel ( boolean pReadOnly )
  {
    this ();
    setReadOnly ( pReadOnly );
  }


  /**
   * Adds the given {@link SymbolChangedListener}.
   * 
   * @param pListener The {@link SymbolChangedListener}.
   */
  public final synchronized void addSymbolChangedListener (
      SymbolChangedListener pListener )
  {
    this.symbolChangedListenerList.add ( pListener );
  }


  /**
   * Let the listeners know that the {@link Symbol} has changed.
   * 
   * @param pNewSymbol The new {@link Symbol}.
   */
  private final void fireSymbolChanged ( Symbol pNewSymbol )
  {
    for ( SymbolChangedListener current : this.symbolChangedListenerList )
    {
      current.symbolChanged ( pNewSymbol );
    }
  }


  /**
   * Returns the {@link Symbol} for the program text within the document. Throws
   * an exception if a parsing error occurred.
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
   * @param pListener The {@link SymbolChangedListener}.
   * @return <tt>true</tt> if the list contained the specified element.
   */
  public final synchronized boolean removeSymbolChangedListener (
      SymbolChangedListener pListener )
  {
    return this.symbolChangedListenerList.remove ( pListener );
  }


  /**
   * Sets the {@link Symbol} of the document.
   * 
   * @param pSymbol The input {@link Symbol}.
   */
  public final void setSymbol ( Symbol pSymbol )
  {
    getEditor ().setText ( pSymbol.toString () );
  }
}
