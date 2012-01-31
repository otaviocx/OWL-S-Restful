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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jvnet.ws.wadl.Method;
import org.jvnet.ws.wadl.Option;
import org.jvnet.ws.wadl.Param;
import org.jvnet.ws.wadl.ParamStyle;
import org.jvnet.ws.wadl.Representation;
import org.jvnet.ws.wadl.Resource;
import org.jvnet.ws.wadl.Response;
import org.jvnet.ws.wadl.util.UriBuilder;
import org.jvnet.ws.wadl2java.ast.PathSegment;
import org.jvnet.ws.wadl2java.ast.ResourceNode;
import org.mindswap.utils.URIUtils;

public class WADLResource {
	private WADLApplication app;
	
	private ResourceNode resourceNode;
	private Resource resource;
	private Method method;
	
	private List<Param> params;
	private List<WADLParameter> wadlParameters;
	private List<WADLParameter> wadlPossibleOutputs;
	private WADLParameter wadlRepresentation;
	private List<Response> responses;
	
	private UriBuilder _uriBuilder;
	private WADLRequest wadlRequest = null;
	
	public WADLResource(ResourceNode rn, Resource r, WADLApplication app) {
		this.app = app;
		this.resourceNode = rn;
		this.resource = r;
		
		_uriBuilder = new UriBuilder();
		
		params = new ArrayList<Param>();
		this.createURI(this.resourceNode);
	}
	
	private Boolean responseContainsStatus(Response res, Number status) {
		for(Long s : res.getStatus()) {
			if(s.equals(status.longValue())) {
				return true;
			}
		}
		return false;
	}
	
	private void createURI(ResourceNode rn) {
		if(rn.getParentResource() != null) {
			createURI(rn.getParentResource());
		}
		PathSegment segment = rn.getPathSegment();
		_uriBuilder.addPathSegment(segment.getTemplate());
		
		params.addAll(segment.getTemplateParameters());
		params.addAll(segment.getMatrixParameters());
		params.addAll(rn.getQueryParams());
		params.addAll(rn.getHeaderParams());
	}
	
	public void setWadlRequest(WADLRequest request) {
		this.wadlRequest = request;
	}

	public Method getMethodFromList(String method, List<Object> objList) {
		for(Object m : objList) {
			if(m instanceof Method) {
				Method meth = (Method) m;
				if(meth.getHref() != null) {
					if(URIUtils.getLocalName(meth.getHref()).equals(URIUtils.getLocalName(method))) {
						return this.app.getMethod(meth.getHref());
					}
				} else if(meth.getId().equals(method) || meth.getId().equals(URIUtils.getLocalName(method))) {
					return (Method) m;
				}
			}
		}
		return null;
	}
	
	public void setMethod(String method) {
		this.setMethod(this.getMethodFromList(method, this.resource.getMethodOrResource()));
	}
	public void setMethod(Method method) {
		this.method = method;
		if(this.method != null) {
			List<Param> pList = this.method.getRequest().getParam();
			for(Param p : pList) {
				if(p.getHref() != null) {
					this.params.add(this.app.getParam(p.getHref()));
				} else {
					this.params.add(p);
				}
			}
			for(Representation r : this.method.getRequest().getRepresentation()) {
				WADLParameter wadlParam = new WADLParameter(r.getId(), r.getElement(), "representation");
				wadlParam.setMediaType(r.getMediaType());
			}
			this.responses = this.method.getResponse();
		}
	}
	
	public List<WADLParameter> getInputs() {
		if(this.wadlParameters == null) {
			this.wadlParameters = new ArrayList<WADLParameter>();
			for(Param p : this.params) {
				
				String id = (p.getId() != null) ? p.getId() : p.getName();
				this.wadlParameters.add(new WADLParameter("#"+id, p.getName(), p.getType(), p.getStyle().value(), p.getDefault()));
			}
		}
		return this.wadlParameters;		
	}
	public WADLParameter getRepresentation() {
		return this.wadlRepresentation;
	}

	public WADLParameter getOutput(String name) {
		for(WADLParameter out : this.wadlPossibleOutputs) {
			if(out.getName().equalsIgnoreCase(name) || out.getName().equalsIgnoreCase(URIUtils.getLocalName(name))) {
				return out;				
			}
		}
		return null;
	}
	public void invoke() {
		if(this.method != null) {
			HashMap<String, Object> queryParams = new HashMap<String, Object>();
			HashMap<String, Object> headerParams = new HashMap<String, Object>();
			HashMap<String, Object> templateAndMatrixParams = new HashMap<String, Object>();
			List<WADLParameter> representationInputs = new ArrayList<WADLParameter>();
			
			for(WADLParameter p : this.wadlParameters) {
				String name = p.getName();
				if(p.getStyle().equals(ParamStyle.HEADER.value())) {
					headerParams.put(name, p.getValue());					
				} else if(p.getStyle().equals(ParamStyle.QUERY.value())) {
					queryParams.put(name, p.getValue());					
				} else if(p.getStyle().equals(ParamStyle.MATRIX.value()) || p.getStyle().equals(ParamStyle.TEMPLATE.value())) {
					templateAndMatrixParams.put(name, p.getValue());					
				} else if(p.getStyle().equals("representation")) {
					representationInputs.add(p);					
				}
			}
			
			String uri = this._uriBuilder.buildUri(templateAndMatrixParams, queryParams);
			String mediaType = this.getMediaType();
			String method = this.method.getName();
			WADLParameter inputParam = null;
			
			if(this.wadlRequest == null) {
				this.wadlRequest = new WADLRequestImpl();
			}
			
			if(method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT")) {
				for(WADLParameter rep : representationInputs) {
					if(rep.getValue() != null) {
						inputParam = rep;
						break;
					}
				}
				
			}
			this.wadlRequest.request(uri, method, inputParam, headerParams, mediaType);
			this.doResponse();
		}
	}
	
	public String getMediaType() {
		String mediaType = null;
		for(int i = 0; i < params.size(); i++) {
			Param p = this.params.get(i);
			WADLParameter wadlp = this.wadlParameters.get(i);
			List<Option> options = p.getOption();
			if(options != null && options.size() > 0) {
				for(Option o : options) {
					if(o.getValue().equals(wadlp.getValue())) {
						if(o.getMediaType() != null) {
							mediaType = o.getMediaType();
						}
					}		
				}
			}
		}
		return mediaType;
	}
	
	public void doResponse() {
        this.wadlPossibleOutputs = new ArrayList<WADLParameter>();
        for(Response res : this.responses) {
    		for(Representation rep : res.getRepresentation()) {
    			if(rep.getId() == null && rep.getHref() != null) {
    				rep = this.app.getRepresentation(rep.getHref());
    			}
            	WADLParameter wadlParam = new WADLParameter(rep.getId(), rep.getElement(), "representation");
				wadlParam.setMediaType(this.wadlRequest.getMediaType());
				if(this.responseContainsStatus(res, this.wadlRequest.getStatus())) {
					if(res.getRepresentation().size() == 1 || rep.getMediaType().equalsIgnoreCase(this.wadlRequest.getMediaType())) {
    					wadlParam.setValue(this.wadlRequest.getRepresentation());
    					this.wadlRepresentation = wadlParam;
					}
            	}
    			this.wadlPossibleOutputs.add(wadlParam);
    		}
        }
	}
}
