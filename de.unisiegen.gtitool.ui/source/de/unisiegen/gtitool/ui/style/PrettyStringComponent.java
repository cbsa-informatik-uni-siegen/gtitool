package de.unisiegen.gtitool.ui.style;


import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;


/**
 * This class implements the {@link PrettyPrintable} {@link Component}.
 * 
 * @author Christian Fehler
 */
public final class PrettyStringComponent extends JLabel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6078784158332697414L;


  /**
   * The {@link PrettyString} of this component.
   */
  private PrettyString prettyString;


  /**
   * Initializes the {@link PrettyStringComponent}.
   * 
   * @param prettyString The {@link PrettyString}.
   */
  public PrettyStringComponent ( PrettyString prettyString )
  {
    super ();
    this.prettyString = prettyString;
    setBorder ( new EmptyBorder ( 1, 1, 1, 1 ) );

    // Used to calculate the preferered size.
    setText ( "Component" ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see JComponent#paintComponent(Graphics)
   */
  @Override
  protected final void paintComponent ( Graphics g )
  {
    g.setColor ( getBackground () );
    g.fillRect ( 0, 0, getWidth (), getHeight () );

    int dx = 0;

    FontMetrics metrics = g.getFontMetrics ();

    for ( PrettyToken currentToken : this.prettyString.getPrettyToken () )
    {
      Font font = null;

      if ( !currentToken.getStyle ().isBold ()
          && !currentToken.getStyle ().isItalic () )
      {
        font = g.getFont ().deriveFont ( Font.PLAIN );
      }
      else if ( currentToken.getStyle ().isBold ()
          && currentToken.getStyle ().isItalic () )
      {
        font = g.getFont ().deriveFont ( Font.BOLD | Font.ITALIC );
      }
      else if ( currentToken.getStyle ().isBold () )
      {
        font = g.getFont ().deriveFont ( Font.BOLD );
      }
      else if ( currentToken.getStyle ().isItalic () )
      {
        font = g.getFont ().deriveFont ( Font.ITALIC );
      }

      g.setFont ( font );
      g.setColor ( currentToken.getStyle ().getColor () );
      char [] chars = currentToken.getChar ();
      for ( int i = 0 ; i < chars.length ; i++ )
      {
        g.drawChars ( chars, i, 1, dx, getHeight () - 3 );
        dx += metrics.charWidth ( chars [ i ] );
      }
    }
  }
}
