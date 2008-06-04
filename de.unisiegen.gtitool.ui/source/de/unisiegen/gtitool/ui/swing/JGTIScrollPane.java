package de.unisiegen.gtitool.ui.swing;


import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;


/**
 * Special {@link JScrollPane}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTIScrollPane extends JScrollPane
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 7315286435055612251L;


  /**
   * Allocates a new {@link JGTIScrollPane}.
   */
  public JGTIScrollPane ()
  {
    super ();
    setBorder ( new LineBorder ( Color.BLACK, 1 ) );
  }


  /**
   * Fills the upper right corner with the table header if the component is an
   * instance of {@link JTable}.
   * 
   * @param view The component to add to the viewport.
   */
  @Override
  public final void setViewportView ( Component view )
  {
    super.setViewportView ( view );
    if ( view instanceof JTable )
    {
      JTable jTable = ( JTable ) view;
      JTableHeader jTableHeader = new JTableHeader ();
      jTableHeader.setTable ( jTable );
      jTableHeader.setResizingAllowed ( false );
      jTableHeader.setReorderingAllowed ( false );
      this.setCorner ( ScrollPaneConstants.UPPER_RIGHT_CORNER, jTableHeader );
    }
  }
}
