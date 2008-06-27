package de.unisiegen.gtitool.core.parser.style.renderer;


import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyStringComponent;


/**
 * The {@link PrettyPrintable} {@link ListCellRenderer}.
 * 
 * @author Christian Fehler
 * @version $Id: PrettyStringListCellRenderer.java 811 2008-04-18 13:52:03Z
 *          fehler $
 */
public class PrettyStringListCellRenderer extends DefaultListCellRenderer
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6665907708097614503L;


  /**
   * The {@link PrettyStringComponent}.
   */
  private PrettyStringComponent prettyStringComponent;


  /**
   * Allocates a new {@link PrettyStringListCellRenderer}.
   */
  public PrettyStringListCellRenderer ()
  {
    super ();
    this.prettyStringComponent = new PrettyStringComponent ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see DefaultListCellRenderer#getListCellRendererComponent(JList, Object,
   *      int, boolean, boolean)
   */
  @Override
  public Component getListCellRendererComponent ( JList list, Object value,
      @SuppressWarnings ( "unused" ) int index, boolean isSelected,
      @SuppressWarnings ( "unused" ) boolean cellHasFocus )
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

    this.prettyStringComponent.setPrettyString ( prettyString );

    this.prettyStringComponent.setComponentOrientation ( list
        .getComponentOrientation () );
    if ( isSelected )
    {
      this.prettyStringComponent
          .setBackground ( list.getSelectionBackground () );
      this.prettyStringComponent
          .setForeground ( list.getSelectionForeground () );
    }
    else
    {
      this.prettyStringComponent.setBackground ( list.getBackground () );
      this.prettyStringComponent.setForeground ( list.getForeground () );
    }

    this.prettyStringComponent.setEnabled ( list.isEnabled () );
    this.prettyStringComponent.setFont ( list.getFont () );

    return this.prettyStringComponent;
  }
}
