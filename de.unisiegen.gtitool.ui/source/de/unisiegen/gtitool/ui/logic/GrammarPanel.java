package de.unisiegen.gtitool.ui.logic;


import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.netbeans.GrammarPanelForm;
import de.unisiegen.gtitool.ui.netbeans.helperclasses.EditorPanelForm;


/**
 * @author Benjamin Mies
 * @version $Id$
 */
@SuppressWarnings ( "all" )
public class GrammarPanel implements EditorPanel
{

  private GrammarPanelForm gui;


  /**
   * Allocates a new {@link GrammarPanel}
   * 
   * @param parent The parent frame
   * @param alphabet the {@link Alphabet}
   */
  public GrammarPanel ( JFrame parent, Alphabet alphabet )
  {
    this.gui = new GrammarPanelForm ();

  }


  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {

  }


  public File getFile ()
  {
    return null;
  }


  public String getFileEnding ()
  {
    return null;
  }


  public EditorPanelForm getGui ()
  {
    return null;
  }


  public String getName ()
  {
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#getPanel()
   */
  public JPanel getPanel ()
  {
    return this.gui;
  }


  public void handleExchange ()
  {
  }


  public void handleRedo ()
  {

  }


  public File handleSave ()
  {
    return null;
  }


  public File handleSaveAs ()
  {
    return null;
  }


  public void handleToolbarAlphabet ()
  {

  }


  public void handleUndo ()
  {

  }


  public boolean isModified ()
  {
    return false;
  }


  public boolean isRedoAble ()
  {
    return false;
  }


  public boolean isUndoAble ()
  {
    return false;
  }


  public void languageChanged ()
  {

  }


  public void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {

  }


  public void resetModify ()
  {

  }


  public void setName ( String name )
  {

  }


  public DefaultMachineModel getModel ()
  {
    // TODO Auto-generated method stub
    return null;
  }
}
