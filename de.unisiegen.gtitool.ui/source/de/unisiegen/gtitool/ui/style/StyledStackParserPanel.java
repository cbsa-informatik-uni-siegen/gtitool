package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import javax.swing.border.LineBorder;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.ParseableEntity;
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
      public void parseableChanged ( Object newObject )
      {
        fireStackChanged ( ( Stack ) newObject );
      }
    } );
  }


  /**
   * Adds the given {@link StackChangedListener}.
   * 
   * @param listener The {@link StackChangedListener}.
   */
  public final synchronized void addStackChangedListener (
      StackChangedListener listener )
  {
    this.listenerList.add ( StackChangedListener.class, listener );
  }


  /**
   * Checks the given {@link Stack}.
   * 
   * @param stack The {@link Stack} to check.
   * @return The input {@link Stack} or null, if a {@link Symbol} in the
   *         {@link Stack} is not in the push down {@link Alphabet}.
   */
  private final Stack checkStack ( Stack stack )
  {
    Stack checkedStack = stack;
    if ( ( checkedStack != null ) && ( this.pushDownAlphabet != null ) )
    {
      ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();
      for ( Symbol current : checkedStack )
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
      // Check for exceptions
      if ( exceptionList.size () > 0 )
      {
        checkedStack = null;
        this.jScrollPane.setBorder ( new LineBorder ( ERROR_COLOR ) );
        getDocument ().setException ( exceptionList );
      }
    }
    return checkedStack;
  }


  /**
   * Let the listeners know that the {@link Stack} has changed.
   * 
   * @param newStack The new {@link Stack}.
   */
  private final void fireStackChanged ( Stack newStack )
  {
    Stack checkedStack = checkStack ( newStack );
    StackChangedListener [] listeners = this.listenerList
        .getListeners ( StackChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].stackChanged ( checkedStack );
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
      Stack stack = ( Stack ) getParsedObject ();
      return checkStack ( stack );
    }
    catch ( Exception exc )
    {
      return null;
    }
  }


  /**
   * Removes the given {@link StackChangedListener}.
   * 
   * @param listener The {@link StackChangedListener}.
   */
  public final synchronized void removeStackChangedListener (
      StackChangedListener listener )
  {
    this.listenerList.remove ( StackChangedListener.class, listener );
  }


  /**
   * Sets the {@link Symbol}s which should be highlighted.
   * 
   * @param symbols The {@link Symbol}s which should be highlighted.
   */
  public final void setHighlightedSymbol ( Iterable < Symbol > symbols )
  {
    setHighlightedParseableEntity ( symbols );
  }


  /**
   * Sets the {@link Symbol}s which should be highlighted.
   * 
   * @param symbols The {@link Symbol}s which should be highlighted.
   */
  public final void setHighlightedSymbol ( Symbol ... symbols )
  {
    ParseableEntity [] entities = new ParseableEntity [ symbols.length ];
    for ( int i = 0 ; i < symbols.length ; i++ )
    {
      entities [ i ] = symbols [ i ];
    }
    setHighlightedParseableEntity ( entities );
  }


  /**
   * Sets the {@link Symbol} which should be highlighted.
   * 
   * @param symbol The {@link Symbol} which should be highlighted.
   */
  public final void setHighlightedSymbol ( Symbol symbol )
  {
    setHighlightedParseableEntity ( symbol );
  }


  /**
   * Sets the push down {@link Alphabet}. Every {@link Symbol} in the
   * {@link Stack} has to be in the push down {@link Alphabet}.
   * 
   * @param pushDownAlphabet The push down {@link Alphabet} to set.
   */
  public final void setPushDownAlphabet ( Alphabet pushDownAlphabet )
  {
    this.pushDownAlphabet = pushDownAlphabet;
  }


  /**
   * Sets the {@link Stack} of the document.
   * 
   * @param stack The input {@link Stack}.
   */
  public final void setStack ( Stack stack )
  {
    getEditor ().setText ( stack.toString () );
  }
}
