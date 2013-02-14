package com.alvarolara.burgostv.utiles;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

/**
 * Parsea un XML.
 * @author Alvaro Lara Cano
 *
 */
public class ParseadorXML {
	
	/**
	 * Documento W3C.
	 */
	private Document documento;
	
	/**
	 * Lista de nodos.
	 */
	private NodeList nodeList;
	
	/**
	 * Elemento W3C.
	 */
	private Element elemento;
	
	
	/**
	 * Crea el parseador con una url y una clave.
	 * @param url
	 * @param key
	 */
	public ParseadorXML(String url, String key){
		String xml = getXmlDeUrl(url);
		documento = getElementoDom(xml);
		nodeList = documento.getElementsByTagName(key);
		elemento = (Element) nodeList.item(0);
	}

	/**
	 * Obtener el XML de las noticias de Internet.
	 * 
	 * @param url
	 * @return
	 */
	public String getXmlDeUrl(String url) {
		String xml = null;

		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			xml = EntityUtils.toString(httpEntity);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return xml;
	}

	
	/**
	 * Obtiene el documento DOM.
	 * @param xml
	 * @return
	 */
	public Document getElementoDom(String xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setEncoding("iso-8859-1");
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);

		} catch (ParserConfigurationException e) {
			Log.e("Error al parsear: ", e.getMessage());
			return null;
		} catch (SAXException e) {
			Log.e("Error SAX: ", e.getMessage());
			return null;
		} catch (IOException e) {
			Log.e("Error IO: ", e.getMessage());
			return null;
		}
		
		// Devolver el DOM.
		return doc;
	}

	
	/**
	 * Devuelve el valor dado un tag.
	 * @param item
	 * @param tag
	 * @return
	 */
	public String getValor(String tag) {
		NodeList nodeList = this.elemento.getElementsByTagName(tag);
		return this.getValorElemento(nodeList.item(0));
	}
	
	public String getValor(Element elemento, String tag) {
		NodeList nodeList = elemento.getElementsByTagName(tag);
		return this.getValorElemento(nodeList.item(0));
	}

	
	/**
	 * Devuelve el valor del elemento.
	 * @param elem
	 * @return
	 */
	public final String getValorElemento(Node elem) {
		Node child;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (child = elem.getFirstChild(); child != null; child = child
						.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}

	
	/**
	 * Devuelve el nodeList.
	 * @return nodeList
	 */
	public NodeList getNodeList() {
		return nodeList;
	}

	
	/**
	 * Establece el nodeList.
	 * @param nodeList
	 */
	public void setNodeList(NodeList nodeList) {
		this.nodeList = nodeList;
	}
	
}
