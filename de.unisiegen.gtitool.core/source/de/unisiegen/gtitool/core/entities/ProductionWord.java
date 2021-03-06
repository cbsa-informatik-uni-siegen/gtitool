package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The {@link ProductionWord} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface ProductionWord extends Entity < ProductionWord >, Storable,
    Modifyable, Iterable < ProductionWordMember >
{

  /**
   * Appends the specified {@link ProductionWordMember}s to the end of this
   * {@link ProductionWord}.
   * 
   * @param productionWordMembers The {@link ProductionWordMember}s to be
   *          appended to this {@link ProductionWord}.
   */
  public void add ( Iterable < ProductionWordMember > productionWordMembers );


  /**
   * Appends the specified {@link ProductionWordMember} to the end of this
   * {@link ProductionWord}.
   * 
   * @param productionWordMember The {@link ProductionWordMember} to be appended
   *          to this {@link ProductionWord}.
   */
  public void add ( ProductionWordMember productionWordMember );


  /**
   * Appends the specified {@link ProductionWordMember}s to the end of this
   * {@link ProductionWord}.
   * 
   * @param productionWordMembers The {@link ProductionWordMember}s to be
   *          appended to this {@link ProductionWord}.
   */
  public void add ( ProductionWordMember ... productionWordMembers );


  /**
   * Returns the {@link ProductionWordMember}s.
   * 
   * @return The {@link ProductionWordMember}s.
   */
  public ArrayList < ProductionWordMember > get ();


  /**
   * Returns the {@link ProductionWordMember} with the given index.
   * 
   * @param index The index.
   * @return The {@link ProductionWordMember} with the given index.
   */
  public ProductionWordMember get ( int index );


  /**
   * Returns the {@link NonterminalSymbol}s the word contains
   * 
   * @return The {@link NonterminalSymbol}s the word contains
   */
  public ArrayList < NonterminalSymbol > getNonterminals ();


  /**
   * Returns the size of the {@link ProductionWord}.
   * 
   * @return the size of the {@link ProductionWord}.
   */
  public int size ();
}
