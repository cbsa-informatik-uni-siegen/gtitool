package de.unisiegen.gtitool.core.machines.dfa;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultLRStateStack;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.entities.LRStateStack;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.grammars.cfg.ExtendedGrammar;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.machines.AbstractStateMachine;
import de.unisiegen.gtitool.core.storage.Element;


/**
 * The abstract base class for LR automata
 */
public abstract class AbstractLR extends AbstractStateMachine implements DFA
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 1L;


  /**
   * Constructs an AbstractLR automaton from an extended grammar
   * 
   * @param grammar
   * @throws AlphabetException
   */
  protected AbstractLR ( final ExtendedGrammar grammar )
      throws AlphabetException
  {
    this ( grammar.getAlphabet (), grammar );
  }


  /**
   * TODO
   * 
   * @param alphabet
   * @param grammar
   */
  protected AbstractLR ( final Alphabet alphabet, final ExtendedGrammar grammar )
  {
    super ( alphabet, new DefaultAlphabet (), false,
        ValidationElement.FINAL_STATE, ValidationElement.STATE_NAME,
        ValidationElement.SYMBOL_ONLY_ONE_TIME );

    this.grammar = grammar;
  }


  /**
   * Read the next production word member
   * 
   * @param symbol
   */
  public void nextSymbol ( final ProductionWordMember symbol )
  {
    this.nextSymbol ( this.getMatchingTransition ( this.getCurrentState (),
        new DefaultSymbol ( symbol.toString () ) ) );

    pushCurrentStateToStack ();
  }


  /**
   * Pushes the current state onto the state stack
   */
  private void pushCurrentStateToStack ()
  {
    this.stateStack.push ( ( LRState ) this.getCurrentState () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStateMachine#previousSymbol()
   */
  @Override
  public void previousSymbol ()
  {
    try
    {
      this.stateStack.pop ();
    }
    catch ( Exception e )
    {
      // The state stack may be empty when the automaton is run "alone"
    }

    super.previousSymbol ();
  }


  /**
   * Gets the current matching transition for a given state and symbol
   * 
   * @param fromState - The state to go from
   * @param symbol - The symbol to read
   * @return The matching transition or null
   */
  private Transition getMatchingTransition ( final State fromState,
      final Symbol symbol )
  {
    Transition ret = null;

    for ( Transition transition : this.getTransition () )
      if ( transition.getStateBegin ().equals ( fromState )
          && transition.getSymbol ().contains ( symbol ) )
      {
        if ( ret != null )
        {
          System.err.println ( "Multiple matching transitions!" ); //$NON-NLS-1$
          System.exit ( 1 );
        }
        ret = transition;
      }

    return ret;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStateMachine#getElement()
   */
  @Override
  public Element getElement ()
  {
    return this.grammar.getElement ();
  }


  /**
   * Returns the associated grammar
   * 
   * @return the grammar
   */
  public ExtendedGrammar getGrammar ()
  {
    return this.grammar;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public boolean epsilonColumnNeeded ()
  {
    return false;
  }


  /**
   * The machine's type
   * 
   * @return The machine's type
   * @see de.unisiegen.gtitool.core.machines.AbstractStateMachine#getMachineType()
   */
  @Override
  public abstract MachineType getMachineType ();


  /**
   * Get all LR states
   * 
   * @return the states
   */
  public ArrayList < LRState > getStates ()
  {
    ArrayList < LRState > ret = new ArrayList < LRState > ();

    for ( State state : getState () )
      ret.add ( ( LRState ) state );

    return ret;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStateMachine#getValueAt(int,
   *      int)
   */
  @Override
  public final Object getValueAt ( final int rowIndex, final int columnIndex )
  {
    if ( columnIndex == STATE_COLUMN )
      return this.getState ().get ( rowIndex );
    return super.getValueAt ( rowIndex, columnIndex );
  }


  /**
   * Get the current state stack
   * 
   * @return the state stack
   */
  public LRStateStack getStateStack ()
  {
    return this.stateStack;
  }


  /**
   * Sets the current state stack
   * 
   * @param stateStack
   */
  public void setStateStack ( final LRStateStack stateStack )
  {
    this.stateStack = stateStack;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStateMachine#onStart()
   */
  @Override
  protected void onStart ()
  {
    this.pushCurrentStateToStack ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStateMachine#onStop()
   */
  @Override
  protected void onStop ()
  {
    this.stateStack.clear ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStateMachine#getTableCaption()
   */
  @Override
  protected String getTableCaption ()
  {
    return Messages.getString ( "Machines.LR.TableCaption" ); //$NON-NLS-1$
  }


  /**
   * The associated grammar
   */
  private ExtendedGrammar grammar;


  /**
   * The state stack (just used for display)
   */
  private LRStateStack stateStack = new DefaultLRStateStack ();
}
