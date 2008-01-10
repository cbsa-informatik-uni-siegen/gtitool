package de.unisiegen.gtitool.ui.popup;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.unisiegen.gtitool.core.exceptions.CoreException.ErrorType;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineValidationException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.ui.logic.MachinePanel;


/**
 * Create a new Default Popup Menu
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class DefaultPopupMenu extends JPopupMenu
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 627345294367905600L;


  /** The {@link Machine} */
  private Machine machine;


  /**
   * The zoom item
   */
  private JMenu zoom;


  /**
   * The zoom 50 % item
   */
  private JCheckBoxMenuItem zoom50;


  /**
   * The zoom 75 % item
   */
  private JCheckBoxMenuItem zoom75;


  /**
   * The zoom 100 % item
   */
  private JCheckBoxMenuItem zoom100;


  /**
   * The zoom 125 % item
   */
  private JCheckBoxMenuItem zoom125;


  /**
   * The zoom 150 % item
   */
  private JCheckBoxMenuItem zoom150;


  /**
   * The zoom 175 % item
   */
  private JCheckBoxMenuItem zoom175;


  /**
   * The zoom 200 % item
   */
  private JCheckBoxMenuItem zoom200;


  /**
   * The validate item
   */
  private JMenuItem validate;


  /**
   * The actual zoom factor
   */
  private int factor;


  /**
   * The {@link MachinePanel}
   */
  MachinePanel panel;


  /**
   * Allocate a new {@link DefaultPopupMenu}
   * 
   * @param pPanel the machine panel
   * @param pMachine The {@link Machine}
   * @param pFactor the actual zoom factor
   */
  public DefaultPopupMenu ( MachinePanel pPanel, Machine pMachine, int pFactor )
  {
    this.machine = pMachine;
    this.factor = pFactor;
    this.panel = pPanel;
    populateMenues ();
  }


  /**
   * Populates the menues of this popup menu.
   */
  protected void populateMenues ()
  {
    this.zoom = new JMenu ( "Zoom" ); //$NON-NLS-1$
    this.zoom.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/popupMenu/zoom.png" ) ) ); //$NON-NLS-1$

    this.zoom50 = new JCheckBoxMenuItem ( "50% " ); //$NON-NLS-1$
    this.zoom50.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent e )
      {
        DefaultPopupMenu.this.panel.setZoomFactor ( 0.5 );
      }
    } );
    this.zoom.add ( this.zoom50 );

    this.zoom75 = new JCheckBoxMenuItem ( "75%" ); //$NON-NLS-1$
    this.zoom75.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent e )
      {
        DefaultPopupMenu.this.panel.setZoomFactor ( 0.75 );
      }
    } );
    this.zoom.add ( this.zoom75 );

    this.zoom100 = new JCheckBoxMenuItem ( "100%" ); //$NON-NLS-1$
    this.zoom100.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent e )
      {
        DefaultPopupMenu.this.panel.setZoomFactor ( 1 );
      }
    } );
    this.zoom.add ( this.zoom100 );

    this.zoom125 = new JCheckBoxMenuItem ( "125%" ); //$NON-NLS-1$
    this.zoom125.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent e )
      {
        DefaultPopupMenu.this.panel.setZoomFactor ( 1.25 );
      }
    } );
    this.zoom.add ( this.zoom125 );

    this.zoom150 = new JCheckBoxMenuItem ( "150%" ); //$NON-NLS-1$
    this.zoom150.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent e )
      {
        DefaultPopupMenu.this.panel.setZoomFactor ( 1.5 );
      }
    } );
    this.zoom.add ( this.zoom150 );

    this.zoom175 = new JCheckBoxMenuItem ( "175%" ); //$NON-NLS-1$
    this.zoom175.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent e )
      {
        DefaultPopupMenu.this.panel.setZoomFactor ( 1.75 );
      }
    } );
    this.zoom.add ( this.zoom175 );

    this.zoom200 = new JCheckBoxMenuItem ( "200%" ); //$NON-NLS-1$
    this.zoom200.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent e )
      {
        DefaultPopupMenu.this.panel.setZoomFactor ( 2 );
      }
    } );
    this.zoom.add ( this.zoom200 );

    add ( this.zoom );

    this.validate = new JMenuItem ( "Validate" ); //$NON-NLS-1$
    this.validate.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent e )
      {
        try
        {
          DefaultPopupMenu.this.panel.clearValidationMessages();
          DefaultPopupMenu.this.machine.validate ();
          
        }
        catch ( MachineValidationException e1 )
        {
          for ( MachineException error : e1.getMachineException () )
          {
            if ( error.getType ().equals ( ErrorType.ERROR ) )
            {
              DefaultPopupMenu.this.panel.addError ( error );
            }
            else if ( error.getType ().equals ( ErrorType.WARNING ) )
            {
              DefaultPopupMenu.this.panel.addWarning ( error );
            }
          }
        }
      }
    } );
    add ( this.validate );

    switch ( this.factor )
    {
      case 50 :
        this.zoom50.setSelected ( true );
        break;
      case 75 :
        this.zoom75.setSelected ( true );
        break;
      case 100 :
        this.zoom100.setSelected ( true );
        break;
      case 125 :
        this.zoom125.setSelected ( true );
        break;
      case 150 :
        this.zoom150.setSelected ( true );
        break;
      case 175 :
        this.zoom175.setSelected ( true );
        break;
      case 200 :
        this.zoom200.setSelected ( true );
        break;
    }
  }

}
