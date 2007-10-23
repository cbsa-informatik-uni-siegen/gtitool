package de.unisiegen.gtitool.ui.logic;


import javax.swing.JPanel;

import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.netbeans.MachinesPanelForm;


public class MachinePanel implements EditorPanel
{

  MachinesPanelForm machinePanel;


  public MachinePanel (JPanel alphabetPanel)
  {
    this.machinePanel = new MachinesPanelForm ();
    this.machinePanel.alphabetPanel.add ( alphabetPanel );
  }


  public JPanel getPanel ()
  {
    return this.machinePanel;
  }
}
