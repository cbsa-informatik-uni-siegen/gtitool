package de.unisiegen.gtitool.ui;


import java.io.File;

import javax.swing.JPanel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.ui.netbeans.helperclasses.EditorPanelForm;


/**
 * The {@link EditorPanel} interface.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public interface EditorPanel extends Modifyable, LanguageChangedListener
{

  /**
   * Get the file of this {@link EditorPanel}.
   * 
   * @return the File of this {@link EditorPanel}.
   */
  public File getFile ();


  /**
   * Returns the name of this {@link EditorPanel}.
   * 
   * @return The name of this {@link EditorPanel}.
   */
  public String getName ();


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
  public File handleSave ();


  /**
   * Handles save as button action performed.
   * 
   * @return The filename of the saved file.
   */
  public File handleSaveAs ();


  /**
   * Handle toolbar {@link Alphabet} button action event.
   */
  public void handleToolbarAlphabet ();


  /**
   * Sets the name of this {@link EditorPanel}.
   * 
   * @param name The name to set;
   */
  public void setName ( String name );
  
  /**
   * 
   * Getter for the gui element of this logic class
   *
   * @return The {@link EditorPanelForm} for this logic class
   */
  public EditorPanelForm getGui();
  
  /**
   * Redo last step
   */
  public void handleRedo();
  
  /**
   * Undo last step
   */
  public void handleUndo();

  /**
   * Signals if this panel is undo able
   * 
   * @return true, if is undo able, false else
   */
  public boolean isUndoAble ();

  /**
   * Signals if this panel is redo able
   * 
   * @return true, if is redo able, false else
   */
  public boolean isRedoAble ();
}
