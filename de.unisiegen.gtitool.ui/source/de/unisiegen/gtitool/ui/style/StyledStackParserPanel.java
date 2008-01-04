package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.stack.StackParseable;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.listener.StackChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link Stack} panel class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StyledStackParserPanel extends StyledParserPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2677286061045400139L;


  /**
   * The {@link StackChangedListener} for the other
   * <code>StyledStackParserPanel</code>.
   */
  private StackChangedListener stackChangedListenerOther;


  /**
   * The {@link StackChangedListener} for this
   * <code>StyledStackParserPanel</code>.
   */
  private StackChangedListener stackChangedListenerThis;


  /**
   * Every {@link Symbol} in the {@link Stack} has to be in this push down
   * {@link Alphabet}.
   */
  private Alphabet pushDownAlphabet = null;


  /**
   * Allocates a new <code>StyledStackParserPanel</code>.
   */
  public StyledStackParserPanel ()
  {
    super ( new StackParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object pNewObject )
      {
        fireStackChanged ( ( Stack ) pNewObject );
      }
    } );
  }


  /**
   * Adds the given {@link StackChangedListener}.
   * 
   * @param pListener The {@link StackChangedListener}.
   */
  public final synchronized void addStackChangedListener (
      StackChangedListener pListener )
  {
    this.listenerList.add ( StackChangedListener.class, pListener );
  }


  /**
   * Let the listeners know that the {@link Stack} has changed.
   * 
   * @param pNewStack The new {@link Stack}.
   */
  private final void fireStackChanged ( Stack pNewStack )
  {
    if ( ( pNewStack != null ) && ( this.pushDownAlphabet != null ) )
    {
      ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();
      for ( Symbol current : pNewStack )
      {
        if ( !this.pushDownAlphabet.contains ( current ) )
        {
          exceptionList.add ( new ParserException ( current
              .getParserStartOffset (), current.getParserEndOffset (), Messages
              .getString (
                  "StyledStackParserPanel.SymbolNotInPushDownAlphabet", //$NON-NLS-1$
                  current.getName (), this.pushDownAlphabet ) ) );
        }
      }
      if ( exceptionList.size () > 0 )
      {
        getDocument ().setException ( exceptionList );
        StackChangedListener [] listeners = this.listenerList
            .getListeners ( StackChangedListener.class );
        for ( int n = 0 ; n < listeners.length ; ++n )
        {
          listeners [ n ].stackChanged ( null );
        }
        return;
      }
    }
    StackChangedListener [] listeners = this.listenerList
        .getListeners ( StackChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].stackChanged ( pNewStack );
    }
  }


  /**
   * Returns the push down {@link Alphabet}.
   * 
   * @return The push down {@link Alphabet}.
   */
  public final Alphabet getPushDownAlphabet ()
  {
    return this.pushDownAlphabet;
  }


  /**
   * Returns the {@link Stack} for the program text within the document.
   * 
   * @return The {@link Stack} for the program text.
   */
  public final Stack getStack ()
  {
    try
    {
      return ( Stack ) getParsedObject ();
    }
    catch ( Exception exc )
    {
      return null;
    }
  }


  /**
   * Removes the given {@link StackChangedListener}.
   * 
   * @param pListener The {@link StackChangedListener}.
   */
  public final synchronized void removeStackChangedListener (
      StackChangedListener pListener )
  {
    this.listenerList.remove ( StackChangedListener.class, pListener );
  }


  /**
   * Sets the {@link Symbol}s which should be highlighted.
   * 
   * @param pSymbols The {@link Symbol}s which should be highlighted.
   */
  public final void setHighlightedSymbol ( Iterable < Symbol > pSymbols )
  {
    setHighlightedParseableEntity ( pSymbols );
  }


  /**
   * Sets the {@link Symbol} which should be highlighted.
   * 
   * @param pSymbol The {@link Symbol} which should be highlighted.
   */
  public final void setHighlightedSymbol ( Symbol pSymbol )
  {
    setHighlightedParseableEntity ( pSymbol );
  }


  /**
   * Sets the push down {@link Alphabet}. Every {@link Symbol} in the
   * {@link Stack} has to be in the push down {@link Alphabet}.
   * 
   * @param pPushDownAlphabet The push down {@link Alphabet} to set.
   */
  public final void setPushDownAlphabet ( Alphabet pPushDownAlphabet )
  {
    this.pushDownAlphabet = pPushDownAlphabet;
  }


  /**
   * Sets the {@link Stack} of the document.
   * 
   * @param pStack The input {@link Stack}.
   */
  public final void setStack ( Stack pStack )
  {
    getEditor ().setText ( pStack.toString () );
  }


  /**
   * Synchronizes this <code>StyledStackParserPanel</code> with the given
   * <code>StyledStackParserPanel</code>.
   * 
   * @param pStyledStackParserPanel The other
   *          <code>StyledStackParserPanel</code> which should be
   *          synchronized.
   */
  public final void synchronize (
      final StyledStackParserPanel pStyledStackParserPanel )
  {
    this.stackChangedListenerOther = new StackChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void stackChanged ( @SuppressWarnings ( "unused" )
      Stack pNewStack )
      {
        removeStackChangedListener ( StyledStackParserPanel.this.stackChangedListenerThis );
        getEditor ().setText ( pStyledStackParserPanel.getEditor ().getText () );
        addStackChangedListener ( StyledStackParserPanel.this.stackChangedListenerThis );
      }
    };
    this.stackChangedListenerThis = new StackChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void stackChanged ( @SuppressWarnings ( "unused" )
      Stack pNewStack )
      {
        pStyledStackParserPanel
            .removeStackChangedListener ( StyledStackParserPanel.this.stackChangedListenerOther );
        pStyledStackParserPanel.getEditor ().setText ( getEditor ().getText () );
        pStyledStackParserPanel
            .addStackChangedListener ( StyledStackParserPanel.this.stackChangedListenerOther );
      }
    };
    pStyledStackParserPanel
        .addStackChangedListener ( this.stackChangedListenerOther );
    addStackChangedListener ( this.stackChangedListenerThis );
  }
}
