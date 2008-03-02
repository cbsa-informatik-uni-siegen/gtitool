package de.unisiegen.gtitool.ui.model;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


public class DefaultGrammarModel implements DefaultModel, Storable, Modifyable
{

  /**
   * The {@link Grammar}.
   */
  private Grammar grammar;
  
  /**
   * Allocate a new {@link DefaultGrammarModel}.
   * 
   * @param grammar The {@link Grammar}
   */
  public DefaultGrammarModel (Grammar grammar){
    this.grammar = grammar;
  }

  
  /**
   * Returns the {@link Grammar}.
   * 
   * @return the {@link Grammar}.
   */
  public Grammar getGrammar ()
  {
    return this.grammar;
  }


  public Element getElement ()
  {
    // TODO Auto-generated method stub
    return null;
  }


  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    // TODO Auto-generated method stub
    
  }


  public boolean isModified ()
  {
    // TODO Auto-generated method stub
    return false;
  }


  public void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    // TODO Auto-generated method stub
    
  }


  public void resetModify ()
  {
    // TODO Auto-generated method stub
    
  }
}
