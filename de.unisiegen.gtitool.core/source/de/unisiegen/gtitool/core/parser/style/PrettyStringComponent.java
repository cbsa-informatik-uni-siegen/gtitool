package de.unisiegen.gtitool.core.parser.style;


import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;


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
   * The center horizontal value.
   */
  private boolean centerHorizontal = false;


  /**
   * The center vertical value.
   */
  private boolean centerVertical = false;


  /**
   * The used font.
   */
  private static final Font FONT = new Font ( "Dialog", Font.PLAIN, 12 ); //$NON-NLS-1$


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
   * Returns the center horizontal value.
   * 
   * @return The center horizontal value.
   * @see #centerHorizontal
   */
  public final boolean isCenterHorizontal ()
  {
    return this.centerHorizontal;
  }


  /**
   * Returns the center vertical value.
   * 
   * @return The center vertical value.
   * @see #centerVertical
   */
  public final boolean isCenterVertical ()
  {
    return this.centerVertical;
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
    g.setFont ( FONT );
    FontMetrics metrics = g.getFontMetrics ();

    int dx = 0;
    if ( this.centerHorizontal )
    {
      dx = ( getWidth () - metrics.stringWidth ( this.prettyString.toString () ) ) / 2;
    }

    int y = getHeight () - 3;
    if ( this.centerVertical )
    {
      y = y - ( ( getHeight () - metrics.getHeight () ) / 2 );
    }

    for ( PrettyToken currentToken : this.prettyString.getPrettyToken () )
    {
      Font font = null;

      if ( !currentToken.getStyle ().isBold ()
          && !currentToken.getStyle ().isItalic () )
      {
        font = FONT;
      }
      else if ( currentToken.getStyle ().isBold ()
          && currentToken.getStyle ().isItalic () )
      {
        font = FONT.deriveFont ( Font.BOLD | Font.ITALIC );
      }
      else if ( currentToken.getStyle ().isBold () )
      {
        font = FONT.deriveFont ( Font.BOLD );
      }
      else if ( currentToken.getStyle ().isItalic () )
      {
        font = FONT.deriveFont ( Font.ITALIC );
      }

      g.setFont ( font );
      g.setColor ( currentToken.getStyle ().getColor () );
      char [] chars = currentToken.getChar ();
      for ( int i = 0 ; i < chars.length ; i++ )
      {
        g.drawChars ( chars, i, 1, dx, y );
        dx += metrics.charWidth ( chars [ i ] );
      }
    }
  }


  /**
   * Sets the center horizontal value.
   * 
   * @param centerHorizontal The center horizontal value to set.
   * @see #centerHorizontal
   */
  public final void setCenterHorizontal ( boolean centerHorizontal )
  {
    this.centerHorizontal = centerHorizontal;
  }


  /**
   * Sets the center vertical.
   * 
   * @param centerVertical The center vertical to set.
   * @see #centerVertical
   */
  public final void setCenterVertical ( boolean centerVertical )
  {
    this.centerVertical = centerVertical;
  }
}
