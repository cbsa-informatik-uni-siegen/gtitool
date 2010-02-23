package de.unisiegen.gtitool.ui.logic;


import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JTabbedPane;
import javax.swing.JTable;

import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.StatelessMachine;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.DefaultModel;
import de.unisiegen.gtitool.ui.model.DefaultStatelessMachineModel;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


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
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#fireModifyStatusChanged(boolean)
   */
  @Override
  protected void fireModifyStatusChanged ( boolean forceModify )
  {
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
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#handleConsoleTableFocusLost(java.awt.event.FocusEvent)
   */
  @Override
  public void handleConsoleTableFocusLost ( FocusEvent evt )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#handleConsoleTableMouseExited(java.awt.event.MouseEvent)
   */
  @Override
  public void handleConsoleTableMouseExited ( MouseEvent evt )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#handleMachinePDATableFocusLost(java.awt.event.FocusEvent)
   */
  @Override
  public void handleMachinePDATableFocusLost ( FocusEvent evt )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#handleMachinePDATableMouseExited(java.awt.event.MouseEvent)
   */
  @Override
  public void handleMachinePDATableMouseExited ( MouseEvent evt )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#handleMachineTableFocusLost(java.awt.event.FocusEvent)
   */
  @Override
  public void handleMachineTableFocusLost ( FocusEvent evt )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#handleMachineTableMouseExited(java.awt.event.MouseEvent)
   */
  @Override
  public void handleMachineTableMouseExited ( MouseEvent evt )
  {
  }

}
