package de.unisiegen.gtitool.ui.logic;


import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;

import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.SaveDialogForm;


/**
 * The {@link SaveDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class SaveDialog implements LogicClass < SaveDialogForm >
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( SaveDialog.class );


  /**
   * The {@link SaveDialogForm}.
   */
  private SaveDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * Flag that indicates if the dialog was confirmed.
   */
  private boolean confirmed = false;


  /**
   * Allocates a new {@link SaveDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param workingPath The working path.
   * @param fileFilter The selected {@link FileFilter}.
   * @param choosableFileFilter The choosable {@link FileFilter}s.
   */
  public SaveDialog ( JFrame parent, String workingPath, FileFilter fileFilter,
      FileFilter ... choosableFileFilter )
  {
    logger.debug ( "SaveDialog", "allocate a new save dialog" ); //$NON-NLS-1$ //$NON-NLS-2$

    this.parent = parent;
    this.gui = new SaveDialogForm ( this, parent );
    this.gui.jGTIFileChooser.setCurrentDirectory ( new File ( workingPath ) );

    boolean found = false;
    for ( FileFilter current : choosableFileFilter )
    {
      if ( current == fileFilter )
      {
        found = true;
        break;
      }
    }
    if ( !found )
    {
      throw new IllegalArgumentException (
          "the file filter must be a member of the choosable filter" ); //$NON-NLS-1$
    }

    for ( FileFilter current : choosableFileFilter )
    {
      this.gui.jGTIFileChooser.addChoosableFileFilter ( current );
    }
    this.gui.jGTIFileChooser.setFileFilter ( fileFilter );
  }


  /**
   * Approve this {@link SaveDialog}.
   */
  public final void approve ()
  {
    logger.debug ( "approve", "approve" ); //$NON-NLS-1$ //$NON-NLS-2$

    if ( !getSelectedFile ().isDirectory () )
    {
      this.confirmed = true;
      this.gui.dispose ();
    }
  }


  /**
   * Returns the current directory.
   * 
   * @return The current directory.
   */
  public final File getCurrentDirectory ()
  {
    return this.gui.jGTIFileChooser.getCurrentDirectory ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final SaveDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Returns the selected {@link File}.
   * 
   * @return The selected {@link File}.
   */
  public final File getSelectedFile ()
  {
    return this.gui.jGTIFileChooser.getSelectedFile ();
  }


  /**
   * Returns the selected {@link File}s.
   * 
   * @return The selected {@link File}s.
   */
  public final ArrayList < File > getSelectedFiles ()
  {
    ArrayList < File > result = new ArrayList < File > ();
    for ( File current : this.gui.jGTIFileChooser.getSelectedFiles () )
    {
      result.add ( current );
    }
    return result;
  }


  /**
   * Closes the {@link SaveDialogForm}.
   */
  public final void handleCancel ()
  {
    logger.debug ( "handleCancel", "handle cancel" ); //$NON-NLS-1$ //$NON-NLS-2$

    this.confirmed = false;
    this.gui.setVisible ( false );
  }


  /**
   * Handles the save event.
   */
  public final void handleSave ()
  {
    logger.debug ( "handleSave", "handle save" ); //$NON-NLS-1$ //$NON-NLS-2$

    if ( this.gui.jGTIFileChooser.getUI () instanceof BasicFileChooserUI )
    {
      BasicFileChooserUI ui = ( BasicFileChooserUI ) this.gui.jGTIFileChooser
          .getUI ();
      ui.getApproveSelectionAction ().actionPerformed ( null );
    }
    else
    {
      throw new RuntimeException ( "ui is not a BasicFileChooserUI" ); //$NON-NLS-1$
    }

    if ( getSelectedFile () != null && !getSelectedFile ().isDirectory () )
    {
      this.confirmed = true;
      this.gui.dispose ();
    }
  }


  /**
   * Returns the confirmed.
   * 
   * @return The confirmed.
   * @see #confirmed
   */
  public final boolean isConfirmed ()
  {
    return this.confirmed;
  }


  /**
   * Shows the {@link SaveDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the save dialog" ); //$NON-NLS-1$ //$NON-NLS-2$

    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
