package de.unisiegen.gtitool.ui.netbeans.helperclasses;

import de.unisiegen.gtitool.ui.EditorPanel;

/**
 * 
 * Interface representing the Panel in the GUI.
 *
 * @author Benjamin Mies
 *
 */
public interface EditorPanelForm
{
  /**
   * 
   * Get the Logic Object for this View
   *
   * @return The {@link EditorPanel}
   */
  public EditorPanel getLogic();
}
