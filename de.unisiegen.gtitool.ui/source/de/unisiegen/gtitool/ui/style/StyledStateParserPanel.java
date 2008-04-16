package de.unisiegen.gtitool.ui.style;


import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.parser.state.StateParseable;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link State} panel class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StyledStateParserPanel extends StyledParserPanel < State >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 257507642715920652L;


  /**
   * Allocates a new {@link StyledStateParserPanel}.
   */
  public StyledStateParserPanel ()
  {
    super ( new StateParseable () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final State checkParsedObject ( State state )
  {
    return state;
  }
}
