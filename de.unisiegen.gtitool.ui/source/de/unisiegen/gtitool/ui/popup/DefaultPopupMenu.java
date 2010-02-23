package de.unisiegen.gtitool.ui.popup;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.StateMachinePanel;
import de.unisiegen.gtitool.ui.utils.LayoutManager;


/**
 * The {@link DefaultPopupMenu}.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class DefaultPopupMenu extends JPopupMenu
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 627345294367905600L;


  /**
   * The actual zoom factor
   */
  private int factor;


  /**
   * The {@link StateMachinePanel}
   */
  protected StateMachinePanel panel;


  /**
   * The validate item.
   */
  private JMenuItem validate;


  /**
   * The layout item.
   */
  private JMenuItem layout;


  /**
   * The zoom item.
   */
  private JMenu zoom;


  /**
   * The zoom 50 percent item.
   */
  private JCheckBoxMenuItem zoom50;


  /**
   * The zoom 100 percent item.
   */
  private JCheckBoxMenuItem zoom100;


  /**
   * The zoom 150 percent item.
   */
  private JCheckBoxMenuItem zoom150;


  /**
   * Allocates a new {@link DefaultPopupMenu}.
   * 
   * @param panel The machine panel.
   * @param factor The actual zoom factor.
   */
  public DefaultPopupMenu ( StateMachinePanel panel, int factor )
  {
    this.factor = factor;
    this.panel = panel;
    populateMenues ();
  }


  /**
   * Returns the panel.
   * 
   * @return The panel.
   * @see #panel
   */
  public StateMachinePanel getPanel ()
  {
    return this.panel;
  }


  /**
   * Populates the menues of this popup menu.
   */
  private final void populateMenues ()
  {
    this.zoom = new JMenu ( "Zoom" ); //$NON-NLS-1$
    this.zoom.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/small/zoom.png" ) ) ); //$NON-NLS-1$

    this.zoom50 = new JCheckBoxMenuItem ( "50\u0025" ); //$NON-NLS-1$
    this.zoom50.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        DefaultPopupMenu.this.panel.setZoomFactor ( 0.5 );
      }
    } );
    this.zoom.add ( this.zoom50 );

    this.zoom100 = new JCheckBoxMenuItem ( "100\u0025" ); //$NON-NLS-1$
    this.zoom100.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        DefaultPopupMenu.this.panel.setZoomFactor ( 1 );
      }
    } );
    this.zoom.add ( this.zoom100 );

    this.zoom150 = new JCheckBoxMenuItem ( "150\u0025" ); //$NON-NLS-1$
    this.zoom150.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        DefaultPopupMenu.this.panel.setZoomFactor ( 1.5 );
      }
    } );
    this.zoom.add ( this.zoom150 );

    switch ( this.factor )
    {
      case 50 :
      {
        this.zoom50.setSelected ( true );
        break;
      }
      case 100 :
      {
        this.zoom100.setSelected ( true );
        break;
      }
      case 150 :
      {
        this.zoom150.setSelected ( true );
        break;
      }
    }

    add ( this.zoom );

    this.validate = new JMenuItem ( Messages
        .getString ( "MachinePanel.Validate" ) ); //$NON-NLS-1$
    this.validate.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        DefaultPopupMenu.this.panel.getMainWindow ().handleValidate ();
      }
    } );
    add ( this.validate );

    this.layout = new JMenuItem ( Messages
        .getString ( "MachinePanel.AutoLayout" ) ); //$NON-NLS-1$
    this.layout.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        new LayoutManager ( DefaultPopupMenu.this.panel.getModel (),
            DefaultPopupMenu.this.panel.getRedoUndoHandler () ).doLayout ();
      }
    } );
    add ( this.layout );
  }
}
