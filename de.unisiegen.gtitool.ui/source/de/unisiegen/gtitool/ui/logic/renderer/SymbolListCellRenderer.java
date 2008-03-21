package de.unisiegen.gtitool.ui.logic.renderer;


import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import de.unisiegen.gtitool.core.entities.Symbol;


/**
 * The symbol list cell renderer.
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
   * {@inheritDoc}
   * 
   * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList,
   *      java.lang.Object, int, boolean, boolean)
   */
  @Override
  public Component getListCellRendererComponent ( JList list, Object value,
      int index, boolean isSelected, boolean cellHasFocus )
  {
    Component component = super.getListCellRendererComponent ( list, value,
        index, isSelected, cellHasFocus );

    Symbol symbol = ( Symbol ) value;
    component.setForeground ( symbol.toPrettyString ().getPrettyToken ( 0 )
        .getStyle ().getColor () );
    return component;
  }
}
