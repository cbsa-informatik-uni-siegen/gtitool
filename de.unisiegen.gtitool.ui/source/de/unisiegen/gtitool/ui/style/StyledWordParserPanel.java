package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.word.WordParseable;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link Word} panel class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StyledWordParserPanel extends StyledParserPanel < Word >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2677286061045400139L;


  /**
   * Every {@link Symbol} in the {@link Word} has to be in this {@link Alphabet}.
   */
  private Alphabet alphabet = null;


  /**
   * Allocates a new {@link StyledWordParserPanel}.
   */
  public StyledWordParserPanel ()
  {
    super ( new WordParseable () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final Word checkParsedObject ( Word word )
  {
    ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();

    if ( ( this.alphabet != null ) && ( word != null ) )
    {
      for ( Symbol current : word )
      {
        if ( !this.alphabet.contains ( current ) )
        {
          exceptionList.add ( new ParserException ( current.getParserOffset ()
              .getStart (), current.getParserOffset ().getEnd (), Messages
              .getString ( "StyledWordParserPanel.SymbolNotInAlphabet", //$NON-NLS-1$
                  current.getName (), this.alphabet ) ) );
        }
      }
    }

    if ( exceptionList.size () > 0 )
    {
      setException ( exceptionList );
      return null;
    }

    return word;
  }


  /**
   * Sets the {@link Alphabet}. Every {@link Symbol} in the {@link Word} has to
   * be in the {@link Alphabet}.
   * 
   * @param alphabet The {@link Alphabet} to set.
   */
  public final void setAlphabet ( Alphabet alphabet )
  {
    this.alphabet = alphabet;
  }
}
