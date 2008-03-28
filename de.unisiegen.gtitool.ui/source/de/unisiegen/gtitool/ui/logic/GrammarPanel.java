package de.unisiegen.gtitool.ui.logic;


import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.exchange.Exchange;
import de.unisiegen.gtitool.ui.model.ConsoleColumnModel;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;
import de.unisiegen.gtitool.ui.model.DefaultModel;
import de.unisiegen.gtitool.ui.model.GrammarColumnModel;
import de.unisiegen.gtitool.ui.model.GrammarConsoleTableModel;
import de.unisiegen.gtitool.ui.model.MachineConsoleTableModel;
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
   * Flag that indicates if the console divider location should be stored.
   */
  private boolean setDividerLocationConsole = true;


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
   * The {@link MachineConsoleTableModel} for the warning table.
   */
  private GrammarConsoleTableModel warningTableModel;


  /**
   * The {@link GrammarConsoleTableModel} for the error table.
   */
  private GrammarConsoleTableModel errorTableModel;


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
    initialize ();

    this.gui.jSplitPaneConsole.setDividerLocation ( PreferenceManager
        .getInstance ().getDividerLocationConsole () );
    setVisibleConsole ( this.mainWindowForm.jCheckBoxMenuItemConsole
        .getState () );
    this.gui.jSplitPaneConsole.addPropertyChangeListener (
        JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void propertyChange ( PropertyChangeEvent event )
          {
            if ( GrammarPanel.this.setDividerLocationConsole )
            {
              PreferenceManager.getInstance ().setDividerLocationConsole (
                  ( ( Integer ) event.getNewValue () ).intValue () );
            }
            GrammarPanel.this.setDividerLocationConsole = true;
          }
        } );

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
      InfoDialog infoDialog = new InfoDialog ( this.mainWindowForm, e
          .getMessage (), Messages.getString ( "MachinePanel.Save" ) ); //$NON-NLS-1$
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
        ConfirmDialog confirmDialog = new ConfirmDialog ( this.mainWindowForm,
            Messages.getString (
                "MachinePanel.FileExists", chooser.getSelectedFile () //$NON-NLS-1$
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
      InfoDialog infoDialog = new InfoDialog ( this.mainWindowForm, e
          .getMessage (), Messages.getString ( "MachinePanel.Save" ) ); //$NON-NLS-1$
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
    // Nothing to do
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

    ArrayList < Production > productions = new ArrayList < Production > ();
    boolean multiRowChoosen = false;

    int [] rows = this.gui.jGTITable.getSelectedRows ();
    int rowIndex = this.gui.jGTITable.rowAtPoint ( event.getPoint () );

    if ( rows.length > 1 )
      for ( int row : rows )
      {
        if ( row == rowIndex )
          multiRowChoosen = true;
      }
    if ( !multiRowChoosen )
    {
      if ( rowIndex == -1 )
      {
        // Do nothing
      }
      else
      {

        // Give the user a visual clue which rowIndex he has clicked on
        this.gui.jGTITable.changeSelection ( rowIndex, 0, false, false );

        productions.add ( this.grammar.getProductionAt ( rowIndex ) );
      }
    }
    else
    {
      int [] rowindeces = new int [ rows.length ];
      for ( int i = 0 ; i < rows.length ; i++ )
      {
        productions.add ( this.grammar.getProductionAt ( rowindeces [ i ] ) );
      }
    }

    ProductionPopupMenu popupmenu = new ProductionPopupMenu ( this.gui,
        this.model, productions );

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


  /**
   * Initialize the used model
   */
  private void initialize ()
  {
    this.gui.jGTITable.setModel ( this.grammar );
    this.gui.jGTITable.setColumnModel ( new GrammarColumnModel () );
    this.gui.jGTITable.getTableHeader ().setReorderingAllowed ( false );
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

    this.errorTableModel = new GrammarConsoleTableModel ();
    this.gui.jGTITableErrors.setModel ( this.errorTableModel );
    this.gui.jGTITableErrors.setColumnModel ( new ConsoleColumnModel () );
    this.gui.jGTITableErrors.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableErrors
        .setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
    this.gui.jGTITableErrors.getSelectionModel ().addListSelectionListener (
        new ListSelectionListener ()
        {

          public void valueChanged ( ListSelectionEvent event )
          {
            handleConsoleTableValueChanged ( event );
          }

        } );
    this.warningTableModel = new GrammarConsoleTableModel ();
    this.gui.jGTITableWarnings.setModel ( this.warningTableModel );
    this.gui.jGTITableWarnings.setColumnModel ( new ConsoleColumnModel () );
    this.gui.jGTITableWarnings.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableWarnings
        .setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
    this.gui.jGTITableWarnings.getSelectionModel ().addListSelectionListener (
        new ListSelectionListener ()
        {

          public void valueChanged ( ListSelectionEvent event )
          {
            handleConsoleTableValueChanged ( event );
          }

        } );
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.EditorPanel#clearValidationMessages()
   */
  public void clearValidationMessages ()
  {
    this.gui.jTabbedPaneConsole.setTitleAt ( 0, Messages
        .getString ( "MachinePanel.Error" ) ); //$NON-NLS-1$
    this.gui.jTabbedPaneConsole.setTitleAt ( 1, Messages
        .getString ( "MachinePanel.Warning" ) ); //$NON-NLS-1$

    this.errorTableModel.clearData ();
    this.warningTableModel.clearData ();
  }


  /**
   * Handles focus lost event on the console table.
   * 
   * @param event The {@link FocusEvent}.
   */
  public final void handleConsoleTableFocusLost ( @SuppressWarnings ( "unused" )
  FocusEvent event )
  {
    this.gui.jGTITableErrors.clearSelection ();
    this.gui.jGTITableWarnings.clearSelection ();
    // clearHighlight ();
  }


  /**
   * Handles the mouse exited event on the console table.
   * 
   * @param event The {@link MouseEvent}.
   */
  public final void handleConsoleTableMouseExited (
      @SuppressWarnings ( "unused" )
      MouseEvent event )
  {
    this.gui.jGTITableErrors.clearSelection ();
    this.gui.jGTITableWarnings.clearSelection ();
  }


  /**
   * Handles {@link ListSelectionEvent}s on the console table.
   * 
   * @param event The {@link ListSelectionEvent}.
   */
  public final void handleConsoleTableValueChanged (
      @SuppressWarnings ( "unused" )
      ListSelectionEvent event )
  {
    // TODO implement me

  }


  /**
   * Sets the visibility of the console.
   * 
   * @param visible Visible or not visible.
   */
  public final void setVisibleConsole ( boolean visible )
  {
    if ( visible )
    {
      this.setDividerLocationConsole = false;
      this.gui.jSplitPaneConsole
          .setRightComponent ( this.gui.jTabbedPaneConsole );
      this.gui.jSplitPaneConsole.setDividerSize ( 3 );
      this.gui.jSplitPaneConsole.setDividerLocation ( PreferenceManager
          .getInstance ().getDividerLocationConsole () );
    }
    else
    {
      this.setDividerLocationConsole = false;
      this.gui.jSplitPaneConsole.setRightComponent ( null );
      this.gui.jSplitPaneConsole.setDividerSize ( 0 );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.EditorPanel#getJTabbedPaneConsole()
   */
  public JTabbedPane getJTabbedPaneConsole ()
  {
    return this.gui.jTabbedPaneConsole;
  }


  /**
   * Add a new Warning
   * 
   * @param grammarException The {@link MachineException} containing the data
   */
  public final void addWarning ( GrammarException grammarException )
  {
    this.warningTableModel.addRow ( grammarException );
  }


  /**
   * Returns the grammar.
   * 
   * @return The grammar.
   * @see #grammar
   */
  public Grammar getGrammar ()
  {
    return this.grammar;
  }


  /**
   * Add a new Error
   * 
   * @param grammarException The {@link MachineException} containing the data
   */
  public final void addError ( GrammarException grammarException )
  {
    this.errorTableModel.addRow ( grammarException );
  }
}
