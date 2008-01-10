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
   * The {@link Attribute} list.
   */
  private ArrayList < Attribute > attributeList;


  /**
   * The <code>Element</code> list.
   */
  private ArrayList < Element > elementList;


  /**
   * The name.
   */
  private String name;


  /**
   * Allocates a new <code>Element</code>.
   * 
   * @param name The name of this <code>Attribute</code>.
   */
  public Element ( String name )
  {
    // Name
    setName ( name );
    this.attributeList = new ArrayList < Attribute > ();
    this.elementList = new ArrayList < Element > ();
  }


  /**
   * Adds the {@link Attribute}.
   * 
   * @param attribute The {@link Attribute} to add.
   */
  public final void addAttribute ( Attribute attribute )
  {
    this.attributeList.add ( attribute );
  }


  /**
   * Adds the <code>Element</code>.
   * 
   * @param element The <code>Element</code> to add.
   */
  public final void addElement ( Element element )
  {
    this.elementList.add ( element );
  }


  /**
   * Adds the {@link Storable}.
   * 
   * @param storable The {@link Storable} to add.
   */
  public final void addElement ( Storable storable )
  {
    this.elementList.add ( storable.getElement () );
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
   * @param index The index to return.
   * @return The attribute list with the given index.
   * @see #attributeList
   */
  public final Attribute getAttribute ( int index )
  {
    return this.attributeList.get ( index );
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
   * @param index The index to return.
   * @return The element list with the given index.
   * @see #elementList
   */
  public final Element getElement ( int index )
  {
    return this.elementList.get ( index );
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
   * @param name The name to set.
   * @see #name
   */
  public final void setName ( String name )
  {
    if ( name == null )
    {
      throw new NullPointerException ();
    }
    if ( name.length () == 0 )
    {
      throw new IllegalArgumentException ( "name is empty" ); //$NON-NLS-1$
    }
    this.name = name;
  }
}
