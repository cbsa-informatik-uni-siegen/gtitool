package de.unisiegen.gtitool.core.preferences.listener;


import java.util.EventListener;


/**
 * The listener interface for receiving language changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface LanguageChangedListener extends EventListener
{

  /**
   * Invoked when the language changed.
   */
  public void languageChanged ();

}
