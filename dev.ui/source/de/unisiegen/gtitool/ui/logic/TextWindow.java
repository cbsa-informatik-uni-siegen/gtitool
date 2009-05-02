package de.unisiegen.gtitool.ui.logic;


import java.awt.Rectangle;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.netbeans.TextForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.swing.JGTIToggleButton;


/**
 * The {@link LogicClass} for the {@link TextForm}
 */
public class TextWindow implements LogicClass < TextForm >
{

  /**
   * The gui class
   */
  private TextForm gui;


  /**
   * The content
   */
  private String text;


  /**
   * The parent {@link Window}
   */
  private Window parent;


  /**
   * The {@link JGTIToggleButton}
   */
  private JGTIToggleButton toggleButton;


  /**
   * True if an algorithm should be shown
   */
  private boolean algorithm;


  /**
   * Creates a new TextWindow
   * 
   * @param parent The paren {@link Window}
   * @param text The text to display
   * @param algorithm True if an algorithm should be shown
   * @param toggleButton The toggle button
   */
  public TextWindow ( Window parent, String text, boolean algorithm,
      JGTIToggleButton toggleButton )
  {
    this.parent = parent;
    this.text = text;
    this.algorithm = algorithm;
    this.toggleButton = toggleButton;
    String title = Messages.getString ( "TextWindow.TitleAlgorithm" ); //$NON-NLS-1$
    if ( !algorithm )
    {
      title = Messages.getString ( "TextWindow.TitleRDP" ); //$NON-NLS-1$
    }
    if ( this.parent instanceof JDialog )
    {
      this.gui = new TextForm ( ( JDialog ) parent, this, !algorithm, title );
    }
    else if ( this.parent instanceof JFrame )
    {
      this.gui = new TextForm ( ( JFrame ) parent, this, !algorithm, title );
    }
    else
    {
      throw new IllegalArgumentException ( "unsupported window" ); //$NON-NLS-1$
    }
  }


  /**
   * Shows the Dialog
   */
  public void show ()
  {
    Rectangle rect;
    if ( this.algorithm )
    {
      rect = PreferenceManager.getInstance ().getAlgorithmDialogBounds ();
      if ( ( rect.x == PreferenceManager.DEFAULT_ALGORITHM_DIALOG_POSITION_X )
          || ( rect.y == PreferenceManager.DEFAULT_ALGORITHM_DIALOG_POSITION_Y ) )
      {
        rect.x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
            - ( this.gui.getWidth () / 2 );
        rect.y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
            - ( this.gui.getHeight () / 2 );
      }
    }
    else
    {
      rect = PreferenceManager.getInstance ().getRDPDialogBounds ();
      if ( ( rect.x == PreferenceManager.DEFAULT_RDP_DIALOG_POSITION_X )
          || ( rect.y == PreferenceManager.DEFAULT_RDP_DIALOG_POSITION_Y ) )
      {
        rect.x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
            - ( this.gui.getWidth () / 2 );
        rect.y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
            - ( this.gui.getHeight () / 2 );
      }
    }
    this.gui.setBounds ( rect );
    this.gui.setVisible ( true );
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public TextForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Dispose the {@link MainWindowForm}
   */
  public void dispose ()
  {
    if ( this.algorithm )
    {
      PreferenceManager.getInstance ()
          .setAlgorithmDialogPreferences ( this.gui );
    }
    else
    {
      PreferenceManager.getInstance ().setRDPDialogPreferences ( this.gui );
    }
    this.gui.dispose ();
  }


  /**
   * Handles the GUI closed event
   */
  public void handleGUIClosed ()
  {
    if ( this.algorithm )
    {
      PreferenceManager.getInstance ()
          .setAlgorithmDialogPreferences ( this.gui );
    }
    else
    {
      PreferenceManager.getInstance ().setRDPDialogPreferences ( this.gui );
    }

    if ( this.toggleButton != null )
    {
      this.toggleButton.setSelected ( false );
    }
  }


  /**
   * Handles the save button clicked
   */
  public void handleSave ()
  {
    FileFilter fileFilter = new FileFilter ()
    {

      @Override
      public boolean accept ( File acceptedFile )
      {
        if ( acceptedFile.isDirectory () )
        {
          return true;
        }
        if ( acceptedFile.getName ().toLowerCase ().matches ( ".+\\.txt" ) ) //$NON-NLS-1$
        {
          return true;
        }
        return false;
      }


      @Override
      public String getDescription ()
      {
        return Messages.getString ( "TextWindow.FileDescription" ); //$NON-NLS-1$
      }
    };

    SaveDialog saveDialog = new SaveDialog ( this.gui, PreferenceManager
        .getInstance ().getWorkingPath (), fileFilter, fileFilter );
    saveDialog.show ();

    if ( ( !saveDialog.isConfirmed () )
        || ( saveDialog.getSelectedFile () == null ) )
    {
      return;
    }

    if ( saveDialog.getSelectedFile ().exists () )
    {
      ConfirmDialog confirmDialog = new ConfirmDialog ( this.gui, Messages
          .getString ( "MachinePanel.FileExists", saveDialog.getSelectedFile () //$NON-NLS-1$
              .getName () ), Messages.getString ( "MachinePanel.Save" ), //$NON-NLS-1$
          true, false, true, false, false );
      confirmDialog.show ();
      if ( confirmDialog.isNotConfirmed () )
      {
        return;
      }
    }
    String filename = saveDialog.getSelectedFile ().toString ().matches (
        ".+\\.txt" ) ? saveDialog //$NON-NLS-1$
        .getSelectedFile ().toString () : saveDialog.getSelectedFile ()
        .toString ()
        + ".txt"; //$NON-NLS-1$

    BufferedWriter bw = null;
    BufferedReader br = null;

    // create targetFile
    File targetFile = new File ( filename );

    // write content to targetFile
    try
    {
      br = new BufferedReader ( new StringReader ( this.text ) );
      bw = new BufferedWriter ( new FileWriter ( targetFile ) );

      String temp = br.readLine ();

      while ( temp != null )
      {
        bw.write ( temp );
        bw.newLine ();
        temp = br.readLine ();
      }

      bw.close ();
    }
    catch ( IOException e )
    {
      e.printStackTrace ();
    }

    PreferenceManager.getInstance ().setWorkingPath (
        saveDialog.getCurrentDirectory ().getAbsolutePath () );
  }


  /**
   * Handles the print button clicked
   */
  public void handlePrint ()
  {
    // TODO
  }


  /**
   * Returns the text.
   * 
   * @return The text.
   * @see #text
   */
  public String getText ()
  {
    return this.text;
  }

}
