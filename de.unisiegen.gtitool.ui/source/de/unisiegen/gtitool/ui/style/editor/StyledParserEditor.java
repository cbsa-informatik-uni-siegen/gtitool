package de.unisiegen.gtitool.ui.style.editor;


import java.awt.event.MouseEvent;

import javax.swing.CellEditor;
import javax.swing.JEditorPane;
import javax.swing.ToolTipManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.ui.style.document.StyledParserDocument;


/**
 * An {@link JEditorPane} that works on {@link StyledParserDocument}s and
 * displays tooltips for parser and lexer errors detected by the document.
 * 
 * @author Christian Fehler
 * @version $Id$
 * @param <E> The {@link Entity}.
 * @see StyledParserDocument
 */
public final class StyledParserEditor < E extends Entity > extends JEditorPane
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -7023082301981068118L;


  /**
   * Flag that indicates if the {@link StyledParserEditor} is used as a
   * {@link CellEditor}.
   */
  private boolean cellEditor = false;


  /**
   * The {@link NoWrapStyledEditorKit}
   */
  private NoWrapStyledEditorKit noWrapStyledEditorKit;


  /**
   * Allocates a new {@link StyledParserEditor} instance.
   * 
   * @see JEditorPane#JEditorPane()
   */
  public StyledParserEditor ()
  {
    super ();
    this.noWrapStyledEditorKit = new NoWrapStyledEditorKit ();
    setEditorKit ( this.noWrapStyledEditorKit );
    setBorder ( new EmptyBorder ( 2, 2, 2, 2 ) );
    ToolTipManager.sharedInstance ().registerComponent ( this );
  }


  /**
   * {@inheritDoc}
   * 
   * @see JTextComponent#getToolTipText(MouseEvent)
   */
  @SuppressWarnings ( "unchecked" )
  @Override
  public final String getToolTipText ( MouseEvent event )
  {
    int index = viewToModel ( event.getPoint () );
    if ( index < getDocument ().getLength () )
    {
      StyledParserDocument < E > document = ( StyledParserDocument ) getDocument ();
      AttributeSet set = document.getCharacterElement ( index )
          .getAttributes ();
      Object exception = set.getAttribute ( "exception" ); //$NON-NLS-1$
      if ( exception != null && exception instanceof Exception )
      {
        return ( ( Exception ) exception ).getMessage ();
      }
    }
    return super.getToolTipText ( event );
  }


  /**
   * Returns true if this {@link StyledParserEditor} is used as a
   * {@link CellEditor}, otherwise false.
   * 
   * @return True if this {@link StyledParserEditor} is used as a
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

    Document doc = getDocument ();
    this.noWrapStyledEditorKit = new NoWrapStyledEditorKit ();
    this.noWrapStyledEditorKit.setCellEditor ( cellEditor );
    setEditorKit ( this.noWrapStyledEditorKit );
    setDocument ( doc );

    if ( this.cellEditor )
    {
      setBorder ( new EmptyBorder ( 0, 0, 0, 0 ) );
    }
    else
    {
      setBorder ( new EmptyBorder ( 2, 2, 2, 2 ) );
    }
  }
}
