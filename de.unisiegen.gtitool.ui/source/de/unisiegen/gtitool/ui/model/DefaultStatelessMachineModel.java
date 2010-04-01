package de.unisiegen.gtitool.ui.model;


import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;
import de.unisiegen.gtitool.core.grammars.cfg.LR0Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.LR1Grammar;
import de.unisiegen.gtitool.core.machines.StatelessMachine;
import de.unisiegen.gtitool.core.machines.lr.DefaultLR0Parser;
import de.unisiegen.gtitool.core.machines.lr.DefaultLR1Parser;
import de.unisiegen.gtitool.core.machines.lr.SLRParser;
import de.unisiegen.gtitool.core.machines.pda.DefaultTDP;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;


/**
 * The Model of the {@link StatelessMachine}s
 * 
 * @author Christian Uhrhan, Philipp Reh
 */
public class DefaultStatelessMachineModel extends DefaultMachineModel
{

  /**
   * the {@link StatelessMachine}
   */
  private StatelessMachine machine;


  /**
   * the {@link Grammar}
   */
  private Grammar grammar;


  /**
   * Allocates a new {@link DefaultStatelessMachineModel}
   * 
   * @param machine the {@link StatelessMachine}
   */
  public DefaultStatelessMachineModel ( final StatelessMachine machine )
  {
    this.machine = machine;
  }


  /**
   * Constructs a new DefaultStatelessMachineModel that has been previously
   * saved to an XML Element.
   * 
   * @param element
   * @param overwrittenMachineType
   * @throws Exception
   */
  public DefaultStatelessMachineModel ( final Element element,
      final String overwrittenMachineType ) throws Exception
  {
    final String machineName = overwrittenMachineType != null ? overwrittenMachineType
        : element.getAttributeByName ( "machineType" ).getValue (); //$NON-NLS-1$

    final Element grammarElement = element.getElementByName ( "Grammar" ); //$NON-NLS-1$

    this.grammar = new DefaultCFG ( grammarElement );

    if ( machineName.equals ( "LR0Parser" ) || machineName.equals ( "SLR" ) ) //$NON-NLS-1$ //$NON-NLS-2$
    {
      this.grammar = new LR0Grammar ( this.grammar.getNonterminalSymbolSet (),
          this.grammar.getTerminalSymbolSet (), this.grammar.getStartSymbol (),
          this.grammar.getProduction () );
    }
    else if ( machineName.equals ( "LR1Parser" ) ) //$NON-NLS-1$
    {
      this.grammar = new LR1Grammar ( this.grammar.getNonterminalSymbolSet (),
          this.grammar.getTerminalSymbolSet (), this.grammar.getStartSymbol (),
          this.grammar.getProduction () );
    }

    if ( machineName.equals ( "LR0Parser" ) ) //$NON-NLS-1$
    {
      this.machine = new DefaultLR0Parser ( ( LR0Grammar ) this.grammar );
    }
    else if ( machineName.equals ( "LR1Parser" ) ) //$NON-NLS-1$
    {
      this.machine = new DefaultLR1Parser ( new LR1Grammar ( this.grammar ) );
    }
    else if ( machineName.equals ( "SLR" ) ) //$NON-NLS-1$
    {
      this.machine = new SLRParser ( ( LR0Grammar ) this.grammar );
    }
    else if ( machineName.equals ( "TDP" ) ) //$NON-NLS-1$
    {
      this.machine = new DefaultTDP ( ( CFG ) this.grammar );
    }
    else
    {
      throw new RuntimeException ( "Cannot create the machine!" ); //$NON-NLS-1$
    }
  }


  /**
   * Sets the {@link Grammar}
   * 
   * @param grammar The {@link Grammar}
   */
  public void setGrammar ( final Grammar grammar )
  {
    this.grammar = grammar;
  }


  /**
   * Returns the {@link Grammar}
   * 
   * @return The {@link Grammar}
   */
  public Grammar getGrammar ()
  {
    return this.grammar;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.model.DefaultMachineModel#getMachine()
   */
  @Override
  public StatelessMachine getMachine ()
  {
    return this.machine;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.model.DefaultModel#getElement()
   */
  @Override
  public Element getElement ()
  {
    final Element newElement = new Element ( "StatelessMachineModel" ); //$NON-NLS-1$

    newElement.addAttribute ( new Attribute ( "machineType", this.machine //$NON-NLS-1$
        .getMachineType ().toString () ) );

    newElement.addElement ( this.getGrammar ().getElement () );

    return newElement;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#addModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    return;
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
    return;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#resetModify()
   */
  public void resetModify ()
  {
    return;
  }

}
