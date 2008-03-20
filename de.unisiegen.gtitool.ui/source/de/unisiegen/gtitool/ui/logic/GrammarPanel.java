package de.unisiegen.gtitool.ui.logic;


import java.awt.Component;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;
import javax.swing.filechooser.FileFilter;

import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.exchange.Exchange;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;
import de.unisiegen.gtitool.ui.model.DefaultModel;
import de.unisiegen.gtitool.ui.model.GrammarColumnModel;
import de.unisiegen.gtitool.ui.netbeans.GrammarPanelForm;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.netbeans.helperclasses.EditorPanelForm;
import de.unisiegen.gtitool.ui.popup.ProductionPopupMenu;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.storage.Storage;


/**
 * @author Benjamin Mies
 * @version $Id$
 */
public class GrammarPanel implements EditorPanel
{

  /**
   * The {@link GrammarPanelForm}.
   */
  private GrammarPanelForm gui;


  /**
   * The {@link DefaultGrammarModel}.
   */
  private DefaultGrammarModel model;


  /**
   * The {@link Grammar}.
   */
  private Grammar grammar;


  /**
   * The name of this {@link MachinePanel}.
   */
  private String name = null;


  /**
   * The {@link File} for this {@link MachinePanel}.
   */
  private File file;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The {@link MainWindowForm}
   */
  private MainWindowForm mainWindowForm;


  /**
   * The {@link ModifyStatusChangedListener}.
   */
  private ModifyStatusChangedListener modifyStatusChangedListener;


