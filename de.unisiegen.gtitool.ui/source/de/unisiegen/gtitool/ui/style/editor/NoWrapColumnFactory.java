package de.unisiegen.gtitool.ui.style.editor;


import javax.swing.text.AbstractDocument;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;


/**
 * The {@link NoWrapColumnFactory}.
 * 
 * @author Christian Fehler
 * @version $Id: StyledParserEditor.java 775 2008-04-13 23:02:55Z fehler $
 */
public final class NoWrapColumnFactory implements ViewFactory
{

  /**
   * Allocates a new {@link NoWrapColumnFactory}.
   */
  public NoWrapColumnFactory ()
  {
    // Do nothing
  }


  /**
   * {@inheritDoc}
   * 
   * @see ViewFactory#create(Element)
   */
  public final View create ( Element element )
  {
    String kind = element.getName ();
    if ( kind != null )
    {
      if ( kind.equals ( AbstractDocument.ContentElementName ) )
      {
        return new NoWrapLabelView ( element );
      }
      else if ( kind.equals ( AbstractDocument.ParagraphElementName ) )
      {
        return new NoWrapParagraphView ( element );
      }
      else if ( kind.equals ( AbstractDocument.SectionElementName ) )
      {
        return new BoxView ( element, View.Y_AXIS );
      }
      else if ( kind.equals ( StyleConstants.ComponentElementName ) )
      {
        return new ComponentView ( element );
      }
      else if ( kind.equals ( StyleConstants.IconElementName ) )
      {
        return new IconView ( element );
      }
    }
    return new LabelView ( element );
  }
}
