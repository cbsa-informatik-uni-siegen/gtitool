package de.unisiegen.gtitool.ui.history;


import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.util.ObjectPair;


/**
 * The {@link ObjectPair} of a {@link Transition} and a {@link Symbol}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class TransitionSymbolPair extends ObjectPair < Transition, Symbol >
{

  /**
   * Allocates a new {@link TransitionSymbolPair}.
   * 
   * @param first The {@link Transition}.
   * @param second The {@link Symbol}.
   */
  public TransitionSymbolPair ( Transition first, Symbol second )
  {
    super ( first, second );
  }
}
