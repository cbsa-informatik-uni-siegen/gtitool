package de.unisiegen.gtitool.ui.logic.renderer;


import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


/**
 * The modified {@link ListCellRenderer}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class ModifiedListCellRenderer extends DefaultListCellRenderer
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -4589532752560312106L;


  /**
   * Allocates a new {@link ModifiedListCellRenderer}.
   */
  public ModifiedListCellRenderer ()
  {
    super ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see DefaultListCellRenderer#getListCellRendererComponent( JList, Object,
   *      int, boolean, boolean)
   */
  @Override
  public Component getListCellRendererComponent ( JList jList, Object value,
      int index, boolean isSelected, @SuppressWarnings ( "unused" )
      boolean cellHasFocus )
  {
    // The cell has focus value is not used any more
    JLabel label = ( JLabel ) super.getListCellRendererComponent ( jList,
        value, index, isSelected, isSelected );
    return label;
  }
}
