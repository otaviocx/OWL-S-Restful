/*
 * Created 2011-07-25
 *
 * (c) 2011 Otávio Calaça Xavier
 *
 * The MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package br.ufg.inf.rest;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.jvnet.ws.wadl.Application;
import org.jvnet.ws.wadl.Method;
import org.jvnet.ws.wadl.Param;
import org.jvnet.ws.wadl.Representation;
import org.jvnet.ws.wadl.Resource;
import org.jvnet.ws.wadl.Resources;
import org.jvnet.ws.wadl2java.Wadl2Java;
import org.jvnet.ws.wadl2java.ast.ResourceNode;
import org.mindswap.utils.URIUtils;

/**
 * Class that loads a WADL document and provides access to the informations of the application, resources, methods, representations, params...
 * 
 * @author Otávio Calaça Xavier <otaviocx@gmail.com>
 *
 */
public class WADLApplication {
	private URI uri;
	private Application app;
	private List<ResourceNode> resources;
	
	private List<Method> methods;
	private List<Param> params;
	private List<Representation> representations;
	
	private HashMap<String, WADLResource> wadlResources;
	
	/**
	 * Private Constructor. This class uses the factory method design pattern for provides itself instances.
	 * 
	 * @param wadlURI
	 * 			The URI for the WADL document.
	 * @throws JAXBException
	 * @throws IOException
	 */
	private WADLApplication(URI wadlURI) throws JAXBException, IOException {
		this.uri = wadlURI;
		Wadl2Java w2j = new Wadl2Java(new File("./"), "", true);
		this.app = w2j.processDescription(this.uri);
		this.resources = w2j.buildAst(app, this.uri);
		this.wadlResources = new HashMap<String, WADLResource>();
		
		this.methods = new ArrayList<Method>();
		this.representations = new ArrayList<Representation>();
		this.params = new ArrayList<Param>();
		for(Object obj : this.app.getResourceTypeOrMethodOrRepresentation()) {
			if(obj instanceof Method) {
				this.methods.add((Method) obj);
			} else if (obj instanceof Representation) {
				this.representations.add((Representation) obj);
			} else if (obj instanceof Param) {
				this.params.add((Param) obj);
			}
		}
	}
	
	/**
	 * Method that returns a instance of this class.
	 * @param wadlURI
	 * 			The URI for the WADL document.
	 * @return A new instance of this class.
	 * @throws JAXBException
	 * @throws IOException
	 */
	public static WADLApplication createApplication(URI wadlURI) throws JAXBException, IOException {
		return new WADLApplication(wadlURI);		
	}
	
	/**
	 * Returns a WADL Method from the link (relative URI or id) of the method.
	 * A WADL Method represents a HTTP Method for a specific resource. 
	 * @param href
	 * 			The link (relative URI or id) for the method.
	 * @return A WADL Method. @see {@link Method}
	 */
	public Method getMethod(String href) {
		for(Method method : this.methods) {
			if(method.getId().equalsIgnoreCase(URIUtils.getLocalName(href))) {
				return method;
			}
		}
		return null;
	}
	
	/**
	 * Returns a WADL Representation from the link (relative URI or id) of the representation.
	 * A WADL Representation represents a specific REST representation for a resource. 
	 * @param href
	 * 			The link (relative URI or id) for the representation.
	 * @return A WADL Representation. @see {@link Representation}
	 */
	public Representation getRepresentation(String href) {
		for(Representation rep : this.representations) {
			if(rep.getId().equalsIgnoreCase(URIUtils.getLocalName(href))) {
				return rep;
			}
		}
		return null;
	}
	
	/**
	 * Returns a WADL Param from the link (relative URI or id) of the param.
	 * Params can be defined as children of an application and only referenced in the real locations where this is used.  
	 * @param href
	 * 			The link (relative URI or id) for the param.
	 * @return A WADL Representation. @see {@link Representation}
	 */
	public Param getParam(String href) {
		for(Param p : this.params) {
			if(p.getId().equalsIgnoreCase(URIUtils.getLocalName(href))) {
				return p;
			}
		}
		return null;
	}
	
	/**
	 * Returns a WADL Resource from its name.
	 * @param name
	 * 			The name of the resource.
	 * @return A WADL Resource. @see {@link WADLResource}
	 */
	public WADLResource getResource(String name) {
		WADLResource wadlResource = null;
		if(this.wadlResources.containsKey(name)) {
			wadlResource = this.wadlResources.get(name);
		} else {
			List<Resources> resourcesList = this.app.getResources();
			for(int i = 0; i < resourcesList.size(); i++) {
				Resources rs = resourcesList.get(i);
				ResourceNode resourceNode = resources.get(i);
				
				for(int j = 0; j < rs.getResource().size(); j++) {
					Resource r = rs.getResource().get(j);							
					if(r.getId().equals(name) || r.getId().equals(URIUtils.getLocalName(name))) {
						ResourceNode rn = resourceNode.getChildResources().get(j);
						wadlResource = new WADLResource(rn, r, this);
						this.wadlResources.put(name, wadlResource);
					}
				}
			}
		}
		return wadlResource;
	}
	
	/**
	 * Returns a WADL Resource from its name and set the method for this resource.
	 * @param name
	 * 			The name of the resource.
	 * @param method
	 * 			The method for the needed resource.
	 * @return A WADL Resource with the method information. @see {@link WADLResource}
	 */
	public WADLResource getResource(String name, String method) {
		WADLResource wadlResource = this.getResource(name);
		if(wadlResource != null) {
			wadlResource.setMethod(method);
		}
		return wadlResource;
	}
}
