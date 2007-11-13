package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.parser.alphabet.AlphabetParseable;
import de.unisiegen.gtitool.ui.preferences.listener.AlphabetChangedListener;


/**
 * The styled {@link Alphabet} panel class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StyledAlphabetParserPanel extends StyledParserPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6870722718951231990L;


  /**
   * The list of {@link AlphabetChangedListener}.
   */
  private ArrayList < AlphabetChangedListener > alphabetChangedListenerList = new ArrayList < AlphabetChangedListener > ();


  /**
   * Allocates a new <code>StyledAlphabetParserPanel</code>.
   * 
   * @param pAlphabetParseable The input {@link AlphabetParseable}.
   */
  public StyledAlphabetParserPanel ( AlphabetParseable pAlphabetParseable )
  {
    super ( pAlphabetParseable );
    getDocument ().setStyledAlphabetParserPanel ( this );
  }


  /**
   * Adds the given {@link AlphabetChangedListener}.
   * 
   * @param pListener The {@link AlphabetChangedListener}.
   */
  public final synchronized void addAlphabetChangedListener (
      AlphabetChangedListener pListener )
  {
    this.alphabetChangedListenerList.add ( pListener );
  }


  /**
   * Let the listeners know that the {@link Alphabet} has changed.
   * 
   * @param pNewAlphabet The new {@link Alphabet}.
   */
  public final void fireAlphabetChanged ( Alphabet pNewAlphabet )
  {
    for ( AlphabetChangedListener current : this.alphabetChangedListenerList )
    {
      current.alphabetChanged ( pNewAlphabet );
    }
  }


  /**
   * Returns the {@link Alphabet} for the program text within the document.
   * Throws an exception if a parsing error occurred.
   * 
   * @return The {@link Alphabet} for the program text.
   */
  public final Alphabet getAlphabet ()
  {
    try
    {
      return ( Alphabet ) getParsedObject ();
    }
    catch ( Exception exc )
    {
      return null;
    }
  }


  /**
   * Removes the given {@link AlphabetChangedListener}.
   * 
   * @param pListener The {@link AlphabetChangedListener}.
   * @return <tt>true</tt> if the list contained the specified element.
   */
  public final synchronized boolean removeAlphabetChangedListener (
      AlphabetChangedListener pListener )
  {
    return this.alphabetChangedListenerList.remove ( pListener );
  }


  /**
   * Sets the {@link Alphabet} of the document.
   * 
   * @param pAlphabet The input {@link Alphabet}.
   */
  public final void setAlphabet ( Alphabet pAlphabet )
  {
    getEditor ().setText ( pAlphabet.toString () );
  }
}
