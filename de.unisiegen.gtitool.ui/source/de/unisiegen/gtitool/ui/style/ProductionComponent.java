package de.unisiegen.gtitool.ui.style;


import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JComponent;

import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;


/**
 * This class implements production which is used in the GrammarPanel.
 * 
 * @author Benjamin Mies
 */
public class ProductionComponent extends JComponent
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -175481337386676439L;
  
  /**
   * The {@link Production} of this component.
   */
  Production production;


  /**
   * Initializes the {@link ProductionComponent}.
   * 
   * @param production The {@link Production}. 
   */
  public ProductionComponent ( Production production )
  {
    super ();
    this.production = production;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void paintComponent ( Graphics g )
  {
    g.setColor ( getBackground () );
    g.fillRect ( 0, 0, getWidth (), getHeight () );

    int dx = 5;

    FontMetrics metrics = g.getFontMetrics ();

    for ( PrettyToken currentToken : this.production.toPrettyString ()
        .getPrettyToken () )
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
        int height = metrics.getHeight ();
        g.drawChars ( chars, i, 1, dx, getHeight () - (( getHeight () - height) / 2) );
        dx += metrics.charWidth ( chars [ i ] );
      }
    }

  }

}
