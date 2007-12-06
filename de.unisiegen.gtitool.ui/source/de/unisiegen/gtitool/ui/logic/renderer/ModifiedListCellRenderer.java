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
   * {@inheritDoc}
   * 
   * @see DefaultListCellRenderer#getListCellRendererComponent( JList, Object,
   *      int, boolean, boolean)
   */
  @Override
  public Component getListCellRendererComponent ( JList pJList, Object pValue,
      int pIndex, boolean pIsSelected, @SuppressWarnings ( "unused" )
      boolean pCellHasFocus )
  {
    // The cell has focus value is not used any more
    JLabel label = ( JLabel ) super.getListCellRendererComponent ( pJList,
        pValue, pIndex, pIsSelected, pIsSelected );
    return label;
  }
}
