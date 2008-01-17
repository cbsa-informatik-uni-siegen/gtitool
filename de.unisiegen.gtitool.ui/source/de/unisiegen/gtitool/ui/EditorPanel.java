package de.unisiegen.gtitool.ui;


import java.io.File;

import javax.swing.JPanel;

import de.unisiegen.gtitool.core.entities.Alphabet;


/**
 * The <code>EditorPanel</code> interface.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public interface EditorPanel
{

  /**
   * Returns the {@link JPanel}.
   * 
   * @return The {@link JPanel}.
   */
  public JPanel getPanel ();


  /**
   * Returns the {@link Alphabet} of this panel
   * 
   * @return the {@link Alphabet}
   */
  public Alphabet getAlphabet ();
  
  /**
   * Get the File for this EditorPanel
   * 
   * @return the File for this EditorPanel
   */
  public File getFile();

  /**
   * 
   * Hanlde Save Button action performed
   *
   * @return filename of the saved file
   */
  public String handleSave ();

  /**
   * 
   * Hanlde Save As Button action performed
   *
   * @return filename of the saved file
   */
  public String handleSaveAs ();
}
