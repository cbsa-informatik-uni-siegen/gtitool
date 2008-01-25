package de.unisiegen.gtitool.ui;


import java.io.File;

import javax.swing.JPanel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.ui.preferences.listener.LanguageChangedListener;


/**
 * The {@link EditorPanel} interface.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public interface EditorPanel extends Modifyable, LanguageChangedListener
{

  /**
   * Get the File for this EditorPanel
   * 
   * @return the File for this EditorPanel
   */
  public File getFile ();


  /**
   * Returns the {@link JPanel}.
   * 
   * @return The {@link JPanel}.
   */
  public JPanel getPanel ();


  /**
   * Handles save button action performed.
   * 
   * @return The filename of the saved file.
   */
  public String handleSave ();


  /**
   * Handles save as button action performed.
   * 
   * @return The filename of the saved file.
   */
  public String handleSaveAs ();


  /**
   * Handle toolbar {@link Alphabet} button action event.
   */
  public void handleToolbarAlphabet ();
}
