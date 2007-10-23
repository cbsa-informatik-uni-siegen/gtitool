package de.unisiegen.gtitool.ui.logic;


import javax.swing.JPanel;

import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.netbeans.GrammarPanelForm;
import de.unisiegen.gtitool.ui.netbeans.MachinesPanelForm;


public class GrammarPanel implements EditorPanel
{

  GrammarPanelForm grammarPanel;


  public GrammarPanel (JPanel alphabetPanel)
  {
    this.grammarPanel = new GrammarPanelForm ();
    
    this.grammarPanel.alphabetPanel.add ( alphabetPanel );

  }


  public JPanel getPanel ()
  {
    return this.grammarPanel;
  }

}
