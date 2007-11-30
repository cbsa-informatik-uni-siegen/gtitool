package de.unisiegen.gtitool.ui.logic;

import javax.swing.JPanel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.ui.netbeans.EditAlphabetPanelForm;

/**
 * 
 * Panel to edit the {@link Alphabet}
 *
 * @author Benjamin Mies
 * @version $Id: NewDialog.java 119 2007-11-10 12:07:30Z fehler $
 *
 */
public class EditAlphabetPanel
{
  /**
   * The {@link EditAlphabetPanelForm}
   */
  private EditAlphabetPanelForm editAlphabetPanel;
  
  /** The {@link Machine} */
  private Machine machine;

  
  /**
   * 
   * Allocate a new {@link EditAlphabetPanel}
   * 
   * @param pMachine the {@link Machine}
   *
   */
  public EditAlphabetPanel ( Machine pMachine )
  {
    this.machine = pMachine;
    this.editAlphabetPanel = new EditAlphabetPanelForm ();
    this.editAlphabetPanel.setLogic ( this );
    this.editAlphabetPanel.styledAlphabetParserPanel.setAlphabet ( this.machine.getAlphabet () );
  }
  
  /**
   * 
   * Get the Panel to edit the Alphabet
   *
   * @return the panel to edit the Alphabet
   */
  public JPanel getPanel (){
    return this.editAlphabetPanel;
  }
  
}
