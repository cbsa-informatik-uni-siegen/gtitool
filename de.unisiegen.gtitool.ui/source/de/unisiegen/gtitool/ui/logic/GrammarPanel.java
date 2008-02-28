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


  public boolean isModified ()
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


  public EditorPanelForm getGui ()
  {
    // TODO Auto-generated method stub
    return null;
  }


  public void handleRedo ()
  {
    // TODO Auto-generated method stub
    
  }


  public void handleUndo ()
  {
    // TODO Auto-generated method stub
    
  }


  public boolean isRedoAble ()
  {
    // TODO Auto-generated method stub
    return false;
  }


  public boolean isUndoAble ()
  {
    // TODO Auto-generated method stub
    return false;
  }


  public DefaultMachineModel getModel ()
  {
    // TODO Auto-generated method stub
    return null;
  }
}
