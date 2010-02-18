package de.unisiegen.gtitool.ui.convert;


import java.util.ArrayList;
import java.util.TreeMap;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.machines.AbstractMachine;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * TODO
 */
public abstract class ConvertToLR extends AbstractConvertGrammar
{

  /**
   * TODO
   * 
   * @param mainWindowForm
   * @param grammar
   */
  public ConvertToLR ( MainWindowForm mainWindowForm, Grammar grammar,
      Alphabet alphabet )
  {
    super ( mainWindowForm, grammar, alphabet );

    this.setPushDownAlphabet ( new DefaultAlphabet () );
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammar#performProductions()
   */
  private void createTransition ( Word read, Word write,
      DefaultStateView source, DefaultStateView target, Symbol symbol )
  {
    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
    symbols.add ( symbol );
    createTransition ( read, write, source, target, symbols );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammar#performProductions()
   */
  @Override
  protected void performProductions ()
  {
    ArrayList < State > copy = new ArrayList < State > ( this.getMachine ()
        .getState () );

    ArrayList < Transition > transCopy = new ArrayList < Transition > ( this
        .getMachine ().getTransition () );

    TreeMap < State, DefaultStateView > stateViews = new TreeMap < State, DefaultStateView > ();

    for ( State state : copy )
      stateViews.put ( state, createStateViewFromState ( state ) );

    for ( Transition transition : transCopy )
      this.getModel ().createTransitionView ( transition,
          stateViews.get ( transition.getStateBegin () ),
          stateViews.get ( transition.getStateEnd () ), true, false, false );
  }


  /**
   * Returns the current machine
   * 
   * @return The current machine
   */
  protected abstract AbstractMachine getMachine ();
}
