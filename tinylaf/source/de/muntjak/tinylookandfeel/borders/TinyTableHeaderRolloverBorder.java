package de.muntjak.tinylookandfeel.borders;


import java.awt.Component;
import java.awt.Graphics;

import de.muntjak.tinylookandfeel.Theme;
import de.muntjak.tinylookandfeel.controlpanel.ColorRoutines;


/**
 * TinyTableHeaderRolloverBorder is the border displayed for table headers of
 * sortable table models if the column in question is the rollover column.
 * 
 * @version 1.0
 * @author Hans Bickel
 */
@SuppressWarnings (
{ "all" } )
public class TinyTableHeaderRolloverBorder extends TinyTableHeaderBorder
{

  public void paintBorder ( Component c, Graphics g, int x, int y, int w, int h )
  {
    if ( color1 == null )
    {
      color1 = Theme.tableHeaderRolloverColor [ Theme.style ].getColor ();
      color2 = ColorRoutines.lighten ( color1, 25 );
    }

    g.setColor ( color1 );
    g.drawLine ( x, y + h - 3, x + w - 1, y + h - 3 ); // top
    g.drawLine ( x, y + h - 1, x + w - 1, y + h - 1 ); // bottom

    g.setColor ( color2 );
    g.drawLine ( x, y + h - 2, x + w - 1, y + h - 2 ); // mid

    // don't paint separator
  }
}
