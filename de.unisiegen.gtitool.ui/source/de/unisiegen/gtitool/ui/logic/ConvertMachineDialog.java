package de.unisiegen.gtitool.ui.logic;


import javax.swing.JFrame;

import org.jgraph.JGraph;

import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.dfa.DefaultDFA;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel.MachineType;
import de.unisiegen.gtitool.ui.netbeans.ConvertMachineDialogForm;


/**
 * The {@link ConvertMachineDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ConvertMachineDialog implements Converter
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( ConvertMachineDialog.class );


  /**
   * The {@link ConvertMachineDialogForm}.
   */
  private ConvertMachineDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The original {@link JGraph} containing the diagramm.
   */
  private JGraph jGraphOriginal;


  /**
   * The converted {@link JGraph} containing the diagramm.
   */
  private JGraph jGraphConverted;


  /**
   * The {@link MachinePanel}.
   */
  private MachinePanel machinePanel;


  /**
   * The original {@link DefaultMachineModel}.
   */
  private DefaultMachineModel modelOriginal;


  /**
   * The original {@link DefaultMachineModel}.
   */
  private DefaultMachineModel modelConverted;


  /**
   * The original {@link Machine}.
   */
  private Machine machineOriginal;


  /**
   * The original {@link Machine}.
   */
  private Machine machineConverted;


  /**
   * Allocates a new {@link ConvertMachineDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param machinePanel The {@link MachinePanel}.
   */
  public ConvertMachineDialog ( JFrame parent, MachinePanel machinePanel )
  {
    logger.debug ( "ConvertMachineDialog", //$NON-NLS-1$
        "allocate a new convert machine dialog" ); //$NON-NLS-1$

    this.parent = parent;
    this.machinePanel = machinePanel;
    this.gui = new ConvertMachineDialogForm ( this, parent );
    this.gui.jGTISplitPaneGraph.setDividerLocation ( 0.5 );

    try
    {
      this.modelOriginal = new DefaultMachineModel ( this.machinePanel
          .getModel ().getElement (), null );
    }
    catch ( TransitionSymbolOnlyOneTimeException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    catch ( SymbolException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    catch ( TransitionException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    catch ( StoreException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    this.jGraphOriginal = this.modelOriginal.getJGraph ();
    this.jGraphOriginal.setEnabled ( false );
    this.gui.jGTIScrollPaneOriginal.setViewportView ( this.jGraphOriginal );
    this.machineOriginal = this.modelOriginal.getMachine ();

    this.modelConverted = new DefaultMachineModel ( new DefaultDFA (
        this.machineOriginal.getAlphabet (), this.machineOriginal
            .getPushDownAlphabet (), this.machineOriginal
            .isUsePushDownAlphabet () ) );
    this.modelConverted.setUseStateSetView ( true );
    this.jGraphConverted = this.modelConverted.getJGraph ();
    this.jGraphConverted.setEnabled ( false );
    this.gui.jGTIScrollPaneConverted.setViewportView ( this.jGraphConverted );
    this.machineConverted = this.modelConverted.getMachine ();

    addStartState ();
    start ();
  }


  /**
   * Starts the convertion.
   */
  private final void start ()
  {
    for ( State current : this.machineOriginal.getState () )
    {
      if ( current.isStartState () )
      {
        current.setActive ( true );
        break;
      }
    }
    // only one state added
    this.machineConverted.getState ( 0 ).setActive ( true );
  }


  /**
   * Adds the start {@link State}.
   */
  private final void addStartState ()
  {
    State newState;
    try
    {
      newState = new DefaultState ( this.machineConverted.getAlphabet (),
          this.machineConverted.getPushDownAlphabet (), true, false );
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    this.modelConverted.createStateView ( 120, 100, newState, false );

  }


  /**
   * {@inheritDoc}
   * 
   * @see Converter#convert(MachineType)
   */
  public final void convert ( @SuppressWarnings ( "unused" )
  MachineType machineType )
  {
    show ();
  }


  /**
   * Handles the action on the auto step button.
   */
  public final void handleAutoStep ()
  {
    logger.debug ( "handleAutoStep", "handle auto step" ); //$NON-NLS-1$ //$NON-NLS-2$
  }


  /**
   * Handles the action on the cancel button.
   */
  public final void handleCancel ()
  {
    logger.debug ( "handleCancel", "handle cancel" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.gui.dispose ();
  }


  /**
   * Handles the action on the next step button.
   */
  public final void handleNextStep ()
  {
    logger.debug ( "handleNextStep", "handle next step" ); //$NON-NLS-1$ //$NON-NLS-2$
  }


  /**
   * Handles the action on the ok button.
   */
  public final void handleOk ()
  {
    logger.debug ( "handleOk", "handle ok" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.gui.dispose ();
  }


  /**
   * Handles the action on the previous step button.
   */
  public final void handlePreviousStep ()
  {
    logger.debug ( "handlePreviousStep", "handle previous step" ); //$NON-NLS-1$ //$NON-NLS-2$
  }


  /**
   * Handles the action on the stop button.
   */
  public final void handleStop ()
  {
    logger.debug ( "handleStop", "handle stop" ); //$NON-NLS-1$ //$NON-NLS-2$
  }


  /**
   * Shows the {@link ConvertMachineDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the convert machine dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
