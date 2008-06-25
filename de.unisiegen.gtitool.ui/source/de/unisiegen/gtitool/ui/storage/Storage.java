package de.unisiegen.gtitool.ui.storage;


import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;


/**
 * The {@link Storage} class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Storage
{

  /**
   * The single instance of the {@link Storage}.
   */
  private static Storage singleStorage;


  /**
   * The name of a supported {@link Charset}.
   */
  private static final String CHARSET_NAME = "UTF8"; //$NON-NLS-1$


  /**
   * The debug flag.
   */
  private static final boolean DEBUG = false;


  /**
   * Returns the single instance of the {@link Storage}.
   * 
   * @return The single instance of the {@link Storage}.
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
   * Allocates a new {@link Storage}.
   */
  private Storage ()
  {
    // Do nothing
  }


  /**
   * Returns the {@link Element}.
   * 
   * @param node The input {@link Node}.
   * @return The {@link Element}.
   */
  private final Element getElement ( Node node )
  {
    Element newElement = new Element ( node.getNodeName () );
    if ( node.getAttributes () != null )
    {
      for ( int i = 0 ; i < node.getAttributes ().getLength () ; i++ )
      {
        Node current = node.getAttributes ().item ( i );
        if ( current.getNodeType () == Node.ATTRIBUTE_NODE )
        {
          newElement.addAttribute ( new Attribute ( current.getNodeName (),
              current.getNodeValue () ) );
        }
      }
    }
    if ( node.getChildNodes () != null )
    {
      for ( int i = 0 ; i < node.getChildNodes ().getLength () ; i++ )
      {
        Node current = node.getChildNodes ().item ( i );
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
   * @param file The {@link File}.
   * @return The {@link Storable} from the given file name.
   * @throws StoreException If the file could not be loaded.
   */
  public final Storable load ( File file ) throws StoreException
  {
    try
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
      DocumentBuilder builder = factory.newDocumentBuilder ();
      Document document = builder.parse ( file );
      Element element = getElement ( document.getDocumentElement () );

      if ( element.getName ().equals ( "MachineModel" ) ) //$NON-NLS-1$
      {
        return new DefaultMachineModel ( element, null );
      }
      if ( element.getName ().equals ( "GrammarModel" ) ) //$NON-NLS-1$
      {
        return new DefaultGrammarModel ( element, null );
      }
      throw new StoreException ( Messages.getString ( "StoreException.Readed" ) ); //$NON-NLS-1$
    }
    catch ( ParserConfigurationException exc )
    {
      if ( DEBUG )
      {
        exc.printStackTrace ();
      }
      throw new StoreException ( Messages.getString ( "StoreException.Parse" ) ); //$NON-NLS-1$
    }
    catch ( SAXException exc )
    {
      if ( DEBUG )
      {
        exc.printStackTrace ();
      }
      throw new StoreException ( Messages.getString ( "StoreException.Parse" ) ); //$NON-NLS-1$
    }
    catch ( IOException exc )
    {
      if ( DEBUG )
      {
        exc.printStackTrace ();
      }
      throw new StoreException ( Messages.getString ( "StoreException.Readed" ) ); //$NON-NLS-1$
    }
    catch ( Exception exc )
    {
      if ( DEBUG )
      {
        exc.printStackTrace ();
      }
      Throwable exception;
      if ( exc instanceof InvocationTargetException )
      {
        exception = exc.getCause ();
      }
      else
      {
        exception = exc;
      }

      if ( exception instanceof StoreException )
      {
        throw ( StoreException ) exception;
      }
      if ( exception instanceof AlphabetException )
      {
        throw new StoreException ( ( ( AlphabetException ) exception )
            .getPrettyDescription ().toString () );
      }
      if ( exception instanceof SymbolException )
      {
        throw new StoreException ( ( ( SymbolException ) exception )
            .getPrettyDescription ().toString () );
      }
      if ( exception instanceof StateException )
      {
        throw new StoreException ( ( ( StateException ) exception )
            .getPrettyDescription ().toString () );
      }
      if ( exception instanceof TransitionException )
      {
        throw new StoreException ( ( ( TransitionException ) exception )
            .getPrettyDescription ().toString () );
      }
      throw new StoreException ( Messages.getString ( "StoreException.Readed" ) ); //$NON-NLS-1$
    }
  }


  /**
   * Loads the {@link Element} from the given text.
   * 
   * @param text The text to parse.
   * @return The {@link Element} from the text.
   * @throws StoreException If the file could not be loaded.
   */
  public final Element load ( String text ) throws StoreException
  {
    try
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
      DocumentBuilder builder = factory.newDocumentBuilder ();
      byte bytes[] = text.getBytes ();
      ByteArrayInputStream inputStream = new ByteArrayInputStream ( bytes );
      Document document = builder.parse ( inputStream );
      return getElement ( document.getDocumentElement () );
    }
    catch ( ParserConfigurationException exc )
    {
      if ( DEBUG )
      {
        exc.printStackTrace ();
      }
      throw new StoreException ( Messages.getString ( "StoreException.Parse" ) ); //$NON-NLS-1$
    }
    catch ( SAXException exc )
    {
      if ( DEBUG )
      {
        exc.printStackTrace ();
      }
      throw new StoreException ( Messages.getString ( "StoreException.Parse" ) ); //$NON-NLS-1$
    }
    catch ( IOException exc )
    {
      if ( DEBUG )
      {
        exc.printStackTrace ();
      }
      throw new StoreException ( Messages.getString ( "StoreException.Readed" ) ); //$NON-NLS-1$
    }
    catch ( Exception exc )
    {
      if ( DEBUG )
      {
        exc.printStackTrace ();
      }
      Throwable exception;
      if ( exc instanceof InvocationTargetException )
      {
        exception = exc.getCause ();
      }
      else
      {
        exception = exc;
      }

      if ( exception instanceof StoreException )
      {
        throw ( StoreException ) exception;
      }
      if ( exception instanceof AlphabetException )
      {
        throw new StoreException ( ( ( AlphabetException ) exception )
            .getPrettyDescription ().toString () );
      }
      if ( exception instanceof SymbolException )
      {
        throw new StoreException ( ( ( SymbolException ) exception )
            .getPrettyDescription ().toString () );
      }
      if ( exception instanceof StateException )
      {
        throw new StoreException ( ( ( StateException ) exception )
            .getPrettyDescription ().toString () );
      }
      if ( exception instanceof TransitionException )
      {
        throw new StoreException ( ( ( TransitionException ) exception )
            .getPrettyDescription ().toString () );
      }
      throw new StoreException ( Messages.getString ( "StoreException.Readed" ) ); //$NON-NLS-1$
    }
  }


  /**
   * Stores the given {@link Storable} to the given file name.
   * 
   * @param storable The {@link Storable} to store.
   * @param file The used {@link File}.
   * @throws StoreException If the file could not be loaded.
   */
  public final void store ( Storable storable, File file )
      throws StoreException
  {
    try
    {
      this.writer = new BufferedWriter ( new OutputStreamWriter (
          new FileOutputStream ( file ), CHARSET_NAME ) );
      this.writer.write ( storable.getElement ().getStoreString () );
      this.writer.close ();
    }
    catch ( Exception exc )
    {
      if ( DEBUG )
      {
        exc.printStackTrace ();
      }
      throw new StoreException ( Messages.getString ( "StoreException.Store" ) ); //$NON-NLS-1$
    }
  }
}
