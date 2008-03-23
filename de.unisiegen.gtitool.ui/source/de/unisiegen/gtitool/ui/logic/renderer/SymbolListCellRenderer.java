package de.unisiegen.gtitool.ui.logic.renderer;


import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.preferences.PreferenceManager;


/**
 * The {@link Symbol} list cell renderer.
 * 
 * @author Benjamin Mies
 */
public class SymbolListCellRenderer extends DefaultListCellRenderer
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2885770452108333745L;


  /**
   * Allocates a new {@link SymbolListCellRenderer}.
   */
  public SymbolListCellRenderer ()
  {
    // Do nothing
  }


  /**
   * {@inheritDoc}
   * 
   * @see DefaultListCellRenderer#getListCellRendererComponent(JList, Object,
   *      int, boolean, boolean)
   */
  @Override
  public Component getListCellRendererComponent ( JList list, Object value,
      int index, boolean isSelected, @SuppressWarnings ( "unused" )
      boolean cellHasFocus )
  {
    Component component = super.getListCellRendererComponent ( list, value,
        index, isSelected, isSelected );
    component.setForeground ( PreferenceManager.getInstance ()
        .getColorItemSymbol ().getColor () );
    return component;
  }
}
