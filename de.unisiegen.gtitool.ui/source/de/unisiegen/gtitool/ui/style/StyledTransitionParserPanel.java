package de.unisiegen.gtitool.ui.style;


import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.parser.transition.TransitionParseable;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link Transition} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledTransitionParserPanel.java 225 2007-11-22 16:55:02Z
 *          fehler $
 */
public final class StyledTransitionParserPanel extends
    StyledParserPanel < Transition >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6870722718951231990L;


  /**
   * Allocates a new {@link StyledTransitionParserPanel}.
   */
  public StyledTransitionParserPanel ()
  {
    super ( new TransitionParseable () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final Transition checkParsedObject ( Transition transition )
  {
    return transition;
  }
}
