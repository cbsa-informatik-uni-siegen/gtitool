package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.regexAlphabet.RegexAlphabetParseable;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * TODO
 */
public class StyledRegexAlphabetParserPanel extends
    StyledParserPanel < Alphabet >
{

  /**
   * The serial version uid
   */
  private static final long serialVersionUID = -8528331934091753221L;


  /**
   * The parsed {@link Alphabet} can not contain this {@link Symbol}s.
   */
  private TreeSet < Symbol > notRemoveableSymbols = null;


  /**
   * TODO
   */
  public StyledRegexAlphabetParserPanel ()
  {
    super ( new RegexAlphabetParseable () );
  }


  /**
   * TODO
   * 
   * @param parsedObject
   * @return
   * @see de.unisiegen.gtitool.ui.style.parser.StyledParserPanel#checkParsedObject(de.unisiegen.gtitool.core.entities.Entity)
   */
  @Override
  protected Alphabet checkParsedObject ( Alphabet alphabet )
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
  public void setNotRemoveableSymbols ( TreeSet < Symbol > notRemoveableSymbols )
  {
    this.notRemoveableSymbols = notRemoveableSymbols;
  }

}
