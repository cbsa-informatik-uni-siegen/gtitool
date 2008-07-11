package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * The listener interface for receiving {@link PrettyString} changes.
 * 
 * @author Christian Fehler
 * @version $Id: PrettyStringChangedListener.java 446 2008-01-18 15:37:55Z
 *          fehler $
 */
public interface PrettyStringChangedListener extends EventListener
{

  /**
   * Invoked when the {@link PrettyString} changed.
   */
  public void prettyStringChanged ();
}
