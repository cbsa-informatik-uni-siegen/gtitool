package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.LR0ItemSet;
import de.unisiegen.gtitool.core.entities.LRAction;
import de.unisiegen.gtitool.core.entities.LRActionSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.Word;


/**
 * TODO
 */
public interface LR0Parser
{

  public boolean transit ( LRAction action );


  public void autoTransit ();


  public void start ( Word word );


  public boolean isWordAccepted ();


  public LRActionSet actions ( LR0ItemSet items, TerminalSymbol symbol );
}
