package de.unisiegen.gtitool.ui.logic;


import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.netbeans.GrammarPanelForm;


/**
 * @author Benjamin Mies
 * @version $Id$
 */
@SuppressWarnings ( "all" )
public class GrammarPanel implements EditorPanel
{

  private GrammarPanelForm gui;


  /**
   * Allocates a new <code>GrammarPanel</code>
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


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.EditorPanel#getAlphabet()
   */
  public Alphabet getAlphabet ()
  {
    return new DefaultAlphabet ();
  }


  public File getFile ()
  {
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.EditorPanel#getPanel()
   */
  public JPanel getPanel ()
  {
    return this.gui;
  }


  public String handleSave ()
  {
    return null;
  }


  public String handleSaveAs ()
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
}
