package de.unisiegen.gtitool.ui.style;


import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.parser.terminalsymbol.TerminalSymbolParseable;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link TerminalSymbol} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledTerminalSymbolParserPanel.java 532 2008-02-04 23:54:55Z
 *          fehler $
 */
public final class StyledTerminalSymbolParserPanel extends
    StyledParserPanel < TerminalSymbol >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6955506932808118867L;


  /**
   * Allocates a new {@link StyledTerminalSymbolParserPanel}.
   */
  public StyledTerminalSymbolParserPanel ()
  {
    super ( new TerminalSymbolParseable () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final TerminalSymbol checkParsedObject (
      TerminalSymbol terminalSymbol )
  {
    return terminalSymbol;
  }
}
