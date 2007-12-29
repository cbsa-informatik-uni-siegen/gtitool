package de.unisiegen.gtitool.ui.logic;

import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;

import de.unisiegen.gtitool.ui.netbeans.ConsolePanelForm;


public class ConsolePanel
{ 
  MachinePanel parent;
  ConsolePanelForm gui;
   public ConsolePanel( MachinePanel pParent ){
     this.parent = pParent;
     this.gui = new ConsolePanelForm();
     this.gui.setLogic(this);
     
   }
  public void handleConsoleTableMouseExited ( MouseEvent evt )
  {
    this.parent.handleConsoleTableMouseExited(evt);
    
  }
  public void handleConsoleTableFocusLost ( FocusEvent evt )
  {
    this.parent.handleConsoleTableFocusLost ( evt );
    
  }
  public void handleConsoleTableMouseMoved ( MouseEvent evt )
  {
    this.parent.handleConsoleTableMouseMoved ( evt );
    
  }
  
  public ConsolePanelForm getGui() {
    return this.gui;
  }
}
