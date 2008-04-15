package de.unisiegen.gtitool.ui.style.editor;


import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.GlyphView;
import javax.swing.text.LabelView;
import javax.swing.text.View;


/**
 * The {@link NoWrapColumnFactory}.
 * 
 * @author Christian Fehler
 * @version $Id: StyledParserEditor.java 775 2008-04-13 23:02:55Z fehler $
 */
public final class NoWrapLabelView extends LabelView
{

  /**
   * Allocates a new {@link NoWrapLabelView}.
   * 
   * @param element The element.
   */
  public NoWrapLabelView ( Element element )
  {
    super ( element );
  }


  /**
   * {@inheritDoc}
   * 
   * @see GlyphView#breakView(int, int, float, float)
   */
  @Override
  public final View breakView ( int axis, int p0, float pos, float len )
  {
    if ( axis == View.X_AXIS )
    {
      checkPainter ();
      int p1 = getGlyphPainter ().getBoundedPosition ( this, p0, pos, len );
      try
      {
        int index = getDocument ().getText ( p0, p1 - p0 ).indexOf ( "\r" ); //$NON-NLS-1$
        if ( index >= 0 )
        {
          GlyphView v = ( GlyphView ) createFragment ( p0, p0 + index + 1 );
          return v;
        }
      }
      catch ( BadLocationException ex )
      {
        // Do nothing
      }
    }
    return super.breakView ( axis, p0, pos, len );
  }


  /**
   * {@inheritDoc}
   * 
   * @see GlyphView#getBreakWeight(int, float, float)
   */
  @Override
  public final int getBreakWeight ( int axis, float pos, float len )
  {
    if ( axis == View.X_AXIS )
    {
      checkPainter ();
      int p0 = getStartOffset ();
      int p1 = getGlyphPainter ().getBoundedPosition ( this, p0, pos, len );
      if ( p1 == p0 )
      {
        return View.BadBreakWeight;
      }
      try
      {
        if ( getDocument ().getText ( p0, p1 - p0 ).indexOf ( "\r" ) >= 0 ) //$NON-NLS-1$
        {
          return View.ForcedBreakWeight;
        }
      }
      catch ( BadLocationException ex )
      {
        // Do nothing
      }
    }
    return super.getBreakWeight ( axis, pos, len );
  }
}
