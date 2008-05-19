package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;

import javax.swing.JFrame;

import org.jgraph.JGraph;

import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.dfa.DefaultDFA;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraph.StateSetView;
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
   * The current {@link Symbol}.
   */
  private Symbol currentActiveSymbol;


  /**
   * The current original {@link State}.
   */
  private State currentActiveStateOriginal;


  /**
   * The current converted {@link State}.
   */
  private State currentActiveStateConverted;


  /**
   * The current step.
   */
  private int step = 0;


  /**
   * The current x position.
   */
  private int currentX = 120;


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

    handleStart ();
  }


  /**
   * Clears the {@link State} highlighting of the converted {@link JGraph}.
   */
  private final void clearStateHighlightConverted ()
  {
    for ( State currentState : this.machineConverted.getState () )
    {
      currentState.setActive ( false );
    }
  }


  /**
   * Clears the {@link State} highlighting of the original {@link JGraph}.
   */
  private final void clearStateHighlightOriginal ()
  {
    for ( State currentState : this.machineOriginal.getState () )
    {
      currentState.setActive ( false );
    }
  }


  /**
   * Clears the {@link Symbol} highlighting of the converted {@link JGraph}.
   */
  private final void clearSymbolHighlightConverted ()
  {
    for ( Transition currentTransition : this.machineConverted.getTransition () )
    {
      for ( Symbol currentSymbol : currentTransition.getSymbol () )
      {
        currentSymbol.setActive ( false );
      }
    }
  }


  /**
   * Clears the {@link Symbol} highlighting of the original {@link JGraph}.
   */
  private final void clearSymbolHighlightOriginal ()
  {
    for ( Transition currentTransition : this.machineOriginal.getTransition () )
    {
      for ( Symbol currentSymbol : currentTransition.getSymbol () )
      {
        currentSymbol.setActive ( false );
      }
    }
  }


  /**
   * Clears the {@link Transition} highlighting of the converted {@link JGraph}.
   */
  private final void clearTransitionHighlightConverted ()
  {
    for ( Transition currentTransition : this.machineConverted.getTransition () )
    {
      currentTransition.setActive ( false );
    }
  }


  /**
   * Clears the {@link Transition} highlighting of the original {@link JGraph}.
   */
  private final void clearTransitionHighlightOriginal ()
  {
    for ( Transition currentTransition : this.machineOriginal.getTransition () )
    {
      currentTransition.setActive ( false );
    }
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

    if ( this.step == 0 )
    {
      logger.debug ( "handleNextStep", "step 0" ); //$NON-NLS-1$ //$NON-NLS-2$

      clearSymbolHighlightOriginal ();

      for ( State currentState : this.machineOriginal.getState () )
      {
        if ( currentState.isActive () )
        {
          for ( Transition currentTransition : this.currentActiveStateOriginal
              .getTransitionBegin () )
          {
            loopSymbol : for ( Symbol currentSymbol : currentTransition
                .getSymbol () )
            {
              if ( currentSymbol.equals ( this.currentActiveSymbol ) )
              {
                currentSymbol.setActive ( true );
                break loopSymbol;
              }
            }
          }
        }
      }

      this.step = 1;
    }
    else if ( this.step == 1 )
    {
      logger.debug ( "handleNextStep", "step 1" ); //$NON-NLS-1$ //$NON-NLS-2$

      ArrayList < Transition > transitionList = new ArrayList < Transition > ();
      for ( Transition currentTransition : this.machineOriginal
          .getTransition () )
      {
        loopSymbol : for ( Symbol currentSymbol : currentTransition
            .getSymbol () )
        {
          if ( currentSymbol.isActive () )
          {
            transitionList.add ( currentTransition );
            break loopSymbol;
          }
        }
      }

      clearStateHighlightOriginal ();
      clearSymbolHighlightOriginal ();

      for ( Transition currentTransition : transitionList )
      {
        currentTransition.getStateEnd ().setActive ( true );
      }

      this.step = 2;
    }
    else if ( this.step == 2 )
    {
      // TODOCF

      String name = ""; //$NON-NLS-1$
      for ( State currentState : this.machineOriginal.getState () )
      {
        if ( currentState.isActive () )
        {
          name += currentState.getName ();
        }
      }

      State stateFound = null;
      for ( State current : this.machineConverted.getState () )
      {
        if ( current.getName ().equals ( name ) )
        {
          stateFound = current;
          break;
        }
      }

      State newState;
      DefaultStateView newStateView;
      if ( stateFound == null )
      {
        try
        {
          newState = new DefaultState ( this.machineConverted.getAlphabet (),
              this.machineConverted.getPushDownAlphabet (), name, false, false );
          newState.setActive ( true );
        }
        catch ( StateException exc )
        {
          exc.printStackTrace ();
          System.exit ( 1 );
          return;
        }

        this.currentX += 80 + StateSetView.WIDTH;
        newStateView = this.modelConverted.createStateView ( this.currentX,
            100, newState, false );
      }
      else
      {
        newState = stateFound;
        newState.setActive ( true );
        newStateView = this.modelConverted.getStateViewForState ( newState );
      }

      Transition transition;
      try
      {
        transition = new DefaultTransition ( this.machineConverted
            .getAlphabet (), this.machineConverted.getPushDownAlphabet (),
            new DefaultWord (), new DefaultWord (),
            this.currentActiveStateConverted, newState, new DefaultSymbol (
                this.currentActiveSymbol.getName () ) );
        transition.setActive ( true );
      }
      catch ( TransitionSymbolNotInAlphabetException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
        return;
      }
      catch ( TransitionSymbolOnlyOneTimeException exc )
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

      this.modelConverted.createTransitionView ( transition,
          this.modelConverted
              .getStateViewForState ( this.currentActiveStateConverted ),
          newStateView, false, false, true );

      this.step = 3;
    }
    else if ( this.step == 3 )
    {
      clearStateHighlightOriginal ();
      clearStateHighlightConverted ();
      clearTransitionHighlightOriginal ();
      clearTransitionHighlightConverted ();
      clearTransitionHighlightOriginal ();
      clearSymbolHighlightConverted ();

      // calculate next symbol
      boolean nextState = false;
      int index = -1;
      for ( int i = 0 ; i < this.machineConverted.getAlphabet ().size () ; i++ )
      {
        if ( this.currentActiveSymbol == this.machineConverted.getAlphabet ()
            .get ( i ) )
        {
          index = i;
          if ( i == this.machineConverted.getAlphabet ().size () - 1 )
          {
            nextState = true;
          }
          break;
        }
      }

      if ( nextState )
      {
        this.currentActiveSymbol = this.machineConverted.getAlphabet ()
            .get ( 0 );
        // TODOCF
      }
      else
      {
        this.currentActiveSymbol = this.machineConverted.getAlphabet ().get (
            index + 1 );
      }

      this.step = 4;
    }
    else if ( this.step == 4 )
    {
      this.currentActiveStateOriginal.setActive ( true );
      this.currentActiveStateConverted.setActive ( true );

      this.step = 0;
    }
    this.jGraphOriginal.repaint ();
    this.jGraphConverted.repaint ();
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
   * Handles the start action.
   */
  private final void handleStart ()
  {
    State startState = null;
    for ( State current : this.machineOriginal.getState () )
    {
      if ( current.isStartState () )
      {
        startState = current;
        break;
      }
    }
    if ( startState == null )
    {
      throw new NullPointerException ( "no start state" ); //$NON-NLS-1$
    }
    startState.setActive ( true );

    State newState;
    try
    {
      newState = new DefaultState ( this.machineConverted.getAlphabet (),
          this.machineConverted.getPushDownAlphabet (), startState.getName (),
          true, false );
      newState.setActive ( true );
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    this.currentX = StateSetView.WIDTH;
    this.modelConverted.createStateView ( this.currentX, 100, newState, false );

    // store the first values
    this.currentActiveStateOriginal = startState;
    this.currentActiveStateConverted = newState;
    this.currentActiveSymbol = this.machineConverted.getAlphabet ().get ( 0 );
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
