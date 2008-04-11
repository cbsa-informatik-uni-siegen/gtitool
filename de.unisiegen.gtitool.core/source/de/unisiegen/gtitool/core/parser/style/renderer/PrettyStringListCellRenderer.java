package de.unisiegen.gtitool.core.parser.style.renderer;


import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyStringComponent;


/**
 * The {@link PrettyPrintable} list cell renderer.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 */
public class PrettyStringListCellRenderer extends DefaultListCellRenderer
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6665907708097614503L;


  /**
   * Allocates a new {@link PrettyStringListCellRenderer}.
   */
  public PrettyStringListCellRenderer ()
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
    PrettyString prettyString = null;
    if ( value instanceof PrettyPrintable )
    {
      prettyString = ( ( PrettyPrintable ) value ).toPrettyString ();
    }
    else if ( value instanceof PrettyString )
    {
      prettyString = ( PrettyString ) value;
    }
    else
    {
      throw new IllegalArgumentException ( "the value can not be renderer" ); //$NON-NLS-1$
    }

    PrettyStringComponent component = new PrettyStringComponent ( prettyString );

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
