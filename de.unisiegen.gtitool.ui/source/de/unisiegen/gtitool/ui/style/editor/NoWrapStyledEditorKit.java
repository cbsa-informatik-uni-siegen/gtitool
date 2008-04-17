package de.unisiegen.gtitool.ui.style.editor;


import javax.swing.CellEditor;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;


/**
 * The {@link NoWrapStyledEditorKit}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class NoWrapStyledEditorKit extends StyledEditorKit
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6101376003114162011L;


  /**
   * The line break attribute name.
   */
  public static final String LINE_BREAK_ATTRIBUTE_NAME = "line_break_attribute"; //$NON-NLS-1$


  /**
   * Flag that indicates if the {@link NoWrapStyledEditorKit} is used as a
   * {@link CellEditor}.
   */
  private boolean cellEditor = false;


  /**
   * The {@link ViewFactory}.
   */
  private ViewFactory defaultFactory;


  /**
   * Allocates a new {@link NoWrapStyledEditorKit}.
   */
  public NoWrapStyledEditorKit ()
  {
    this.defaultFactory = new NoWrapColumnFactory ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledEditorKit#getInputAttributes()
   */
  @Override
  public final MutableAttributeSet getInputAttributes ()
  {
    if ( this.cellEditor )
    {
      MutableAttributeSet attributeSet = super.getInputAttributes ();
      attributeSet.removeAttribute ( LINE_BREAK_ATTRIBUTE_NAME );
      return attributeSet;
    }
    return super.getInputAttributes ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledEditorKit#getViewFactory()
   */
  @Override
  public final ViewFactory getViewFactory ()
  {
    if ( this.cellEditor )
    {
      return this.defaultFactory;
    }
    return super.getViewFactory ();
  }


  /**
   * Returns true if this {@link NoWrapStyledEditorKit} is used as a
   * {@link CellEditor}, otherwise false.
   * 
   * @return True if this {@link NoWrapStyledEditorKit} is used as a
   *         {@link CellEditor}, otherwise false.
   */
  public final boolean isCellEditor ()
  {
    return this.cellEditor;
  }


  /**
   * Sets the cell editor flag.
   * 
   * @param cellEditor The cell editor flag.
   */
  public final void setCellEditor ( boolean cellEditor )
  {
    this.cellEditor = cellEditor;
  }
}
