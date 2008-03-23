package de.unisiegen.gtitool.ui.logic.renderer;


import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.preferences.PreferenceManager;


/**
 * The {@link TerminalSymbol} list cell renderer.
 * 
 * @author Christian Fehler
 */
public class TerminalSymbolListCellRenderer extends DefaultListCellRenderer
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -3440337249454342945L;


  /**
   * Allocates a new {@link TerminalSymbolListCellRenderer}.
   */
  public TerminalSymbolListCellRenderer ()
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
        .getColorItemTerminalSymbol ().getColor () );
    return component;
  }
}
