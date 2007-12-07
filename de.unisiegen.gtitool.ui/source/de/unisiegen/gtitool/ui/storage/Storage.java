package de.unisiegen.gtitool.ui.storage;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;


/**
 * The <code>Storage</code> class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Storage
{

  /**
   * The single instance of the <code>Storage<code>
   */
  private static Storage singleStorage;


  /**
   * The name of a supported {@link Charset}.
   */
  private static final String CHARSET_NAME = "UTF8"; //$NON-NLS-1$


  /**
   * Returns the single instance of the <code>Storage</code>.
   * 
   * @return The single instance of the <code>Storage</code>.
   */
  public final static Storage getInstance ()
  {
    if ( singleStorage == null )
    {
      singleStorage = new Storage ();
    }
    return singleStorage;
  }


  /**
   * The used
   */
  private BufferedWriter writer;


  /**
   * Allocates a new <code>Storage</code>.
   */
  private Storage ()
  {
    // Do nothing
  }


  /**
   * Returns the {@link Element}.
   * 
   * @param pNode The input {@link Node}.
   * @return The {@link Element}.
   */
  private final Element getElement ( Node pNode )
  {
    Element newElement = new Element ( pNode.getNodeName () );
    if ( pNode.getAttributes () != null )
    {
      for ( int i = 0 ; i < pNode.getAttributes ().getLength () ; i++ )
      {
        Node current = pNode.getAttributes ().item ( i );
        if ( current.getNodeType () == Node.ATTRIBUTE_NODE )
        {
          newElement.addAttribute ( new Attribute ( current.getNodeName (),
              current.getNodeValue () ) );
        }
      }
    }
    if ( pNode.getChildNodes () != null )
    {
      for ( int i = 0 ; i < pNode.getChildNodes ().getLength () ; i++ )
      {
        Node current = pNode.getChildNodes ().item ( i );
        if ( current.getNodeType () == Node.ELEMENT_NODE )
        {
          newElement.addElement ( getElement ( current ) );
        }
      }
    }
    return newElement;
  }


  /**
   * Loads the {@link Storable} from the given file name.
   * 
   * @param pFileName The file name.
   * @return The {@link Storable} from the given file name.
   * @throws StoreException If the file could not be loaded.
   */
  public final Storable load ( String pFileName ) throws StoreException
  {
    try
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
      DocumentBuilder builder = factory.newDocumentBuilder ();
      Document document = builder.parse ( new File ( pFileName ) );
      Element element = getElement ( document.getDocumentElement () );
      if ( element.getName ().equals ( "Alphabet" ) ) //$NON-NLS-1$
      {
        return new Alphabet ( element );
      }
      else if ( element.getName ().equals ( "Symbol" ) ) //$NON-NLS-1$
      {
        return new Symbol ( element );
      }
      else if ( element.getName ().equals ( "Word" ) ) //$NON-NLS-1$
      {
        return new Word ( element );
      }
      else if ( element.getName ().equals ( "State" ) ) //$NON-NLS-1$
      {
        return new State ( element );
      }
      else if ( element.getName ().equals ( "Transition" ) ) //$NON-NLS-1$
      {
        return new Transition ( element );
      }
      else if ( element.getName ().equals ( "DefaultMachineModel") ) //$NON-NLS-1$
        return new DefaultMachineModel ( element );
    }
    catch ( StoreException exc )
    {
      throw exc;
    }
    catch ( Exception exc )
    {
      throw new StoreException ( Messages.getString ( "StoreException.Load" ) ); //$NON-NLS-1$
    }
    return null;
  }


  /**
   * Writes a string to the {@link BufferedWriter}.
   * 
   * @param pText The text which should be written to the {@link BufferedWriter}.
   * @throws IOException If an I/O error occurs.
   */
  private final void print ( String pText ) throws IOException
  {
    this.writer.write ( pText );
  }


  /**
   * Writes a new line to the {@link BufferedWriter}.
   * 
   * @throws IOException If an I/O error occurs.
   */
  private final void println () throws IOException
  {
    this.writer.newLine ();
  }


  /**
   * Writes a string and a new line to the {@link BufferedWriter}.
   * 
   * @param pText The text which should be written to the {@link BufferedWriter}.
   * @throws IOException If an I/O error occurs.
   */
  private final void println ( String pText ) throws IOException
  {
    this.writer.write ( pText );
    this.writer.newLine ();
  }


  /**
   * Stores the given {@link Element} with the given indent.
   * 
   * @param pElement The {@link Element} to store.
   * @param pIndent The used indent.
   * @throws IOException If an I/O error occurs.
   */
  private final void store ( Element pElement, int pIndent ) throws IOException
  {
    StringBuilder indent = new StringBuilder ();
    for ( int i = 0 ; i < pIndent ; i++ )
    {
      indent.append ( " " ); //$NON-NLS-1$
    }
    print ( indent.toString () );
    print ( "<" ); //$NON-NLS-1$
    print ( pElement.getName () );
    for ( Attribute current : pElement.getAttribute () )
    {
      print ( " " ); //$NON-NLS-1$
      print ( current.getName () );
      print ( "=" ); //$NON-NLS-1$
      print ( "\"" ); //$NON-NLS-1$
      print ( current.getValue () );
      print ( "\"" ); //$NON-NLS-1$
    }
    if ( pElement.getElement ().size () == 0 )
    {
      print ( "/>" ); //$NON-NLS-1$
    }
    else
    {
      println ( ">" ); //$NON-NLS-1$
      for ( Element current : pElement.getElement () )
      {
        store ( current, pIndent + 2 );
      }
      print ( indent.toString () );
      print ( "</" ); //$NON-NLS-1$
      print ( pElement.getName () );
      print ( ">" ); //$NON-NLS-1$
    }
    if ( pIndent != 0 )
    {
      println ();
    }
  }


  /**
   * Stores the given {@link Storable} to the given file name.
   * 
   * @param pStorable The {@link Storable} to store.
   * @param pFileName The used file name.
   * @throws StoreException If the file could not be loaded.
   */
  public final void store ( Storable pStorable, String pFileName )
      throws StoreException
  {
    try
    {
      this.writer = new BufferedWriter ( new OutputStreamWriter (
          new FileOutputStream ( pFileName ), CHARSET_NAME ) );
      println ( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" ); //$NON-NLS-1$
      println ();
      store ( pStorable.getElement (), 0 );
      this.writer.close ();
    }
    catch ( Exception exc )
    {
      throw new StoreException ( Messages.getString ( "StoreException.Store" ) ); //$NON-NLS-1$
    }
  }
}
