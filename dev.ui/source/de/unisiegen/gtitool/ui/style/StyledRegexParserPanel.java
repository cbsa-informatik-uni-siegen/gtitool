package de.unisiegen.gtitool.ui.style;

import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.parser.regex.RegexParseable;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * TODO
 *
 */
public final class StyledRegexParserPanel extends StyledParserPanel < RegexNode >
{

  /**
   * TODO
   */
  private static final long serialVersionUID = -6169359927866826626L;

  /**
   * TODO
   *
   * @param parseable
   */
  public StyledRegexParserPanel ( )
  {
    super ( new RegexParseable() );
  }

  /**
   * TODO
   *
   * @param parsedObject
   * @return
   * @see de.unisiegen.gtitool.ui.style.parser.StyledParserPanel#checkParsedObject(de.unisiegen.gtitool.core.entities.Entity)
   */
  @Override
  protected RegexNode checkParsedObject ( RegexNode parsedObject )
  {
    return parsedObject;
  }

}
