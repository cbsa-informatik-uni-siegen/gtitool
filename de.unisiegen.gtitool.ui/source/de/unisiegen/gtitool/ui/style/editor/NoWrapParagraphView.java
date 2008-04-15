package de.unisiegen.gtitool.ui.style.editor;


import javax.swing.text.BoxView;
import javax.swing.text.Element;
import javax.swing.text.FlowView;
import javax.swing.text.ParagraphView;


/**
 * The no wrap {@link ParagraphView}.
 * 
 * @author Christian Fehler
 * @version $Id: StyledParserEditor.java 775 2008-04-13 23:02:55Z fehler $
 */
public final class NoWrapParagraphView extends ParagraphView
{

  /**
   * Allocates a new {@link NoWrapParagraphView}.
   * 
   * @param element The {@link Element}.
   */
  public NoWrapParagraphView ( Element element )
  {
    super ( element );
  }


  /**
   * {@inheritDoc}
   * 
   * @see BoxView#getMinimumSpan(int)
   */
  @Override
  public final float getMinimumSpan ( int axis )
  {
    return super.getPreferredSpan ( axis );
  }


  /**
   * {@inheritDoc}
   * 
   * @see FlowView#layout(int, int)
   */
  @Override
  public final void layout ( @SuppressWarnings ( "unused" )
  int width, int height )
  {
    super.layout ( Integer.MAX_VALUE, height );
  }
}
