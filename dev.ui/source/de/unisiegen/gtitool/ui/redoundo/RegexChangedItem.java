package de.unisiegen.gtitool.ui.redoundo;

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
  private String newText;
  
  /**
   * TODO
   */
  private String oldText;
  
  
  /**
   * TODO
   * @param panel
   * @param newText 
   * @param oldText 
   *
   */
  public RegexChangedItem (RegexPanel panel, String newText, String oldText)
  {
    this.panel = panel;
    this.oldText = oldText;
    this.newText = newText;
  }

  /**
   * TODO
   *
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#redo()
   */
  @Override
  public void redo ()
  {
    this.panel.changeRegexText ( this.newText );
  }


  /**
   * TODO
   *
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#undo()
   */
  @Override
  public void undo ()
  {
    this.panel.changeRegexText ( this.oldText );
  }

}
