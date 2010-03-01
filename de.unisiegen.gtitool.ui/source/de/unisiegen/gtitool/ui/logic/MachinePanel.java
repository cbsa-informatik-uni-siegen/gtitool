package de.unisiegen.gtitool.ui.logic;


import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Timer;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableColumnModel;

import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.StateMachine;
import de.unisiegen.gtitool.core.machines.Machine.MachineType;
import de.unisiegen.gtitool.core.machines.pda.PDA;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.MainWindow.ButtonState;
import de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.ConsoleColumnModel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.DefaultModel;
import de.unisiegen.gtitool.ui.model.MachineConsoleTableModel;
import de.unisiegen.gtitool.ui.netbeans.MachinePanelForm;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.item.PDAModeItem;
import de.unisiegen.gtitool.ui.preferences.item.WordModeItem;
import de.unisiegen.gtitool.ui.preferences.listener.WordModeChangedListener;
import de.unisiegen.gtitool.ui.redoundo.RedoUndoHandler;
import de.unisiegen.gtitool.ui.storage.Storage;


/**
 * base class for all {@link MachinePanel}s
 */
public abstract class MachinePanel implements LogicClass < MachinePanelForm >,
    EditorPanel
{

  /**
   * Signals the active mouse adapter.
   */
  public enum ActiveMouseAdapter
  {
    /**
     * Add Final State is choosen.
     */
    ADD_FINAL_STATE,

    /**
     * Add Start State is choosen.
     */
    ADD_START_STATE,

    /**
     * Add State is choosen.
     */
    ADD_STATE,

    /**
     * Add Transition is choosen.
     */
    ADD_TRANSITION,

    /**
     * Mouse is choosen.
     */
    MOUSE;
  }


  /**
   * The {@link StateMachine} mode.
   * 
   * @author Christian Fehler
   */
  public enum MachineMode
  {
    /**
     * The edit machine mode.
     */
    EDIT_MACHINE,

    /**
     * The enter word mode.
     */
    ENTER_WORD,

    /**
     * The word navigation mode.
     */
    WORD_NAVIGATION;
  }


  /**
   * The actual active MouseAdapter
   */
  protected static ActiveMouseAdapter activeMouseAdapter;


  /**
   * The {@link MachineMode}.
   */
  protected MachineMode machineMode = MachineMode.EDIT_MACHINE;


  /**
   * The {@link MachinePanelForm}.
   */
  protected MachinePanelForm gui;


  /**
   * The {@link MainWindowForm}.
   */
  protected MainWindowForm mainWindowForm;


  /**
   * The {@link RedoUndoHandler}
   */
  protected RedoUndoHandler redoUndoHandler;


  /**
   * Flag that indicates if a cell is edited.
   */
  protected boolean cellEditingMode = false;


  /**
   * Signals if drag in progress.
   */
  protected boolean dragged;


  /**
   * The {@link File} for this {@link StateMachinePanel}.
   */
  private File file;


  /**
   * The name of this {@link StateMachinePanel}.
   */
  private String name = null;


  /**
   * The {@link EventListenerList}.
   */
  protected EventListenerList listenerList = new EventListenerList ();


  /**
   * Getter for the {@link StateMachine}
   * 
   * @return the {@link StateMachine} of this panel
   */
  protected abstract Machine getMachine ();


  /**
   * The {@link MachineConsoleTableModel} for the warning table.
   */
  private MachineConsoleTableModel warningTableModel;


  /**
   * The {@link MachineConsoleTableModel} for the error table.
   */
  private MachineConsoleTableModel errorTableModel;


  /**
   * The {@link Timer} of the auto step mode.
   */
  protected Timer autoStepTimer = null;
  
  
  /**
   * Flag that indicates if the user input was needed during the navigation so
   * far.
   */
  protected boolean userInputNeeded = false;


  /**
   * Allocates a new {@link MachinePanel}
   * 
   * @param mainWindowForm the {@link MainWindowForm}
   * @param file the {@link File}
   */
  public MachinePanel ( final MainWindowForm mainWindowForm, final File file,
      final DefaultMachineModel model )
  {
    this.mainWindowForm = mainWindowForm;
    this.file = file;
    setupModelMachine ( model );
    this.gui = new MachinePanelForm ( this );

    this.redoUndoHandler = new RedoUndoHandler ( this.mainWindowForm );
    setVisibleConsole ( this.mainWindowForm.getJCheckBoxMenuItemConsole ()
        .getState () );
    setVisibleTable ( this.mainWindowForm.getJCheckBoxMenuItemTable ()
        .getState () );

    initialize ();

    if ( PreferenceManager.getInstance ().getWordModeItem ().equals (
        WordModeItem.LEFT ) )
      this.gui.wordPanelForm.styledWordParserPanel.setRightAlignment ( false );
    else if ( PreferenceManager.getInstance ().getWordModeItem ().equals (
        WordModeItem.RIGHT ) )
      this.gui.wordPanelForm.styledWordParserPanel.setRightAlignment ( true );
    else
      throw new RuntimeException ( "unsupported word mode" ); //$NON-NLS-1$

    PreferenceManager.getInstance ().addWordModeChangedListener (
        new WordModeChangedListener ()
        {

          public void wordModeChanged ( WordModeItem newValue )
          {
            if ( newValue.equals ( WordModeItem.LEFT ) )
              MachinePanel.this.gui.wordPanelForm.styledWordParserPanel
                  .setRightAlignment ( false );
            else if ( newValue.equals ( WordModeItem.RIGHT ) )
              MachinePanel.this.gui.wordPanelForm.styledWordParserPanel
                  .setRightAlignment ( true );
            else
              throw new RuntimeException ( "unsupported word mode" ); //$NON-NLS-1$

            MachinePanel.this.gui.wordPanelForm.styledWordParserPanel.parse ();
          }
        } );

    this.gui.wordPanelForm.styledWordParserPanel.parse ();

    this.gui.jGTISplitPaneConsole.setDividerLocation ( 1.0 );
    this.gui.jGTISplitPaneTable.setDividerLocation ( this.mainWindowForm
        .getWidth () - 220 );
    this.gui.jGTISplitPanePDATable.setDividerLocation ( 0.5 );
  }


  /**
   * Cancels the auto step {@link Timer}.
   * 
   * @return Returns true if the timer was canceled, otherwise false.
   */
  public final boolean cancelAutoStepTimer ()
  {
    if ( this.autoStepTimer != null )
    {
      this.autoStepTimer.cancel ();
      this.autoStepTimer = null;
      return true;
    }
    return false;
  }


  /**
   * Handles the enter {@link Word} event.
   */
  public void handleEnterWord ()
  {
    this.machineMode = MachineMode.ENTER_WORD;

    if ( ! ( getMachine ().getMachineType ().equals ( MachineType.PDA ) )
        || getMachine ().getMachineType ().equals ( MachineType.TDP ) )
      if ( PreferenceManager.getInstance ().getPDAModeItem ().equals (
          PDAModeItem.SHOW ) )
      {
        // do nothing
      }
      else if ( PreferenceManager.getInstance ().getPDAModeItem ().equals (
          PDAModeItem.HIDE ) )
      {
        this.gui.wordPanelForm.jGTILabelStack.setEnabled ( false );
        this.gui.wordPanelForm.styledStackParserPanel.setEnabled ( false );
        this.gui.wordPanelForm.jGTILabelPushDownAlphabet.setEnabled ( false );
        this.gui.wordPanelForm.styledAlphabetParserPanelPushDown
            .setEnabled ( false );
      }
      else
        throw new RuntimeException ( "unsupported pda mode" ); //$NON-NLS-1$

    setVisibleConsole ( false );
    setWordConsole ( true );
    this.gui.wordPanelForm.requestFocus ();
  }


  /**
   * Handle Stop Action in the Word Enter Mode
   */
  public void handleWordStop ()
  {
    cancelAutoStepTimer ();

    this.machineMode = MachineMode.ENTER_WORD;
    this.userInputNeeded = false;

    getMachine ().stop ();

    this.gui.wordPanelForm.styledStackParserPanel.setText ( this.getMachine ()
        .getStack () );

    this.mainWindowForm.getLogic ().removeButtonState (
        ButtonState.SELECTED_AUTO_STEP );
    this.mainWindowForm.getLogic ().addButtonState (
        ButtonState.ENABLED_NAVIGATION_START );

    this.gui.wordPanelForm.styledWordParserPanel
        .setHighlightedParseableEntity ();
    this.gui.wordPanelForm.styledWordParserPanel.setEditable ( true );
    this.gui.wordPanelForm.styledAlphabetParserPanelInput.setCopyable ( true );
    this.gui.wordPanelForm.styledAlphabetParserPanelPushDown
        .setCopyable ( true );
  }


  /**
   * setup the {@link Machine}
   * 
   * @param model the {@link DefaultMachineModel}
   */
  abstract protected void setupModelMachine ( final DefaultMachineModel model );


  abstract protected void onHandleMachinePDATableMouseExited ();


  abstract protected void onHandleMachinePDATableFocusLost ();


  /**
   * Handles the focus lost event on the machine pda table.
   * 
   * @param event The {@link FocusEvent}.
   */
  public void handleMachinePDATableFocusLost (
      @SuppressWarnings ( "unused" ) FocusEvent event )
  {
    if ( this.machineMode.equals ( MachineMode.EDIT_MACHINE )
        && !this.cellEditingMode )
    {
      this.gui.jGTITableMachinePDA.clearSelection ();
      onHandleMachinePDATableFocusLost ();
    }
  }


  /**
   * Handles the mouse exited event on the machine pda table.
   * 
   * @param event The {@link MouseEvent}.
   */
  public void handleMachinePDATableMouseExited (
      @SuppressWarnings ( "unused" ) MouseEvent event )
  {
    if ( this.machineMode.equals ( MachineMode.EDIT_MACHINE )
        && !this.cellEditingMode )
    {
      this.gui.jGTITableMachinePDA.clearSelection ();
      onHandleMachinePDATableMouseExited ();
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#clearValidationMessages()
   */
  public final void clearValidationMessages ()
  {
    this.gui.jGTITabbedPaneConsole.setTitleAt ( 0, Messages
        .getString ( "MachinePanel.Error" ) ); //$NON-NLS-1$
    this.gui.jGTITabbedPaneConsole.setTitleAt ( 1, Messages
        .getString ( "MachinePanel.Warning" ) ); //$NON-NLS-1$

    this.errorTableModel.clearData ();
    this.warningTableModel.clearData ();
  }


  /**
   * Add a new Warning
   * 
   * @param machineException The {@link MachineException} containing the data
   */
  public final void addWarning ( MachineException machineException )
  {
    this.warningTableModel.addRow ( machineException );
  }


  /**
   * Add a new error.
   * 
   * @param machineException The {@link MachineException} containing the error.
   */
  public final void addError ( MachineException machineException )
  {
    this.errorTableModel.addRow ( machineException );
  }


  /**
   * handle the toolbar button 'start'
   * 
   * @param state state of the button
   */
  protected abstract void handleToolbarStart ( final boolean state );


  /**
   * handle the toolbar button 'end'
   * 
   * @param state state of the button
   */
  protected abstract void handleToolbarEnd ( final boolean state );


  protected abstract void handleMouseAdapter ();


  /**
   * handle the console table
   */
  protected abstract void onHandleConsoleTableValueChanged ();


  /**
   * handle highlight of the console table
   * 
   * @param table the {@link JTable}
   */
  protected abstract void onHandleConsoleTableValueChangedHighlight (
      final JTable table );


  /**
   * handles additional actions that are taken when the mouse exists the machine
   * table
   */
  protected abstract void onHandleMachineTableMouseExited ();


  /**
   * handles additional actions that are taken when the machine table looses the
   * focus
   */
  protected abstract void onHandleMachineTableFocusLost ();


  /**
   * handles the mouse exited event of the machine table
   * 
   * @param evt the {@link FocusEvent}
   */
  public void handleMachineTableMouseExited (
      @SuppressWarnings ( "unused" ) final MouseEvent evt )
  {
    if ( this.machineMode.equals ( MachineMode.EDIT_MACHINE )
        && !this.cellEditingMode )
    {
      this.gui.jGTITableMachine.clearSelection ();
      onHandleMachineTableMouseExited ();
    }
  }


  /**
   * handles the machine table focus lost event
   * 
   * @param evt the {@link FocusEvent}
   */
  public void handleMachineTableFocusLost (
      @SuppressWarnings ( "unused" ) final FocusEvent evt )
  {
    if ( this.machineMode.equals ( MachineMode.EDIT_MACHINE )
        && !this.cellEditingMode )
    {
      this.gui.jGTITableMachine.clearSelection ();
      onHandleMachineTableFocusLost ();
    }
  }


  /**
   * handles the focus lost event of the console
   * 
   * @param evt the {@link FocusEvent}
   */
  public void handleConsoleTableFocusLost (
      @SuppressWarnings ( "unused" ) final FocusEvent evt )
  {
    this.gui.jGTITableErrors.clearSelection ();
    this.gui.jGTITableWarnings.clearSelection ();
  }


  /**
   * handles the mouse exited event of the console
   * 
   * @param evt the {@link FocusEvent}
   */
  public void handleConsoleTableMouseExited (
      @SuppressWarnings ( "unused" ) final MouseEvent evt )
  {
    this.gui.jGTITableErrors.clearSelection ();
    this.gui.jGTITableWarnings.clearSelection ();
  }


  /**
   * Handles {@link ListSelectionEvent}s on the console table.
   * 
   * @param event The {@link ListSelectionEvent}.
   */
  public final void handleConsoleTableValueChanged ( ListSelectionEvent event )
  {
    JTable table;
    if ( event.getSource () == this.gui.jGTITableErrors.getSelectionModel () )
      table = this.gui.jGTITableErrors;
    else if ( event.getSource () == this.gui.jGTITableWarnings
        .getSelectionModel () )
      table = this.gui.jGTITableWarnings;
    else
      throw new IllegalArgumentException ( "wrong event source" ); //$NON-NLS-1$

    onHandleConsoleTableValueChanged ();

    onHandleConsoleTableValueChangedHighlight ( table );
  }


  /**
   * Initializes the {@link StateMachinePanel}.
   */
  private final void initialize ()
  {
    handleMouseAdapter ();

    this.errorTableModel = new MachineConsoleTableModel ();
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
    this.warningTableModel = new MachineConsoleTableModel ();
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

    setWordConsole ( false );
    this.gui.wordPanelForm.setAlphabet ( getMachine ().getAlphabet () );
  }


  /**
   * Returns the {@link MachineMode}.
   * 
   * @return The {@link MachineMode}.
   */
  public final MachineMode getMachineMode ()
  {
    return this.machineMode;
  }


  /**
   * Returns the {@link TableColumnModel} of the machine.
   * 
   * @return The {@link TableColumnModel} of the machine.
   */
  public final TableColumnModel getMachineTableColumnModel ()
  {
    return getMachine ().getTableColumnModel ();
  }


  /**
   * Returns the {@link MainWindow}.
   * 
   * @return The {@link MainWindow}.
   * @see #mainWindowForm
   */
  public final MainWindow getMainWindow ()
  {
    return this.mainWindowForm.getLogic ();
  }


  /**
   * Returns the {@link MainWindowForm}.
   * 
   * @return The {@link MainWindowForm}.
   * @see #mainWindowForm
   */
  public final MainWindowForm getMainWindowForm ()
  {
    return this.mainWindowForm;
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#getName()
   */
  public final String getName ()
  {
    return this.file == null ? this.name : this.file.getName ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#getPanel()
   */
  public final JPanel getPanel ()
  {
    return this.gui;
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#getFile()
   */
  public final File getFile ()
  {
    return this.file;
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#getFileEnding()
   */
  public final String getFileEnding ()
  {
    return "." + getMachine ().getMachineType ().getFileEnding (); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final MachinePanelForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Returns the redoUndoHandler.
   * 
   * @return The redoUndoHandler.
   * @see #redoUndoHandler
   */
  public final RedoUndoHandler getRedoUndoHandler ()
  {
    return this.redoUndoHandler;
  }


  /**
   * Returns the {@link DefaultModel}.
   * 
   * @return The {@link DefaultModel}.
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#getModel()
   */
  public abstract DefaultModel getModel ();


  /**
   * Set the file for this {@link StateMachine Panel}.
   * 
   * @param file The file for this {@link StateMachine Panel}.
   */
  public final void setFileName ( File file )
  {
    this.file = file;
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#setName(java.lang.String)
   */
  public final void setName ( String name )
  {
    this.name = name;
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
      this.gui.jGTISplitPaneConsole
          .setRightComponent ( this.gui.jGTITabbedPaneConsole );
      this.gui.jGTISplitPaneConsole.setDividerSize ( 3 );
      this.gui.jGTISplitPaneConsole.setDividerLocation ( this.mainWindowForm
          .getHeight () - 322 );
    }
    else
    {
      this.gui.jGTISplitPaneConsole.setRightComponent ( null );
      this.gui.jGTISplitPaneConsole.setDividerSize ( 0 );
    }
  }


  /**
   * Sets the visibility of the {@link PDA} table.
   * 
   * @param visible Visible or not visible.
   */
  public final void setVisiblePDATable ( boolean visible )
  {
    if ( visible )
    {
      if ( this.gui.jGTISplitPanePDATable.getRightComponent () == null )
      {
        this.gui.jGTISplitPanePDATable
            .setRightComponent ( this.gui.jGTIScrollPaneMachinePDA );
        this.gui.jGTISplitPanePDATable.setDividerSize ( 3 );
      }
    }
    else if ( this.gui.jGTISplitPanePDATable.getRightComponent () != null )
    {
      this.gui.jGTISplitPanePDATable.setRightComponent ( null );
      this.gui.jGTISplitPanePDATable.setDividerSize ( 0 );
    }
  }


  /**
   * Sets the visibility of the table.
   * 
   * @param visible Visible or not visible.
   */
  public final void setVisibleTable ( boolean visible )
  {
    if ( visible )
    {
      if ( this.gui.jGTISplitPaneTable.getRightComponent () == null )
      {
        this.gui.jGTISplitPaneTable
            .setRightComponent ( this.gui.jGTISplitPanePDATable );
        this.gui.jGTISplitPaneTable.setDividerSize ( 3 );
        this.gui.jGTISplitPaneTable.setDividerLocation ( this.mainWindowForm
            .getWidth () - 220 );
      }
    }
    else if ( this.gui.jGTISplitPaneTable.getRightComponent () != null )
    {
      this.gui.jGTISplitPaneTable.setRightComponent ( null );
      this.gui.jGTISplitPaneTable.setDividerSize ( 0 );
    }
  }


  /**
   * Sets the visibility of the {@link Word} console.
   * 
   * @param visible Visible or not visible.
   */
  protected final void setWordConsole ( boolean visible )
  {
    if ( visible )
    {
      if ( this.gui.jGTISplitPaneWord.getRightComponent () == null )
      {
        this.gui.jGTISplitPaneWord.setRightComponent ( this.gui.wordPanelForm );
        this.gui.jGTISplitPaneWord.setDividerSize ( 3 );
      }
    }
    else if ( this.gui.jGTISplitPaneWord.getRightComponent () != null )
    {
      this.gui.jGTISplitPaneWord.setRightComponent ( null );
      this.gui.jGTISplitPaneWord.setDividerSize ( 0 );
    }
  }


  /**
   * Handle save as operation
   * 
   * @return filename
   */
  public final File handleSave ()
  {
    if ( this.file == null )
      return handleSaveAs ();
    try
    {
      Storage.getInstance ().store ( getModel (), this.file );
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
      FileFilter fileFilter = new FileFilter ()
      {

        @Override
        public boolean accept ( File acceptedFile )
        {
          if ( acceptedFile.isDirectory () )
            return true;
          if ( acceptedFile.getName ().toLowerCase ().matches ( ".+\\." //$NON-NLS-1$
              + getMachine ().getMachineType ().getFileEnding () ) )
            return true;
          return false;
        }


        @Override
        public String getDescription ()
        {
          return Messages.getString ( "NewDialog." //$NON-NLS-1$
              + getMachine ().getMachineType ().toString () ) + " (*." //$NON-NLS-1$
              + getMachine ().getMachineType ().getFileEnding () + ")"; //$NON-NLS-1$
        }
      };

      SaveDialog saveDialog = new SaveDialog ( this.mainWindowForm,
          PreferenceManager.getInstance ().getWorkingPath (), fileFilter,
          fileFilter );
      saveDialog.show ();

      if ( ( !saveDialog.isConfirmed () )
          || ( saveDialog.getSelectedFile () == null ) )
        return null;

      if ( saveDialog.getSelectedFile ().exists () )
      {
        ConfirmDialog confirmDialog = new ConfirmDialog ( this.mainWindowForm,
            Messages.getString ( "MachinePanel.FileExists", saveDialog //$NON-NLS-1$
                .getSelectedFile ().getName () ), Messages
                .getString ( "MachinePanel.Save" ), true, false, true, false, //$NON-NLS-1$
            false );
        confirmDialog.show ();
        if ( confirmDialog.isNotConfirmed () )
          return null;
      }

      String filename = saveDialog.getSelectedFile ().toString ().matches (
          ".+\\." + getMachine ().getMachineType ().getFileEnding () ) ? saveDialog //$NON-NLS-1$
          .getSelectedFile ().toString ()
          : saveDialog.getSelectedFile ().toString ()
              + "." + getMachine ().getMachineType ().getFileEnding (); //$NON-NLS-1$

      Storage.getInstance ().store ( getModel (), new File ( filename ) );

      PreferenceManager.getInstance ().setWorkingPath (
          saveDialog.getCurrentDirectory ().getAbsolutePath () );
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
   * Let the listeners know that the modify status has changed.
   * 
   * @param forceModify True if the modify is forced, otherwise false.
   */
  protected void fireModifyStatusChanged ( boolean forceModify )
  {
    ModifyStatusChangedListener [] listeners = this.listenerList
        .getListeners ( ModifyStatusChangedListener.class );
    if ( forceModify )
      for ( ModifyStatusChangedListener current : listeners )
        current.modifyStatusChanged ( true );
    else
    {
      boolean newModifyStatus = isModified ();
      for ( ModifyStatusChangedListener current : listeners )
        current.modifyStatusChanged ( newModifyStatus );
    }
  }
}
