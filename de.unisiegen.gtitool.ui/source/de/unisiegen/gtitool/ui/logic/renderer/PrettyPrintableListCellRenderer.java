package de.unisiegen.gtitool.ui.logic.renderer;


import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.ui.style.PrettyPrintableComponent;


/**
 * The {@link PrettyPrintable} list cell renderer.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 */
public class PrettyPrintableListCellRenderer extends DefaultListCellRenderer
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6665907708097614503L;


  /**
   * Allocates a new {@link PrettyPrintableListCellRenderer}.
   */
  public PrettyPrintableListCellRenderer ()
  {
    super ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see DefaultListCellRenderer#getListCellRendererComponent(JList, Object,
   *      int, boolean, boolean)
   */
  @Override
  public Component getListCellRendererComponent ( JList list, Object value,
      @SuppressWarnings ( "unused" )
      int index, boolean isSelected, @SuppressWarnings ( "unused" )
      boolean cellHasFocus )
  {
    PrettyPrintable prettyPrintable = ( PrettyPrintable ) value;
    PrettyPrintableComponent component = new PrettyPrintableComponent (
        prettyPrintable );

    component.setComponentOrientation ( list.getComponentOrientation () );
    if ( isSelected )
    {
      component.setBackground ( list.getSelectionBackground () );
      component.setForeground ( list.getSelectionForeground () );
    }
    else
    {
      component.setBackground ( list.getBackground () );
      component.setForeground ( list.getForeground () );
    }

    component.setEnabled ( list.isEnabled () );
    component.setFont ( list.getFont () );

    return component;
  }
}
