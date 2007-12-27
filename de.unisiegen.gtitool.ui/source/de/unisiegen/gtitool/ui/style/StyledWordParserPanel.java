package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.word.WordParseable;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.listener.WordChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link Word} panel class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StyledWordParserPanel extends StyledParserPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2677286061045400139L;


  /**
   * The list of {@link WordChangedListener}.
   */
  private ArrayList < WordChangedListener > wordChangedListenerList = new ArrayList < WordChangedListener > ();


  /**
   * The {@link WordChangedListener} for the other
   * <code>StyledWordParserPanel</code>.
   */
  private WordChangedListener wordChangedListenerOther;


  /**
   * The {@link WordChangedListener} for this <code>StyledWordParserPanel</code>.
   */
  private WordChangedListener wordChangedListenerThis;


  /**
   * Every {@link Symbol} in the {@link Word} has to be in this {@link Alphabet}.
   */
  private Alphabet alphabet = null;


  /**
   * Allocates a new <code>StyledWordParserPanel</code>.
   */
  public StyledWordParserPanel ()
  {
    super ( new WordParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object pNewObject )
      {
        fireWordChanged ( ( Word ) pNewObject );
      }
    } );
  }


  /**
   * Adds the given {@link WordChangedListener}.
   * 
   * @param pListener The {@link WordChangedListener}.
   */
  public final synchronized void addWordChangedListener (
      WordChangedListener pListener )
  {
    this.wordChangedListenerList.add ( pListener );
  }


  /**
   * Let the listeners know that the {@link Word} has changed.
   * 
   * @param pNewWord The new {@link Word}.
   */
  private final void fireWordChanged ( Word pNewWord )
  {
    if ( ( pNewWord != null ) && ( this.alphabet != null ) )
    {
      ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();
      for ( Symbol current : pNewWord )
      {
        if ( !this.alphabet.contains ( current ) )
        {
          exceptionList.add ( new ParserException ( current
              .getParserStartOffset (), current.getParserEndOffset (), Messages
              .getString ( "StyledWordParserPanel.SymbolNotInAlphabet", //$NON-NLS-1$
                  current.getName (), this.alphabet ) ) );
        }
      }
      if ( exceptionList.size () > 0 )
      {
        getDocument ().setException ( exceptionList );
        for ( WordChangedListener current : this.wordChangedListenerList )
        {
          current.wordChanged ( null );
        }
        return;
      }
    }
    for ( WordChangedListener current : this.wordChangedListenerList )
    {
      current.wordChanged ( pNewWord );
    }
  }


  /**
   * Returns the {@link Alphabet}.
   * 
   * @return The {@link Alphabet}.
   */
  public final Alphabet getAlphabet ()
  {
    return this.alphabet;
  }


  /**
   * Returns the {@link Word} for the program text within the document. Throws
   * an exception if a parsing error occurred.
   * 
   * @return The {@link Word} for the program text.
   */
  public final Word getWord ()
  {
    try
    {
      return ( Word ) getParsedObject ();
    }
    catch ( Exception exc )
    {
      return null;
    }
  }


  /**
   * Removes the given {@link WordChangedListener}.
   * 
   * @param pListener The {@link WordChangedListener}.
   * @return <tt>true</tt> if the list contained the specified element.
   */
  public final synchronized boolean removeWordChangedListener (
      WordChangedListener pListener )
  {
    return this.wordChangedListenerList.remove ( pListener );
  }


  /**
   * Sets the {@link Alphabet}. Every {@link Symbol} in the {@link Word} has to
   * be in the {@link Alphabet}.
   * 
   * @param pAlphabet The {@link Alphabet} to set.
   */
  public final void setAlphabet ( Alphabet pAlphabet )
  {
    this.alphabet = pAlphabet;
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
   * Sets the {@link Word} of the document.
   * 
   * @param pWord The input {@link Word}.
   */
  public final void setWord ( Word pWord )
  {
    getEditor ().setText ( pWord.toString () );
  }


  /**
   * Synchronizes this <code>StyledWordParserPanel</code> with the given
   * <code>StyledWordParserPanel</code>.
   * 
   * @param pStyledWordParserPanel The other <code>StyledWordParserPanel</code>
   *          which should be synchronized.
   */
  public final void synchronize (
      final StyledWordParserPanel pStyledWordParserPanel )
  {
    this.wordChangedListenerOther = new WordChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void wordChanged ( @SuppressWarnings ( "unused" )
      Word pNewWord )
      {
        removeWordChangedListener ( StyledWordParserPanel.this.wordChangedListenerThis );
        getEditor ().setText ( pStyledWordParserPanel.getEditor ().getText () );
        addWordChangedListener ( StyledWordParserPanel.this.wordChangedListenerThis );
      }
    };
    this.wordChangedListenerThis = new WordChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void wordChanged ( @SuppressWarnings ( "unused" )
      Word pNewWord )
      {
        pStyledWordParserPanel
            .removeWordChangedListener ( StyledWordParserPanel.this.wordChangedListenerOther );
        pStyledWordParserPanel.getEditor ().setText ( getEditor ().getText () );
        pStyledWordParserPanel
            .addWordChangedListener ( StyledWordParserPanel.this.wordChangedListenerOther );
      }
    };
    pStyledWordParserPanel
        .addWordChangedListener ( this.wordChangedListenerOther );
    addWordChangedListener ( this.wordChangedListenerThis );
  }
}
