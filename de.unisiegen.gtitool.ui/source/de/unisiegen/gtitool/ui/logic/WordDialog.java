package de.unisiegen.gtitool.ui.logic;


import org.apache.log4j.Logger;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.ui.netbeans.WordDialogForm;
import de.unisiegen.gtitool.ui.style.listener.WordChangedListener;


/**
 * The <code>WordDialog</code>.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class WordDialog
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( WordDialog.class );


  /**
   * The {@link WordDialogForm}.
   */
  private WordDialogForm gui;


  /**
   * The {@link MainWindow}.
   */
  private MainWindow mainWindow;


  /**
   * The used {@link Alphabet}.
   */
  private Alphabet alphabet;


  /**
   * Allocates a new <code>AboutDialog</code>.
   * 
   * @param pMainWindow The {@link MainWindow}.
   */
  public WordDialog ( MainWindow pMainWindow )
  {
    logger.debug ( "allocate a new word dialog" ); //$NON-NLS-1$
    this.mainWindow = pMainWindow;
    this.gui = new WordDialogForm ( this, this.mainWindow.getGui () );
    this.alphabet = this.mainWindow.getSelectedAlphabet ();
    this.gui.styledAlphabetParserPanel.setAlphabet ( this.alphabet );
    this.gui.styledWordParserPanel.setAlphabet ( this.alphabet );
    this.gui.styledWordParserPanel
        .addWordChangedListener ( new WordChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void wordChanged ( Word pNewWord )
          {
            if ( pNewWord == null )
            {
              WordDialog.this.gui.jButtonOk.setEnabled ( false );
            }
            else
            {
              WordDialog.this.gui.jButtonOk.setEnabled ( true );
            }
          }
        } );
  }


  /**
   * Handles the action on the <code>Cancel</code> button.
   */
  public final void handleCancel ()
  {
    logger.debug ( "handle cancel" ); //$NON-NLS-1$
    this.gui.setVisible ( false );
    this.gui.dispose ();
  }


  /**
   * Handles the action on the <code>Accept</code> button.
   */
  public final void handleAccept ()
  {
    logger.debug ( "handle accept" ); //$NON-NLS-1$
  }


  /**
   * Shows the {@link WordDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show the word dialog" ); //$NON-NLS-1$
    int x = this.mainWindow.getGui ().getBounds ().x
        + ( this.mainWindow.getGui ().getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.mainWindow.getGui ().getBounds ().y
        + ( this.mainWindow.getGui ().getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
