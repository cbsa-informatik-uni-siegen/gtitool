package de.unisiegen.gtitool.ui.redoundo;


import de.unisiegen.gtitool.ui.logic.RegexPanel;


/**
 * The {@link RedoUndoItem} if the Regex was changed
 */
public class RegexChangedItem extends RedoUndoItem
{

  /**
   * The {@link RegexPanel}
   */
  private RegexPanel panel;


  /**
   * The new text
   */
  private String newText;


  /**
   * The old text
   */
  private String oldText;


  /**
   * Creates a new of {@link RegexChangedItem}
   * 
   * @param panel The {@link RegexPanel}
   * @param newText The new text
   * @param oldText The old text
   */
  public RegexChangedItem ( RegexPanel panel, String newText, String oldText )
  {
    this.panel = panel;
    this.oldText = oldText;
    this.newText = newText;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#redo()
   */
  @Override
  public void redo ()
  {
    this.panel.changeRegexText ( this.newText );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#undo()
   */
  @Override
  public void undo ()
  {
    this.panel.changeRegexText ( this.oldText );
  }

}
