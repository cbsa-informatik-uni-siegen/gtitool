package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.parser.alphabet.AlphabetParseable;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link Alphabet} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledAlphabetParserPanel.java 946 2008-05-30 14:27:24Z fehler
 *          $
 */
public final class StyledAlphabetParserPanel extends
    StyledParserPanel < Alphabet >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6870722718951231990L;


  /**
   * The parsed {@link Alphabet} can not contain this {@link Symbol}s.
   */
  private TreeSet < Symbol > notRemoveableSymbols = null;


  /**
   * Allocates a new {@link StyledAlphabetParserPanel}.
   */
  public StyledAlphabetParserPanel ()
  {
    super ( new AlphabetParseable () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final Alphabet checkParsedObject ( Alphabet alphabet )
  {
    ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();

    if ( ( this.notRemoveableSymbols != null ) && ( alphabet != null ) )
    {
      for ( Symbol current : this.notRemoveableSymbols )
      {
        if ( !alphabet.contains ( current ) )
        {
          exceptionList.add ( new ParserException ( current.getParserOffset ()
              .getStart (), current.getParserOffset ().getEnd (), Messages
              .getPrettyString ( "AlphabetDialog.SymbolUsed", //$NON-NLS-1$
                  current.toPrettyString () ).toHTMLString () ) );
        }
      }
    }

    if ( exceptionList.size () > 0 )
    {
      setException ( exceptionList );
      return null;
    }

    return alphabet;
  }


  /**
   * Sets the {@link Symbol}s which should not be removeable.
   * 
   * @param notRemoveableSymbols The {@link Symbol}s which should not be
   *          removeable.
   * @see #notRemoveableSymbols
   */
  public final void setNotRemoveableSymbols (
      TreeSet < Symbol > notRemoveableSymbols )
  {
    this.notRemoveableSymbols = notRemoveableSymbols;
  }
}
