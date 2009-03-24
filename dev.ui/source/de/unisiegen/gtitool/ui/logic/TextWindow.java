package de.unisiegen.gtitool.ui.logic;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.filechooser.FileFilter;

import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.netbeans.TextForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


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
   * The {@link MainWindowForm}
   */
  private MainWindowForm mainWindowForm;


  /**
   * Creates a new TextWindow
   * 
   * @param mainWindowForm The {@link MainWindowForm}
   * @param text The text to display
   * @param algorithm True if an algorithm should be shown
   */
  public TextWindow ( MainWindowForm mainWindowForm, String text,
      boolean algorithm )
  {
    this.mainWindowForm = mainWindowForm;
    this.text = text;
    String title = Messages.getString ( "TextWindow.TitleAlgorithm" ); //$NON-NLS-1$
    if ( !algorithm )
    {
      title = Messages.getString ( "TextWindow.TitleRDP" ); //$NON-NLS-1$
    }
    this.gui = new TextForm ( this, true, title );
  }


  /**
   * Shows the Dialog
   */
  public void show ()
  {
    this.gui.setLocationRelativeTo ( this.mainWindowForm );
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
        return Messages.getString ( "TextWindow.FileDescription"); //$NON-NLS-1$
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
      ConfirmDialog confirmDialog = new ConfirmDialog ( this.mainWindowForm,
          Messages.getString (
              "MachinePanel.FileExists", saveDialog.getSelectedFile () //$NON-NLS-1$
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
    //TODO
  }


  /**
   * Returns the mainWindowForm.
   * 
   * @return The mainWindowForm.
   * @see #mainWindowForm
   */
  public MainWindowForm getMainWindowForm ()
  {
    return this.mainWindowForm;
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
