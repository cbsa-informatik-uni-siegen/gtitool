package de.unisiegen.gtitool.ui.style.parser;


import java.awt.event.MouseEvent;

import javax.swing.JEditorPane;
import javax.swing.ToolTipManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledEditorKit;


/**
 * An {@link JEditorPane} that works on {@link StyledParserDocument}s and
 * displays tooltips for parser and lexer errors detected by the document.
 * 
 * @author Christian Fehler
 * @version $Id$
 * @see StyledParserDocument
 */
public final class StyledParserEditor extends JEditorPane
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -7023082301981068118L;


  /**
   * Allocates a new <code>StyledParserEditor</code> instance.
   * 
   * @see JEditorPane#JEditorPane()
   */
  public StyledParserEditor ()
  {
    super ();
    setEditorKit ( new StyledEditorKit () );
    ToolTipManager.sharedInstance ().registerComponent ( this );
  }


  /**
   * {@inheritDoc}
   * 
   * @see JEditorPane#getScrollableTracksViewportWidth()
   */
  @Override
  public final boolean getScrollableTracksViewportWidth ()
  {
    return true;
  }


  /**
   * {@inheritDoc}
   * 
   * @see JTextComponent#getToolTipText(MouseEvent)
   */
  @Override
  public final String getToolTipText ( MouseEvent event )
  {
    int index = viewToModel ( event.getPoint () );
    if ( index < getDocument ().getLength () )
    {
      StyledParserDocument document = ( StyledParserDocument ) getDocument ();
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
}
