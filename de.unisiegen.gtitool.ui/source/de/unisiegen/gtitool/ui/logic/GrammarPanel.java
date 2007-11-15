package de.unisiegen.gtitool.ui.logic;


import javax.swing.JFrame;
import javax.swing.JPanel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.netbeans.GrammarPanelForm;

/**
 * 
 * TODO
 *
 * @author Benjamin Mies
 * @version $Id: NewDialog.java 119 2007-11-10 12:07:30Z fehler 
 *
 */
public class GrammarPanel implements EditorPanel
{

  GrammarPanelForm grammarPanel;


  public GrammarPanel ( JFrame pParent, Alphabet pAlphabet )
  {
    this.grammarPanel = new GrammarPanelForm ();
    

  }


  public JPanel getPanel ()
  {
    return this.grammarPanel;
  }

}
