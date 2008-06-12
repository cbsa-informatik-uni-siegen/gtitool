package de.unisiegen.gtitool.core.storage;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * The {@link Element} class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Element implements Serializable
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 574828409775987118L;


  /**
   * The {@link Attribute} list.
   */
  private ArrayList < Attribute > attributeList;


  /**
   * The {@link Element} list.
   */
  private ArrayList < Element > elementList;


  /**
   * The name.
   */
  private String name;


  /**
   * Allocates a new {@link Element}.
   * 
   * @param name The name of this {@link Attribute}.
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
   * Adds the {@link Element}.
   * 
   * @param element The {@link Element} to add.
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
   * Escapes the input {@link String}.
   * 
   * @param input The input {@link String}.
   * @return The escaped input {@link String}.
   */
  private final String escape ( String input )
  {
    String result = input;
    result = result.replaceAll ( "&", "&amp;" ); //$NON-NLS-1$ //$NON-NLS-2$
    result = result.replaceAll ( "<", "&lt;" ); //$NON-NLS-1$ //$NON-NLS-2$
    result = result.replaceAll ( ">", "&gt;" ); //$NON-NLS-1$ //$NON-NLS-2$
    result = result.replaceAll ( "\"", "&quot;" ); //$NON-NLS-1$ //$NON-NLS-2$
    result = result.replaceAll ( "\'", "&apos;" ); //$NON-NLS-1$ //$NON-NLS-2$
    return result;
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
   * Returns the string to store.
   * 
   * @return The string to store.
   * @throws IOException If an I/O error occurs.
   */
  public final String getStoreString () throws IOException
  {
    StringBuilder result = new StringBuilder ();
    result.append ( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" ); //$NON-NLS-1$
    result.append ( System.getProperty ( "line.separator" ) ); //$NON-NLS-1$
    result.append ( System.getProperty ( "line.separator" ) ); //$NON-NLS-1$
    result.append ( getStoreString ( 0 ) );
    return result.toString ();
  }


  /**
   * Returns the string to store.
   * 
   * @param indent The used indent.
   * @return The string to store.
   * @throws IOException If an I/O error occurs.
   */
  private final String getStoreString ( int indent ) throws IOException
  {
    StringBuilder result = new StringBuilder ();
    StringBuilder indentText = new StringBuilder ();
    for ( int i = 0 ; i < indent ; i++ )
    {
      indentText.append ( " " ); //$NON-NLS-1$
    }
    result.append ( indentText.toString () );
    result.append ( "<" ); //$NON-NLS-1$
    result.append ( this.name );
    for ( Attribute current : this.attributeList )
    {
      result.append ( " " ); //$NON-NLS-1$
      result.append ( current.getName () );
      result.append ( "=" ); //$NON-NLS-1$
      result.append ( "\"" ); //$NON-NLS-1$
      result.append ( escape ( current.getValue () ) );
      result.append ( "\"" ); //$NON-NLS-1$
    }
    if ( this.elementList.size () == 0 )
    {
      result.append ( "/>" ); //$NON-NLS-1$
    }
    else
    {
      result.append ( ">" ); //$NON-NLS-1$
      result.append ( System.getProperty ( "line.separator" ) ); //$NON-NLS-1$
      for ( Element current : this.elementList )
      {
        result.append ( current.getStoreString ( indent + 2 ) );
      }
      result.append ( indentText.toString () );
      result.append ( "</" ); //$NON-NLS-1$
      result.append ( this.name );
      result.append ( ">" ); //$NON-NLS-1$
    }
    if ( indent != 0 )
    {
      result.append ( System.getProperty ( "line.separator" ) ); //$NON-NLS-1$
    }
    return result.toString ();
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
