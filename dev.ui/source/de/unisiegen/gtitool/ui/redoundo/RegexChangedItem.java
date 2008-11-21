package de.unisiegen.gtitool.ui.redoundo;

import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.ui.logic.RegexPanel;


/**
 * TODO
 *
 */
public class RegexChangedItem extends RedoUndoItem
{
  
  /**
   * TODO
   */
  private RegexPanel panel;
  
  /**
   * TODO
   */
  private RegexNode newRegex;
  
  /**
   * TODO
   */
  private RegexNode oldRegex;
  
  
  /**
   * TODO
   * @param panel
   * @param newRegex
   *
   */
  public RegexChangedItem (RegexPanel panel, RegexNode newRegex, RegexNode oldRegex)
  {
    this.panel = panel;
    this.newRegex = newRegex;
    this.oldRegex = oldRegex;
  }

  /**
   * TODO
   *
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#redo()
   */
  @Override
  public void redo ()
  {
    this.panel.changeRegex ( this.newRegex, false );
  }


  /**
   * TODO
   *
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#undo()
   */
  @Override
  public void undo ()
  {
    this.panel.changeRegex ( this.oldRegex, false );
  }

}