  /**
   * Allocates a new {@link GrammarPanel}
   * 
   * @param mainWindowForm The {@link MainWindowForm}.
   * @param model The {@link DefaultGrammarModel}.
   * @param file The {@link File}
   */
  public GrammarPanel ( MainWindowForm mainWindowForm,
      DefaultGrammarModel model, File file )
  {
    this.mainWindowForm = mainWindowForm;
    this.model = model;
    this.file = file;
    this.gui = new GrammarPanelForm ();
    this.gui.setGrammarPanel ( this );
    this.grammar = this.model.getGrammar ();
    this.gui.jGTITable.setModel ( this.grammar );
    this.gui.jGTITable.setColumnModel ( new GrammarColumnModel () );

    if ( this.grammar.getColumnCount () > 0 )
    {
      this.gui.jGTITable.getSelectionModel ().setSelectionInterval ( 0, 0 );
    }

    // ModifyStatusChangedListener
    this.modifyStatusChangedListener = new ModifyStatusChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void modifyStatusChanged ( boolean modified )
      {
        fireModifyStatusChanged ( modified );
      }
    };
    this.model
        .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#addModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#getFile()
   */
  public File getFile ()
  {
    return this.file;
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#getFileEnding()
   */
  public String getFileEnding ()
  {
    return "." + this.grammar.getGrammarType ().toLowerCase (); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.EditorPanel#getGui()
   */
  public EditorPanelForm getGui ()
  {
    return this.gui;
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#getName()
   */
  public String getName ()
  {
    return this.file == null ? this.name : this.file.getName ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#getPanel()
   */
  public JPanel getPanel ()
  {
    return this.gui;
  }


  /**
   * Handles the {@link Exchange}.
   */
  public final void handleExchange ()
  {
    ExchangeDialog exchangeDialog = new ExchangeDialog ( this.mainWindowForm
        .getLogic (), this.model.getElement (), this.file );
    exchangeDialog.show ();
  }


  /**
   * Handle redo button pressed
   */
  public void handleRedo ()
  {
    // TODO implement me
  }


  /**
   * Handle save as operation
   * 
   * @return filename
   */
  public final File handleSave ()
  {
    if ( this.file == null )
    {
      return handleSaveAs ();
    }
    try
    {
      Storage.getInstance ().store ( this.model, this.file );
    }
    catch ( StoreException e )
    {
      InfoDialog infoDialog = new InfoDialog ( this.mainWindowForm, e.getMessage (),
          Messages.getString ( "MachinePanel.Save" ) ); //$NON-NLS-1$
      infoDialog.show ();
    }
    resetModify ();
    fireModifyStatusChanged ( false );
    return this.file;
  }


  /**
   * Handle save as operation
   * 
   * @return filename
   */
  public final File handleSaveAs ()
  {
    try
    {
      PreferenceManager prefmanager = PreferenceManager.getInstance ();
      JFileChooser chooser = new JFileChooser ( prefmanager.getWorkingPath () );
      chooser.setMultiSelectionEnabled ( false );
      chooser.setAcceptAllFileFilterUsed ( false );
      chooser.addChoosableFileFilter ( new FileFilter ()
      {

        @SuppressWarnings ( "synthetic-access" )
        @Override
        public boolean accept ( File acceptedFile )
        {
          if ( acceptedFile.isDirectory () )
          {
            return true;
          }
          if ( acceptedFile.getName ().toLowerCase ().matches ( ".+\\." //$NON-NLS-1$
              + GrammarPanel.this.grammar.getGrammarType ().toLowerCase () ) )
          {
            return true;
          }
          return false;
        }


        @SuppressWarnings ( "synthetic-access" )
        @Override
        public String getDescription ()
        {
          return Messages.getString ( "NewDialog." //$NON-NLS-1$
              + GrammarPanel.this.grammar.getGrammarType () )
              + " (*." //$NON-NLS-1$
              + GrammarPanel.this.grammar.getGrammarType ().toLowerCase ()
              + ")"; //$NON-NLS-1$
        }
      } );
      int n = chooser.showSaveDialog ( this.mainWindowForm );
      if ( ( n == JFileChooser.CANCEL_OPTION )
          || ( chooser.getSelectedFile () == null ) )
      {
        return null;
      }
      if ( chooser.getSelectedFile ().exists () )
      {
        ConfirmDialog confirmDialog = new ConfirmDialog ( this.mainWindowForm, Messages
            .getString ( "MachinePanel.FileExists", chooser.getSelectedFile () //$NON-NLS-1$
                .getName () ), Messages.getString ( "MachinePanel.Save" ), //$NON-NLS-1$
            true, true, false );
        confirmDialog.show ();
        if ( confirmDialog.isNotConfirmed () )
        {
          return null;
        }
      }

      String filename = chooser.getSelectedFile ().toString ().matches (
          ".+\\." + this.grammar.getGrammarType ().toLowerCase () ) ? chooser //$NON-NLS-1$
          .getSelectedFile ().toString () : chooser.getSelectedFile ()
          .toString ()
          + "." + this.grammar.getGrammarType ().toLowerCase (); //$NON-NLS-1$

      Storage.getInstance ().store ( this.model, new File ( filename ) );

      prefmanager.setWorkingPath ( chooser.getCurrentDirectory ()
          .getAbsolutePath () );
      this.file = new File ( filename );

    }
    catch ( StoreException e )
    {
      InfoDialog infoDialog = new InfoDialog ( this.mainWindowForm, e.getMessage (),
          Messages.getString ( "MachinePanel.Save" ) ); //$NON-NLS-1$
      infoDialog.show ();
    }
    resetModify ();
    fireModifyStatusChanged ( false );
    return this.file;
  }


  /**
   * Handle Toolbar Alphabet button action event
   */
  public void handleToolbarEditDocument ()
  {
    TerminalDialog alphabetDialog = new TerminalDialog ( this.mainWindowForm,
        this.grammar );
    alphabetDialog.show ();
  }


  /**
   * Handle undo button pressed
   */
  public void handleUndo ()
  {
    // TODO implement me
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public final boolean isModified ()
  {
    return ( this.model.isModified () ) || ( this.file == null );
  }


  /**
   * Signals if this panel is redo able
   * 
   * @return true, if is redo able, false else
   */
  public boolean isRedoAble ()
  {
    // TODO implement me
    return false;
  }


  /**
   * Signals if this panel is undo able
   * 
   * @return true, if is undo able, false else
   */
  public boolean isUndoAble ()
  {
    // TODO implement me
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see LanguageChangedListener#languageChanged()
   */
  public void languageChanged ()
  {
    // Nothing to do so far
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#removeModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final synchronized void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.remove ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#resetModify()
   */
  public void resetModify ()
  {
    this.model.resetModify ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#setName(java.lang.String)
   */
  public void setName ( String name )
  {
    this.name = name;
  }


  /**
   * Returns the {@link DefaultModel}.
   * 
   * @return The {@link DefaultModel}.
   * @see #model
   */
  public DefaultModel getModel ()
  {
    return this.model;
  }


  /**
   * Handle mouse button event for the JTable.
   * 
   * @param event The {@link MouseEvent}.
   */
  public void handleTableMouseClickedEvent ( MouseEvent event )
  {
    if ( event.getButton () != MouseEvent.BUTTON3 )
      return;

    Production selectedProduction = null;

    int index = this.gui.jGTITable.rowAtPoint ( event.getPoint () );

    // set the production under the mouse curser selected
    this.gui.jGTITable.getSelectionModel ()
        .setSelectionInterval ( index, index );

    if ( this.grammar.getRowCount () > 0 && index != -1 )
    {
      selectedProduction = this.grammar.getProductionAt ( index );
    }

    ProductionPopupMenu popupmenu = new ProductionPopupMenu ( this.gui,
        this.model, selectedProduction );

    popupmenu.show ( ( Component ) event.getSource (), event.getX (), event
        .getY () );
  }


  /**
   * Add a new {@link Production}.
   * 
   * @param production the new Production to add.
   */
  public void addProduction ( Production production )
  {
    this.model.addProduction ( production );
  }


  /**
   * Returns the parent frame.
   * 
   * @return the parent frame.
   */
  public JFrame getParent ()
  {
    return this.mainWindowForm;
  }


  /**
   * Let the listeners know that the modify status has changed.
   * 
   * @param forceModify True if the modify is forced, otherwise false.
   */
  public final void fireModifyStatusChanged ( boolean forceModify )
  {

    ModifyStatusChangedListener [] listeners = this.listenerList
        .getListeners ( ModifyStatusChangedListener.class );
    if ( forceModify )
    {
      for ( ModifyStatusChangedListener current : listeners )
      {
        current.modifyStatusChanged ( true );
      }
    }
    else
    {
      boolean newModifyStatus = isModified ();
      for ( ModifyStatusChangedListener current : listeners )
      {
        current.modifyStatusChanged ( newModifyStatus );
      }
    }
  }


  /**
   * Handle add production button pressed.
   */
  public void handleAddProduction ()
  {
    ProductionDialog dialog = new ProductionDialog ( getParent (), this.grammar
        .getNonterminalSymbolSet (), this.grammar.getTerminalSymbolSet (),
        this.model, null );
    dialog.show ();
  }


  /**
   * Handle edit production button pressed.
   */
  public void handleEditProduction ()
  {

    if ( this.gui.jGTITable.getSelectedRow () >= 0 )
    {
      Production production = this.grammar.getProductionAt ( this.gui.jGTITable
          .getSelectedRow () );
      JFrame window = ( JFrame ) SwingUtilities.getWindowAncestor ( this.gui );
      ProductionDialog productionDialog = new ProductionDialog ( window,
          this.grammar.getNonterminalSymbolSet (), this.grammar
              .getTerminalSymbolSet (), this.model, production );
      productionDialog.show ();
    }
  }


  /**
   * Handle delete production button pressed.
   */
  public void handleDeleteProduction ()
  {
    if ( this.gui.jGTITable.getSelectedRow () >= 0 )
    {
      Production production = this.grammar.getProductionAt ( this.gui.jGTITable
          .getSelectedRow () );
      ConfirmDialog confirmedDialog = new ConfirmDialog ( getParent (),
          Messages.getString ( "ProductionPopupMenu.DeleteProductionQuestion", //$NON-NLS-1$
              production ), Messages
              .getString ( "ProductionPopupMenu.DeleteProductionTitle" ), true, //$NON-NLS-1$
          true, false );
      confirmedDialog.show ();
      if ( confirmedDialog.isConfirmed () )
      {
        this.model.removeProduction ( production );
        this.gui.repaint ();
      }
    }
  }
}
