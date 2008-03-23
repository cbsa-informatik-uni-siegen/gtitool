package de.unisiegen.gtitool.ui.logic.renderer;


import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.preferences.PreferenceManager;


/**
 * The {@link NonterminalSymbol} list cell renderer.
 * 
 * @author Christian Fehler
 */
public class NonterminalSymbolListCellRenderer extends DefaultListCellRenderer
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 4408718231403697897L;


  /**
   * Allocates a new {@link NonterminalSymbolListCellRenderer}.
   */
  public NonterminalSymbolListCellRenderer ()
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
        .getColorItemNonterminalSymbol ().getColor () );
    return component;
  }
}
