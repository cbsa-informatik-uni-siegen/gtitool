package de.unisiegen.gtitool.ui.logic;


import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;

import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.netbeans.OpenDialogForm;


/**
 * The {@link OpenDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class OpenDialog
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( OpenDialog.class );


  /**
   * The {@link OpenDialogForm}.
   */
  private OpenDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * Flag that indicates if the dialog was confirmed.
   */
  private boolean confirmed = false;


  /**
   * Allocates a new {@link OpenDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param workingPath The working path.
   */
  public OpenDialog ( JFrame parent, String workingPath )
  {
    logger.debug ( "OpenDialog", "allocate a new open dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.parent = parent;
    this.gui = new OpenDialogForm ( this, parent );
    this.gui.jGTIFileChooser.setCurrentDirectory ( new File ( workingPath ) );

    // Source files
    FileFilter sourceFileFilter = new FileFilter ()
    {

      @Override
      public boolean accept ( File file )
      {
        if ( file.isDirectory () )
        {
          return true;
        }
        for ( String current : Machine.AVAILABLE_MACHINES )
        {
          if ( file.getName ().toLowerCase ().matches (
              ".+\\." + current.toLowerCase () ) ) //$NON-NLS-1$
          {
            return true;
          }
        }
        for ( String current : Grammar.AVAILABLE_GRAMMARS )
        {
          if ( file.getName ().toLowerCase ().matches (
              ".+\\." + current.toLowerCase () ) ) //$NON-NLS-1$
          {
            return true;
          }
        }
        return false;
      }


      @Override
      public String getDescription ()
      {
        StringBuilder result = new StringBuilder ();
        result.append ( Messages.getString ( "OpenDialog.FilterFiles" ) ); //$NON-NLS-1$
        result.append ( " (" ); //$NON-NLS-1$
        for ( int i = 0 ; i < Machine.AVAILABLE_MACHINES.length ; i++ )
        {
          result.append ( "*." ); //$NON-NLS-1$
          result.append ( Machine.AVAILABLE_MACHINES [ i ].toLowerCase () );
          if ( i != Machine.AVAILABLE_MACHINES.length - 1 )
          {
            result.append ( "; " ); //$NON-NLS-1$
          }
        }
        if ( ( Machine.AVAILABLE_MACHINES.length > 0 )
            && ( Grammar.AVAILABLE_GRAMMARS.length > 0 ) )
        {
          result.append ( "; " ); //$NON-NLS-1$
        }
        for ( int i = 0 ; i < Grammar.AVAILABLE_GRAMMARS.length ; i++ )
        {
          result.append ( "*." ); //$NON-NLS-1$
          result.append ( Grammar.AVAILABLE_GRAMMARS [ i ].toLowerCase () );
          if ( i != Grammar.AVAILABLE_GRAMMARS.length - 1 )
          {
            result.append ( "; " ); //$NON-NLS-1$
          }
        }
        result.append ( ")" ); //$NON-NLS-1$
        return result.toString ();
      }
    };

    // Machine files
    FileFilter machineFileFilter = new FileFilter ()
    {

      @Override
      public boolean accept ( File file )
      {
        if ( file.isDirectory () )
        {
          return true;
        }
        for ( String current : Machine.AVAILABLE_MACHINES )
        {
          if ( file.getName ().toLowerCase ().matches (
              ".+\\." + current.toLowerCase () ) ) //$NON-NLS-1$
          {
            return true;
          }
        }
        return false;
      }


      @Override
      public String getDescription ()
      {
        StringBuilder result = new StringBuilder ();
        result.append ( Messages.getString ( "OpenDialog.FilterFilesMachine" ) ); //$NON-NLS-1$
        result.append ( " (" ); //$NON-NLS-1$
        for ( int i = 0 ; i < Machine.AVAILABLE_MACHINES.length ; i++ )
        {
          result.append ( "*." ); //$NON-NLS-1$
          result.append ( Machine.AVAILABLE_MACHINES [ i ].toLowerCase () );
          if ( i != Machine.AVAILABLE_MACHINES.length - 1 )
          {
            result.append ( "; " ); //$NON-NLS-1$
          }
        }
        result.append ( ")" ); //$NON-NLS-1$
        return result.toString ();
      }
    };

    // Grammar files
    FileFilter grammarFileFilter = new FileFilter ()
    {

      @Override
      public boolean accept ( File file )
      {
        if ( file.isDirectory () )
        {
          return true;
        }
        for ( String current : Grammar.AVAILABLE_GRAMMARS )
        {
          if ( file.getName ().toLowerCase ().matches (
              ".+\\." + current.toLowerCase () ) ) //$NON-NLS-1$
          {
            return true;
          }
        }
        return false;
      }


      @Override
      public String getDescription ()
      {
        StringBuilder result = new StringBuilder ();
        result.append ( Messages.getString ( "OpenDialog.FilterFilesGrammar" ) ); //$NON-NLS-1$
        result.append ( " (" ); //$NON-NLS-1$
        for ( int i = 0 ; i < Grammar.AVAILABLE_GRAMMARS.length ; i++ )
        {
          result.append ( "*." ); //$NON-NLS-1$
          result.append ( Grammar.AVAILABLE_GRAMMARS [ i ].toLowerCase () );
          if ( i != Grammar.AVAILABLE_GRAMMARS.length - 1 )
          {
            result.append ( "; " ); //$NON-NLS-1$
          }
        }
        result.append ( ")" ); //$NON-NLS-1$
        return result.toString ();
      }
    };

    this.gui.jGTIFileChooser.addChoosableFileFilter ( sourceFileFilter );
    this.gui.jGTIFileChooser.addChoosableFileFilter ( machineFileFilter );
    this.gui.jGTIFileChooser.addChoosableFileFilter ( grammarFileFilter );
    this.gui.jGTIFileChooser.setFileFilter ( sourceFileFilter );
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
   * Closes the {@link OpenDialogForm}.
   */
  public final void handleCancel ()
  {
    logger.debug ( "handleCancel", "handle cancel" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.confirmed = false;
    this.gui.dispose ();
  }


  /**
   * Handles the open event.
   */
  public final void handleOpen ()
  {
    logger.debug ( "handleOpen", "handle open" ); //$NON-NLS-1$ //$NON-NLS-2$

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
    if ( !getSelectedFile ().isDirectory () )
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
   * Shows the {@link OpenDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the open dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }

  /**
   * Approve this dialog.
   */
  public void approve ()
  {
    if ( !getSelectedFile ().isDirectory () )
    {
      this.confirmed = true;
      this.gui.dispose ();
    }
  }
}
