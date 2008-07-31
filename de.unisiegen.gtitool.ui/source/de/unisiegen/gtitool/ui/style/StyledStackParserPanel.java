package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.stack.StackParseable;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link Stack} panel class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StyledStackParserPanel extends StyledParserPanel < Stack >
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
   * Allocates a new {@link StyledStackParserPanel}.
   */
  public StyledStackParserPanel ()
  {
    super ( new StackParseable () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final Stack checkParsedObject ( Stack stack )
  {
    ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();

    if ( ( this.pushDownAlphabet != null ) && ( stack != null ) )
    {
      for ( Symbol current : stack )
      {
        if ( !this.pushDownAlphabet.contains ( current ) )
        {
          exceptionList.add ( new ParserException ( current.getParserOffset ()
              .getStart (), current.getParserOffset ().getEnd (), Messages
              .getPrettyString (
                  "StyledStackParserPanel.SymbolNotInPushDownAlphabet", //$NON-NLS-1$
                  current.toPrettyString (),
                  this.pushDownAlphabet.toPrettyString () ).toHTMLString () ) );
        }
      }
    }

    if ( exceptionList.size () > 0 )
    {
      setException ( exceptionList );
      return null;
    }

    return stack;
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
}
