package de.unisiegen.gtitool.ui.logic;


import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import de.unisiegen.gtitool.core.entities.exceptions.RegexException;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.regex.Regex;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarException;
import de.unisiegen.gtitool.core.regex.DefaultRegex;
import de.unisiegen.gtitool.core.regex.DefaultRegex.RegexType;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;
import de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.ConsoleColumnModel;
import de.unisiegen.gtitool.ui.model.DefaultModel;
import de.unisiegen.gtitool.ui.model.DefaultRegexModel;
import de.unisiegen.gtitool.ui.model.RegexConsoleTableModel;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.netbeans.RegexPanelForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.redoundo.RedoUndoHandler;
import de.unisiegen.gtitool.ui.storage.Storage;


/**
 * @author Simon Meurer
 */
public final class RegexPanel implements LogicClass < RegexPanelForm >,
    EditorPanel
{

  /**
   * The {@link JGTIGraph} for this Panel
   */
  private JGTIGraph jGTIGraph;



  /**
   * The {@link ModifyStatusChangedListener}.
   */
  private ModifyStatusChangedListener modifyStatusChangedListener;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The {@link RegexPanelForm}
   */
  private RegexPanelForm gui;


  /**
   * The {@link Regex}
   */
  private DefaultRegex regex;


  /**
   * The {@link File} for this {@link RegexPanel}.
   */
  private File file;


  /**
   * The name of this {@link GrammarPanel}.
   */
  private String name = null;


  /**
   * The {@link MainWindowForm}
   */
  private MainWindowForm mainWindowForm;


  /**
   * The {@link RegexConsoleTableModel} for the warning table.
   */
  private RegexConsoleTableModel warningTableModel;


  /**
   * The {@link RegexConsoleTableModel} for the error table.
   */
  private RegexConsoleTableModel errorTableModel;


  /**
   * The {@link RedoUndoHandler}
   */
  private RedoUndoHandler redoUndoHandler;


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#clearValidationMessages()
   */
  public void clearValidationMessages ()
  {
    this.errorTableModel.clearData ();
    this.warningTableModel.clearData ();
  }


  /**
   * Returns the regex.
   * 
   * @return The regex.
   * @see #regex
   */
  public DefaultRegex getRegex ()
  {
    return this.regex;
  }


  /**
   * Allocates a new {@link RegexPanel}
   * 
   * @param mainWindowForm The {@link MainWindowForm}
   * @param file The {@link File}
   * @param model The {@link DefaultRegexModel}
   */
  public RegexPanel ( MainWindowForm mainWindowForm, DefaultRegexModel model,
      File file )
  {
    this.mainWindowForm = mainWindowForm;
    this.file = file;
    this.model = model;
    this.model.initializeGraph ();
    this.jGTIGraph = this.model.getJGTIGraph ();
    this.jGTIGraph.setEditable ( false );
    this.regex = model.getRegex ();
    this.gui = new RegexPanelForm ( this );

    this.redoUndoHandler = new RedoUndoHandler ( this.mainWindowForm );

    setVisibleConsole ( this.mainWindowForm.getJCheckBoxMenuItemConsole ()
        .getState () );

    this.gui.jGTISplitPaneConsole.setDividerLocation ( 1.0 );

    PreferenceManager.getInstance ().addLanguageChangedListener ( this );

    initialize ();
  }


  /**
   * Initialize
   */
  private void initialize ()
  {
    this.errorTableModel = new RegexConsoleTableModel ();
    this.warningTableModel = new RegexConsoleTableModel ();
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
            // TODO
          }
        } );
    this.gui.jGTIScrollPaneGraph.setViewportView ( this.jGTIGraph );
  }


  /**
   * Add a new Error
   * 
   * @param regexException The {@link GrammarException} containing the data
   */
  public final void addError ( RegexException regexException )
  {
    this.errorTableModel.addRow ( regexException );
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
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#getConverter()
   */
  public Converter getConverter ()
  {
    return null;
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
    return "." + RegexType.REGEX.getFileEnding (); //$NON-NLS-1$
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#getJTabbedPaneConsole()
   */
  public JTabbedPane getJTabbedPaneConsole ()
  {
    return this.gui.jGTITabbedPaneConsole;
  }


  /**
   * The {@link DefaultRegexModel}.
   */
  private DefaultRegexModel model;


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
   * @see EditorPanel#getPanel()
   */
  public JPanel getPanel ()
  {
    return this.gui;
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleExchange()
   */
  public void handleExchange ()
  {
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleRedo()
   */
  public void handleRedo ()
  {
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleSave()
   */
  public File handleSave ()
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
          .getMessage (), Messages.getString ( "RegexPanel.Save" ) ); //$NON-NLS-1$
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
  public final void fireModifyStatusChanged ( boolean forceModify )
  {
    clearValidationMessages ();

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
   * TODO
   * 
   * @param evt
   */
  public void handleRegexChangeButtonClicked ( @SuppressWarnings ( "unused" )
  ActionEvent evt )
  {
    new RegexDialog ( this );
  }


  /**
   * Changes the Regex
   * 
   * @param newRegexNode The new {@link RegexNode}
   */
  public void changeRegex ( RegexNode newRegexNode )
  {
    this.regex.changeRegexNode ( newRegexNode );
    this.gui.jGTITextFieldRegex.setText ( this.regex.getRegexString () );
    
    this.model.getRegex ().changeRegexNode ( newRegexNode );
    this.model.initializeGraph ();
    this.jGTIGraph = this.model.getJGTIGraph ();
    this.jGTIGraph.setEditable ( false );
    this.jGTIGraph.setEnabled ( false );
    this.gui.jGTIScrollPaneGraph.setViewportView ( this.jGTIGraph );
    
    this.model.createTree ();
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleSaveAs()
   */
  public File handleSaveAs ()
  {
    try
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
          if ( acceptedFile.getName ().toLowerCase ().matches ( ".+\\." //$NON-NLS-1$
              + RegexType.REGEX.getFileEnding () ) )
          {
            return true;
          }
          return false;
        }


        @Override
        public String getDescription ()
        {
          return Messages.getString ( "NewDialog." //$NON-NLS-1$
              + RegexType.REGEX.toString () ) + " (*." //$NON-NLS-1$
              + RegexType.REGEX.getFileEnding () + ")"; //$NON-NLS-1$
        }
      };

      SaveDialog saveDialog = new SaveDialog ( this.mainWindowForm,
          PreferenceManager.getInstance ().getWorkingPath (), fileFilter,
          fileFilter );
      saveDialog.show ();

      if ( ( !saveDialog.isConfirmed () )
          || ( saveDialog.getSelectedFile () == null ) )
      {
        return null;
      }

      if ( saveDialog.getSelectedFile ().exists () )
      {
        ConfirmDialog confirmDialog = new ConfirmDialog ( this.mainWindowForm,
            Messages.getString (
                "RegexPanel.FileExists", saveDialog.getSelectedFile () //$NON-NLS-1$
                    .getName () ), Messages.getString ( "RegexPanel.Save" ), //$NON-NLS-1$
            true, false, true, false, false );
        confirmDialog.show ();
        if ( confirmDialog.isNotConfirmed () )
        {
          return null;
        }
      }

      String filename = saveDialog.getSelectedFile ().toString ().matches (
          ".+\\." + RegexType.REGEX.getFileEnding () ) ? saveDialog //$NON-NLS-1$
          .getSelectedFile ().toString () : saveDialog.getSelectedFile ()
          .toString ()
          + "." + RegexType.REGEX.getFileEnding (); //$NON-NLS-1$

      Storage.getInstance ().store ( this.model, new File ( filename ) );

      PreferenceManager.getInstance ().setWorkingPath (
          saveDialog.getCurrentDirectory ().getAbsolutePath () );
      this.file = new File ( filename );
    }
    catch ( StoreException e )
    {
      InfoDialog infoDialog = new InfoDialog ( this.mainWindowForm, e
          .getMessage (), Messages.getString ( "RegexPanel.Save" ) ); //$NON-NLS-1$
      infoDialog.show ();
    }
    resetModify ();
    fireModifyStatusChanged ( false );
    return this.file;
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleToolbarEditDocument()
   */
  public void handleToolbarEditDocument ()
  {
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleUndo()
   */
  public void handleUndo ()
  {
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#isRedoAble()
   */
  public boolean isRedoAble ()
  {
    return false;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#isUndoAble()
   */
  public boolean isUndoAble ()
  {
    return false;
  }


  /**
   * TODO
   * 
   * @param name
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#setName(java.lang.String)
   */
  public void setName ( String name )
  {
    this.name = name;
  }


  /**
   * TODO
   * 
   * @param visible
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#setVisibleConsole(boolean)
   */
  public void setVisibleConsole ( boolean visible )
  {
    this.gui.jGTIPanelConsole.setVisible ( visible );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#addModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public boolean isModified ()
  {
    return this.model.isModified ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#removeModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final void removeModifyStatusChangedListener (
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
   * TODO
   * 
   * @see de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener#languageChanged()
   */
  public void languageChanged ()
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public RegexPanelForm getGUI ()
  {
    return this.gui;
  }

}
