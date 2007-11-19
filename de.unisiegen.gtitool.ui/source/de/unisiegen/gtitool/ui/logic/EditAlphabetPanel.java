package de.unisiegen.gtitool.ui.logic;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JPanel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.ui.netbeans.EditAlphabetPanelForm;
import de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel;

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
  
  /**
   * The {@link StyledAlphabetParserPanel}.
   */
  public StyledAlphabetParserPanel styledAlphabetParserPanel;

  
  /**
   * 
   * Allocate a new {@link EditAlphabetPanel}
   *
   */
  public EditAlphabetPanel ()
  {
    this.editAlphabetPanel = new EditAlphabetPanelForm ();
    
    /*
     * StyledPanel
     */
    this.styledAlphabetParserPanel = new StyledAlphabetParserPanel ();
    GridBagConstraints gridBagConstraints = new GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new Insets ( 0, 0, 0, 0 );
    this.editAlphabetPanel.jPanelTextArea.add ( this.styledAlphabetParserPanel ,
        gridBagConstraints );
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
