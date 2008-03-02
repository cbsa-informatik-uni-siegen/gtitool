package de.unisiegen.gtitool.ui.logic;


import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;
import de.unisiegen.gtitool.ui.model.DefaultModel;
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
   * The {@link DefaultGrammarModel}.
   */
  private DefaultGrammarModel model;
  
  /**
   * The {@link Grammar}.
   */
  private Grammar grammar;
  
  /**
   * The name of this {@link MachinePanel}.
   */
  private String name = null;
  
  /**
   * The {@link File} for this {@link MachinePanel}.
   */
  private File file;


  /**
   * Allocates a new {@link GrammarPanel}
   * 
   * @param parent The parent frame
   * @param alphabet the {@link Alphabet}
   */
  public GrammarPanel ( JFrame parent, DefaultGrammarModel model )
  {
    this.gui = new GrammarPanelForm ();
    this.gui.setGrammarPanel ( this );
    this.model = model;
    this.grammar = this.model.getGrammar ();

  }


  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
      //TODO implement me
  }


  public File getFile ()
  {
    return this.file;
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#getFileEnding()
   */
  public String getFileEnding ()
  {
    return "." + this.grammar.getGrammarType ().toLowerCase (); //$NON-NLS-1$
  }


  public EditorPanelForm getGui ()
  {
    return this.gui;
  }


  public String getName ()
  {
    return this.file == null ? this.name : this.file.getName ();
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
    //TODO implement me
  }


  public void handleRedo ()
  {
    //TODO implement me
  }


  public File handleSave ()
  {
    //TODO implement me
    return null;
  }


  public File handleSaveAs ()
  {
    //TODO implement me
    return null;
  }


  public void handleToolbarAlphabet ()
  {
    //TODO implement me
  }


  public void handleUndo ()
  {
    //TODO implement me
  }


  public boolean isModified ()
  {
    //TODO implement me
    return false;
  }


  public boolean isRedoAble ()
  {
    //TODO implement me
    return false;
  }


  public boolean isUndoAble ()
  {
    //TODO implement me
    return false;
  }


  public void languageChanged ()
  {
    //TODO implement me
  }


  public void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    //TODO implement me
  }


  public void resetModify ()
  {
    //TODO implement me
  }


  public void setName ( String name )
  {
    this.name = name;
  }


  public DefaultModel getModel ()
  {
    return this.model;
  }
}
