package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.entities.regex.LeafNode;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.entities.regex.TokenNode;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.regex.RegexParseable;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * A panel for the RegexParser
 */
public final class StyledRegexParserPanel extends
    StyledParserPanel < RegexNode >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6169359927866826626L;


  /**
   * Creates a new from {@link StyledRegexParserPanel}
   */
  public StyledRegexParserPanel ()
  {
    super ( new RegexParseable () );
  }


  /**
   * Every {@link Symbol} in the {@link Word} has to be in this {@link Alphabet} .
   */
  private Alphabet alphabet = null;


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(de.unisiegen.gtitool.core.entities.Entity)
   */
  @Override
  protected RegexNode checkParsedObject ( RegexNode regexNode )
  {
    System.err.println ("Alphabet: " + this.alphabet);
    System.err.println ("regexNode: " + regexNode);
    ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();

    if ( ( this.alphabet != null ) && ( regexNode != null ) )
    {
      for ( LeafNode current : regexNode.getTokenNodes () )
      {
        if ( current instanceof TokenNode )
        {
          if ( !this.alphabet.contains ( new DefaultSymbol (
              ( ( TokenNode ) current ).getName () ) ) )
          {
            exceptionList.add ( new ParserException ( current
                .getParserOffset ().getStart (), current.getParserOffset ()
                .getEnd (), Messages.getPrettyString (
                "StyledWordParserPanel.SymbolNotInAlphabet", //$NON-NLS-1$
                current.toPrettyString (), this.alphabet.toPrettyString () )
                .toHTMLString () ) );
          }
        }
      }
    }

    if ( exceptionList.size () > 0 )
    {
      setException ( exceptionList );
      return null;
    }
    return regexNode;
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
