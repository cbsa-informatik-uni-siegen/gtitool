package de.unisiegen.gtitool.ui.style;


import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.parser.symbol.SymbolParseable;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link Symbol} panel class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StyledSymbolParserPanel extends StyledParserPanel < Symbol >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8301131779540090916L;


  /**
   * Allocates a new {@link StyledSymbolParserPanel}.
   */
  public StyledSymbolParserPanel ()
  {
    super ( new SymbolParseable () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final Symbol checkParsedObject ( Symbol symbol )
  {
    return symbol;
  }
}
