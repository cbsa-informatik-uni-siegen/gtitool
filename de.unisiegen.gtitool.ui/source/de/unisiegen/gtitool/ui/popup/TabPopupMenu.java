package de.unisiegen.gtitool.ui.popup;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.MainWindow;


/**
 * The {@link TabPopupMenu}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class TabPopupMenu extends JPopupMenu
{

  /**
   * The {@link TabPopupMenu} type.
   * 
   * @author Christian Fehler
   */
  public enum TabPopupMenuType
  {
    /**
     * The tab is active.
     */
    TAB_ACTIVE,

    /**
     * The tab is deactive.
     */
    TAB_DEACTIVE;
  }


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 5167619665848455832L;


  /**
   * The new item.
   */
  private JMenuItem jMenuItemNew;


  /**
   * The open item.
   */
  private JMenuItem jMenuItemOpen;


  /**
   * The close item.
   */
  private JMenuItem jMenuItemClose;


  /**
   * The close all item.
   */
  private JMenuItem jMenuItemCloseAll;


  /**
   * The save item.
   */
  private JMenuItem jMenuItemSave;


  /**
   * The save as item.
   */
  private JMenuItem jMenuItemSaveAs;


  /**
   * The {@link MainWindow}.
   */
  private MainWindow mainWindow;


  /**
   * The {@link TabPopupMenuType}.
   */
  private TabPopupMenuType tabPopupMenuType;


  /**
   * Allocates a new {@link TabPopupMenu}
   * 
   * @param mainWindow The {@link MainWindow}.
   * @param tabPopupMenuType The {@link TabPopupMenuType}.
   */
  public TabPopupMenu ( MainWindow mainWindow, TabPopupMenuType tabPopupMenuType )
  {
    this.mainWindow = mainWindow;
    this.tabPopupMenuType = tabPopupMenuType;
    populateMenues ();
  }


  /**
   * Populates the menues of this popup menu.
   */
  private final void populateMenues ()
  {
    // New
    this.jMenuItemNew = new JMenuItem ( Messages.getString ( "MainWindow.New" ) ); //$NON-NLS-1$
    this.jMenuItemNew.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/new16.gif" ) ) ); //$NON-NLS-1$
    this.jMenuItemNew.setMnemonic ( Messages.getString (
        "MainWindow.NewMnemonic" ) //$NON-NLS-1$
        .charAt ( 0 ) );
    this.jMenuItemNew.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        TabPopupMenu.this.mainWindow.handleNew ();
      }
    } );
    this.jMenuItemNew.setEnabled ( this.mainWindow.isEnabledNew () );
    add ( this.jMenuItemNew );

    // Open
    this.jMenuItemOpen = new JMenuItem ( Messages
        .getString ( "MainWindow.Open" ) ); //$NON-NLS-1$
    this.jMenuItemOpen.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/open16.png" ) ) ); //$NON-NLS-1$
    this.jMenuItemOpen.setMnemonic ( Messages.getString (
        "MainWindow.OpenMnemonic" ) //$NON-NLS-1$
        .charAt ( 0 ) );
    this.jMenuItemOpen.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        TabPopupMenu.this.mainWindow.handleOpen ();
      }
    } );
    this.jMenuItemOpen.setEnabled ( this.mainWindow.isEnabledOpen () );
    add ( this.jMenuItemOpen );

    // Close
    this.jMenuItemClose = new JMenuItem ( Messages
        .getString ( "MainWindow.Close" ) ); //$NON-NLS-1$
    this.jMenuItemClose.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/empty16.gif" ) ) ); //$NON-NLS-1$
    this.jMenuItemClose.setMnemonic ( Messages.getString (
        "MainWindow.CloseMnemonic" ) //$NON-NLS-1$
        .charAt ( 0 ) );
    this.jMenuItemClose.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        TabPopupMenu.this.mainWindow.handleClose ();
      }
    } );
    this.jMenuItemClose.setEnabled ( this.tabPopupMenuType
        .equals ( TabPopupMenuType.TAB_ACTIVE )
        && this.mainWindow.isEnabledClose () );
    add ( this.jMenuItemClose );

    // CloseAll
    this.jMenuItemCloseAll = new JMenuItem ( Messages
        .getString ( "MainWindow.CloseAll" ) ); //$NON-NLS-1$
    this.jMenuItemCloseAll.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/empty16.gif" ) ) ); //$NON-NLS-1$
    this.jMenuItemCloseAll.setMnemonic ( Messages.getString (
        "MainWindow.CloseAllMnemonic" ) //$NON-NLS-1$
        .charAt ( 0 ) );
    this.jMenuItemCloseAll.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        TabPopupMenu.this.mainWindow.handleCloseAll ();
      }
    } );
    this.jMenuItemCloseAll.setEnabled ( this.mainWindow.isEnabledCloseAll () );
    add ( this.jMenuItemCloseAll );

    add ( new JSeparator () );

    // Save
    this.jMenuItemSave = new JMenuItem ( Messages
        .getString ( "MainWindow.Save" ) ); //$NON-NLS-1$
    this.jMenuItemSave.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/save16.png" ) ) ); //$NON-NLS-1$
    this.jMenuItemSave.setMnemonic ( Messages.getString (
        "MainWindow.SaveMnemonic" ) //$NON-NLS-1$
        .charAt ( 0 ) );
    this.jMenuItemSave.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        TabPopupMenu.this.mainWindow.handleSave ();
      }
    } );
    this.jMenuItemSave.setEnabled ( this.tabPopupMenuType
        .equals ( TabPopupMenuType.TAB_ACTIVE )
        && this.mainWindow.isEnabledSave () );
    add ( this.jMenuItemSave );

    // SaveAs
    this.jMenuItemSaveAs = new JMenuItem ( Messages
        .getString ( "MainWindow.SaveAs" ) ); //$NON-NLS-1$
    this.jMenuItemSaveAs.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/saveas16.png" ) ) ); //$NON-NLS-1$
    this.jMenuItemSaveAs.setMnemonic ( Messages.getString (
        "MainWindow.SaveAsMnemonic" ) //$NON-NLS-1$
        .charAt ( 0 ) );
    this.jMenuItemSaveAs.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        TabPopupMenu.this.mainWindow.handleSaveAs ();
      }
    } );
    this.jMenuItemSaveAs.setEnabled ( this.tabPopupMenuType
        .equals ( TabPopupMenuType.TAB_ACTIVE )
        && this.mainWindow.isEnabledSaveAs () );
    add ( this.jMenuItemSaveAs );
  }
}
