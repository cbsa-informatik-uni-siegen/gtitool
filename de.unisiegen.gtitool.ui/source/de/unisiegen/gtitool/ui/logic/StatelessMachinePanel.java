package de.unisiegen.gtitool.ui.logic;


import java.awt.GridBagConstraints;
import java.io.File;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.StatelessMachine;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.DefaultModel;
import de.unisiegen.gtitool.ui.model.DefaultStatelessMachineModel;
import de.unisiegen.gtitool.ui.model.PTTableColumnModel;
import de.unisiegen.gtitool.ui.model.PTTableModel;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.swing.JGTIPanel;
import de.unisiegen.gtitool.ui.swing.JGTIScrollPane;
import de.unisiegen.gtitool.ui.swing.JGTITable;


/**
 * MachinePanel for the {@link StatelessMachine}s
 */
public class StatelessMachinePanel extends MachinePanel
{

  /**
   * the {@link DefaultStatelessMachineModel}
   */
  private DefaultStatelessMachineModel model;


  /**
   * the {@link StatelessMachine}
   */
  private StatelessMachine machine;


  /**
   * allocates a new {@link StatelessMachinePanel}
   * 
   * @param mainWindowForm the {@link MainWindowForm}
   * @param model the {@link DefaultStatelessMachineModel}
   * @param file the {@link File}
   */
  public StatelessMachinePanel ( final MainWindowForm mainWindowForm,
      final DefaultStatelessMachineModel model, final File file )
  {
    super ( mainWindowForm, file, model );
    this.model = model;
    this.machine = this.model.getMachine ();

    initializeParsingTable ();
    initializeStatelessMachineTable ();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void setupModelMachine ( final DefaultMachineModel model )
  {
    this.model = ( DefaultStatelessMachineModel ) model;
    this.machine = this.model.getMachine ();
  }


  /**
   * Initializes the TDP action table
   */
  private final void initializeParsingTable ()
  {
    /*
     * initialize the parsing table
     */
    this.gui.jGTITableMachine.setModel ( new PTTableModel ( ( CFG ) this.model
        .getGrammar () ) );
    this.gui.jGTITableMachine.setColumnModel ( new PTTableColumnModel (
        this.machine.getAlphabet () ) );
    this.gui.jGTITableMachine.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableMachine
        .setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
    this.gui.jGTITableMachine.setCellSelectionEnabled ( true );

    // we don't need the pda stack operation table
    setVisiblePDATable ( false );
  }


  /**
   * Initializes the parsing table
   */
  private final void initializeStatelessMachineTable ()
  {
    JGTITable jGTIStatelessMachineTable = new JGTITable ();
    JGTIPanel jGTIStatelessMachineTablePanel = new JGTIPanel ();
    JGTIScrollPane jGTIStatelessMachineTablePanelScrollPane = new JGTIScrollPane ();
    GridBagConstraints gridBagConstraints = new GridBagConstraints ();

    jGTIStatelessMachineTablePanelScrollPane.setBorder ( null );

    gridBagConstraints.fill = GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    jGTIStatelessMachineTablePanel.add (
        jGTIStatelessMachineTablePanelScrollPane, gridBagConstraints );
    jGTIStatelessMachineTablePanelScrollPane
        .setViewportView ( jGTIStatelessMachineTable );

    this.gui.jGTISplitPaneTable
        .setLeftComponent ( jGTIStatelessMachineTablePanel );

    int loc = this.gui.getWidth () / 2;
    this.gui.jGTISplitPaneTable.setDividerLocation ( loc );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#getMachine()
   */
  @Override
  protected Machine getMachine ()
  {
    return this.machine;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#getModel()
   */
  @Override
  public DefaultModel getModel ()
  {
    return this.model;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#handleMouseAdapter()
   */
  @Override
  protected void handleMouseAdapter ()
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#handleToolbarEnd(boolean)
   */
  @Override
  protected void handleToolbarEnd ( boolean state )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#handleToolbarStart(boolean)
   */
  @Override
  protected void handleToolbarStart ( boolean state )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#onHandleConsoleTableValueChanged()
   */
  @Override
  protected void onHandleConsoleTableValueChanged ()
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#onHandleConsoleTableValueChangedHighlight(javax.swing.JTable)
   */
  @Override
  protected void onHandleConsoleTableValueChangedHighlight ( JTable table )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#getConverter(de.unisiegen.gtitool.core.entities.InputEntity.EntityType)
   */
  public Converter getConverter ( EntityType destination )
  {
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#getJTabbedPaneConsole()
   */
  public JTabbedPane getJTabbedPaneConsole ()
  {
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleExchange()
   */
  public void handleExchange ()
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleRedo()
   */
  public void handleRedo ()
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleToolbarEditDocument()
   */
  public void handleToolbarEditDocument ()
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleUndo()
   */
  public void handleUndo ()
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#isRedoAble()
   */
  public boolean isRedoAble ()
  {
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#isUndoAble()
   */
  public boolean isUndoAble ()
  {
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#addModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#isModified()
   */
  public boolean isModified ()
  {
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#removeModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#resetModify()
   */
  public void resetModify ()
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener#languageChanged()
   */
  public void languageChanged ()
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#onHandleMachinePDATableFocusLost()
   */
  @Override
  protected void onHandleMachinePDATableFocusLost ()
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#onHandleMachinePDATableMouseExited()
   */
  @Override
  protected void onHandleMachinePDATableMouseExited ()
  {
  }


  /**
   * TODO
   *
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#onHandleMachineTableFocusLost()
   */
  @Override
  protected void onHandleMachineTableFocusLost ()
  {
  }


  /**
   * TODO
   *
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#onHandleMachineTableMouseExited()
   */
  @Override
  protected void onHandleMachineTableMouseExited ()
  {
  }

}
