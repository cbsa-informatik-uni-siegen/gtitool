package de.unisiegen.gtitool.ui.logic;


import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.ChooseNextActionDialogForm;


/**
 * TODO
 */
public final class ChooseNextActionDialog implements
    LogicClass < ChooseNextActionDialogForm >
{

  private ChooseNextActionDialogForm gui;


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.logic.interfaces.LogicClass#getGUI()
   */
  public ChooseNextActionDialogForm getGUI ()
  {
    return this.gui;
  }

}
