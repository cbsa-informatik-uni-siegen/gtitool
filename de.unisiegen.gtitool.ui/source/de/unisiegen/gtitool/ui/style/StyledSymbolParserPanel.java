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
   * The {@link SymbolChangedListener} for the other
   * <code>StyledSymbolParserPanel</code>.
   */
  private SymbolChangedListener symbolChangedListenerOther;


  /**
   * The {@link SymbolChangedListener} for this
   * <code>StyledSymbolParserPanel</code>.
   */
  private SymbolChangedListener symbolChangedListenerThis;


  /**
   * Allocates a new <code>StyledSymbolParserPanel</code>.
   */
  public StyledSymbolParserPanel ()
  {
    super ( new SymbolParseable () );
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


  /**
   * Synchronizes this <code>StyledSymbolParserPanel</code> with the given
   * <code>StyledSymbolParserPanel</code>.
   * 
   * @param pStyledSymbolParserPanel The other
   *          <code>StyledSymbolParserPanel</code> which should be
   *          synchronized.
   */
  public final void synchronize (
      final StyledSymbolParserPanel pStyledSymbolParserPanel )
  {
    this.symbolChangedListenerOther = new SymbolChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void symbolChanged ( @SuppressWarnings ( "unused" )
      Symbol pNewSymbol )
      {
        removeSymbolChangedListener ( StyledSymbolParserPanel.this.symbolChangedListenerThis );
        getEditor ()
            .setText ( pStyledSymbolParserPanel.getEditor ().getText () );
        addSymbolChangedListener ( StyledSymbolParserPanel.this.symbolChangedListenerThis );
      }
    };
    this.symbolChangedListenerThis = new SymbolChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void symbolChanged ( @SuppressWarnings ( "unused" )
      Symbol pNewSymbol )
      {
        pStyledSymbolParserPanel
            .removeSymbolChangedListener ( StyledSymbolParserPanel.this.symbolChangedListenerOther );
        pStyledSymbolParserPanel.getEditor ()
            .setText ( getEditor ().getText () );
        pStyledSymbolParserPanel
            .addSymbolChangedListener ( StyledSymbolParserPanel.this.symbolChangedListenerOther );
      }
    };
    pStyledSymbolParserPanel
        .addSymbolChangedListener ( this.symbolChangedListenerOther );
    addSymbolChangedListener ( this.symbolChangedListenerThis );
  }
}
