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
   * Returns the {@link Alphabet} of this{@link JPanel}.
   * 
   * @return the {@link Alphabet}
   */
  public Alphabet getAlphabet ();


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
   * Hanlde Save Button action performed
   * 
   * @return filename of the saved file
   */
  public String handleSave ();


  /**
   * Hanlde Save As Button action performed
   * 
   * @return filename of the saved file
   */
  public String handleSaveAs ();


  /**
   * Handle toolbar {@link Alphabet} button action event.
   */
  public void handleToolbarAlphabet ();
}
