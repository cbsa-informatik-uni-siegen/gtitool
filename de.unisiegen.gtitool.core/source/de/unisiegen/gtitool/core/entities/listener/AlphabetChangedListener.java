package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.Alphabet;


/**
 * The listener interface for receiving {@link Alphabet} changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface AlphabetChangedListener extends EventListener
{

  /**
   * Invoked when the {@link Alphabet} changed.
   * 
   * @param newAlphabet The new {@link Alphabet}.
   */
  public void alphabetChanged ( Alphabet newAlphabet );
}
