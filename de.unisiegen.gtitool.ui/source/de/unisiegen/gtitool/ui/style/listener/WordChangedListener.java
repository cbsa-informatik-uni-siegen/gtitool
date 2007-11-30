package de.unisiegen.gtitool.ui.style.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.Word;


/**
 * The listener interface for receiving {@link Word} changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface WordChangedListener extends EventListener
{

  /**
   * Invoked when the {@link Word} changed.
   * 
   * @param pNewWord The new {@link Word}.
   */
  public void wordChanged ( Word pNewWord );

}
