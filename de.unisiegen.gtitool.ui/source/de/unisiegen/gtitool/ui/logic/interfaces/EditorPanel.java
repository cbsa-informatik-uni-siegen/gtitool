package de.unisiegen.gtitool.ui.logic.interfaces;


import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.exchange.Exchange;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.DefaultModel;


/**
 * The {@link EditorPanel} interface.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public interface EditorPanel extends Modifyable, LanguageChangedListener
{

  /**
   * Clears all error and warning messages.
   */
  public void clearValidationMessages ();


  /**
   * Get the {@link Converter} for this {@link EditorPanel}.
   * 
   * @return The {@link Converter}.
   */
  public Converter getConverter ();


  /**
   * Returns the {@link File} of this {@link EditorPanel}.
   * 
   * @return The {@link File} of this {@link EditorPanel}.
   */
  public File getFile ();


  /**
   * Returns the {@link File} ending.
   * 
   * @return The {@link File} ending.
   */
  public String getFileEnding ();


  /**
   * Returns the {@link JTabbedPane} containing the console.
   * 
   * @return the {@link JTabbedPane} containing the console.
   */
  public JTabbedPane getJTabbedPaneConsole ();


  /**
   * Returns the {@link DefaultMachineModel}
   * 
   * @return the {@link DefaultMachineModel}
   */
  public DefaultModel getModel ();


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
   * Handles the {@link Exchange}.
   */
  public void handleExchange ();


  /**
   * Redo last step
   */
  public void handleRedo ();


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
  public void handleToolbarEditDocument ();


  /**
   * Undo last step
   */
  public void handleUndo ();


  /**
   * Signals if this panel is redo able
   * 
   * @return true, if is redo able, false else
   */
  public boolean isRedoAble ();


  /**
   * Signals if this panel is undo able
   * 
   * @return true, if is undo able, false else
   */
  public boolean isUndoAble ();


  /**
   * Sets the name of this {@link EditorPanel}.
   * 
   * @param name The name to set;
   */
  public void setName ( String name );


  /**
   * Sets the visibility of the console.
   * 
   * @param visible Visible or not visible.
   */
  public void setVisibleConsole ( boolean visible );
}
