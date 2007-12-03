package de.unisiegen.gtitool.core.storage;


import java.util.ArrayList;



/**
 * The <code>Element</code> class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Element
{

  /**
   * The name.
   */
  private String name;


  /**
   * The {@link Attribute} list.
   */
  private ArrayList < Attribute > attributeList;


  /**
   * The <code>Element</code> list.
   */
  private ArrayList < Element > elementList;


  /**
   * Allocates a new <code>Element</code>.
   * 
   * @param pName The name of this <code>Attribute</code>.
   */
  public Element ( String pName )
  {
    // Name
    setName ( pName );
    this.attributeList = new ArrayList < Attribute > ();
    this.elementList = new ArrayList < Element > ();
  }


  /**
   * Adds the {@link Attribute}.
   * 
   * @param pAttribute The {@link Attribute} to add.
   */
  public final void addAttribute ( Attribute pAttribute )
  {
    this.attributeList.add ( pAttribute );
  }


  /**
   * Adds the <code>Element</code>.
   * 
   * @param pElement The <code>Element</code> to add.
   */
  public final void addElement ( Element pElement )
  {
    this.elementList.add ( pElement );
  }


  /**
   * Returns the attribute list.
   * 
   * @return The attribute list.
   * @see #attributeList
   */
  public final ArrayList < Attribute > getAttribute ()
  {
    return this.attributeList;
  }


  /**
   * Returns the attribute with the given index.
   * 
   * @param pIndex The index to return.
   * @return The attribute list with the given index.
   * @see #attributeList
   */
  public final Attribute getAttribute ( int pIndex )
  {
    return this.attributeList.get ( pIndex );
  }


  /**
   * Returns the {@link Attribute} with the given name.
   * 
   * @param pName The name of the {@link Attribute}.
   * @return The {@link Attribute} with the given name.
   */
  public final String getAttribute ( String pName )
  {
    for ( Attribute current : this.attributeList )
    {
      if ( current.getName ().equals ( pName ) )
      {
        return current.getValue ();
      }
    }
    return null;
  }


  /**
   * Returns the element list.
   * 
   * @return The element list.
   * @see #elementList
   */
  public final ArrayList < Element > getElement ()
  {
    return this.elementList;
  }


  /**
   * Returns the element with the given index.
   * 
   * @param pIndex The index to return.
   * @return The element list with the given index.
   * @see #elementList
   */
  public final Element getElement ( int pIndex )
  {
    return this.elementList.get ( pIndex );
  }


  /**
   * Returns the name.
   * 
   * @return The name.
   * @see #name
   */
  public final String getName ()
  {
    return this.name;
  }


  /**
   * Sets the name.
   * 
   * @param pName The name to set.
   * @see #name
   */
  public final void setName ( String pName )
  {
    if ( pName == null )
    {
      throw new NullPointerException ();
    }
    this.name = pName;
  }
}
