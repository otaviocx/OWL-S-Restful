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
package br.ufg.inf.sws.rest.impl;

import impl.owls.grounding.MessageMapAtomicGroundingImpl;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.Future;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.mindswap.common.CompletedFuture;
import org.mindswap.exceptions.ExecutionException;
import org.mindswap.exceptions.ServiceNotAvailableException;
import org.mindswap.owl.OWLDataValue;
import org.mindswap.owl.OWLFactory;
import org.mindswap.owl.OWLIndividual;
import org.mindswap.owl.OWLIndividualList;
import org.mindswap.owl.OWLKnowledgeBase;
import org.mindswap.owl.OWLObjectProperty;
import org.mindswap.owl.OWLValue;
import org.mindswap.owls.grounding.MessageMap;
import org.mindswap.owls.process.variable.Input;
import org.mindswap.owls.process.variable.Output;
import org.mindswap.owls.process.variable.Parameter;
import org.mindswap.query.ValueMap;
import org.mindswap.utils.Utils;
import org.mindswap.utils.XSLTEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.ufg.inf.rest.WADLApplication;
import br.ufg.inf.rest.WADLParameter;
import br.ufg.inf.rest.WADLRequest;
import br.ufg.inf.rest.WADLRequestImpl;
import br.ufg.inf.rest.WADLResource;
import br.ufg.inf.sws.rest.WADLAtomicGrounding;
import br.ufg.inf.sws.rest.WADLGrounding;
import br.ufg.inf.sws.rest.WADLGroundingFactory;
import br.ufg.inf.sws.rest.WADLResourceMethodRef;
import br.ufg.inf.sws.rest.vocabulary.OWLSRestful;

/**
 * Default implementation of {@link WADLAtomicGrounding}.
 * 
 * @author Otávio Calaça Xavier <otaviocx@gmail.com>
 *
 */
public class WADLAtomicGroundingImpl extends MessageMapAtomicGroundingImpl<TransformationFileMap> implements WADLAtomicGrounding {
	
	private static final Logger logger = LoggerFactory.getLogger(WADLAtomicGroundingImpl.class);

	/**
	 * The WADLResource instance, that executes the requests for resources. 
	 */
	private WADLResource wadlResource;

	/**
	 * @param ind
	 * 			Entity that represents the corresponding ontological class.
	 */
	public WADLAtomicGroundingImpl(OWLIndividual ind) {
		super(ind);
	}

	/* (non-Javadoc)
	 * @see org.mindswap.owls.grounding.AtomicGrounding#getDescriptionURL()
	 */
	public URL getDescriptionURL() {
		try {
			return getWADL().toURL();
		} catch(final MalformedURLException e) {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.mindswap.owls.grounding.AtomicGrounding#invoke(org.mindswap.query.ValueMap)
	 */
	public Future<ValueMap<Output, OWLValue>> invoke(ValueMap<Input, OWLValue> values) {
		return invoke(values, OWLFactory.createKB());
	}

	/* (non-Javadoc)
	 * @see org.mindswap.owls.grounding.AtomicGrounding#invoke(org.mindswap.query.ValueMap, org.mindswap.owl.OWLKnowledgeBase)
	 */
	public Future<ValueMap<Output, OWLValue>> invoke(ValueMap<Input, OWLValue> inputs, OWLKnowledgeBase env) {
		try
		{
			initWADLResource();
			for (final WADLParameter in : wadlResource.getInputs())
			{
				final MessageMap<TransformationFileMap> mp = getMessageMap(true, in.getId());
				if (mp == null) continue;

				final Input input = mp.getOWLSParameter().castTo(Input.class);
				
				OWLValue inputValue = null;
				try {
					inputValue = getParameterValue(input, inputs);
				} catch (ExecutionException e) {
					if(in.getValue() != null) {
						inputValue = env.createDataValue(in.getValue());
						inputs.setValue(input, inputValue);
					}
				}
				if(inputValue != null) {
					Object inputValueObj;
					TransformationFileMap transf = mp.getTransformation();
					String xslt = null;
					if(transf != null) 
						xslt = transf.getTransformationURI().toString();
					
					if (inputValue.isIndividual())
					{
						final String rdfXML = ((OWLIndividual) inputValue).toRDF(true, true);
						if (xslt != null)
						{
							final String xsltResult = XSLTEngine.transform(rdfXML, xslt, inputs);
							inputValueObj = Utils.getAsNode(xsltResult);
							if (inputValueObj == null) inputValueObj = xsltResult.trim();
						}
						else
						{
							logger.debug("No XSLT transformation for input parameter " + input + " specified." +
								" OWLIndividual bound to this parameter should be transformed rather than using" +
							" its RDF/XML serialization.");
							inputValueObj = rdfXML;
						}
					}
					else // it is a OWLDataValue
					{
						if (xslt != null) throw new ExecutionException("XSLT transformation for input parameter " +
							input + " cannot be applied to OWL data value (only to OWL individual).");
	
						inputValueObj = ((OWLDataValue) inputValue).getValue();
					}
	
					in.setValue(inputValueObj);
				}
			}

			wadlResource.invoke();

			final ValueMap<Output, OWLValue> results = new ValueMap<Output, OWLValue>();
			for (final Output outputParam : getProcess().getOutputs())
			{
				final MessageMap<TransformationFileMap> mp = getMessageMap(outputParam);
				final TransformationFileMap transformation = mp.getTransformation();
				final String wadlMessageParam = mp.getGroundingParameter();
				WADLParameter out = wadlResource.getOutput(wadlMessageParam);
				//WADLParameter out = wadlResource.getRepresentation();
				Object outputValue = out.getValue();
				if(outputValue != null) {
					if (outputParam.getParamType().isDataType()) {
						results.setValue(outputParam, env.createDataValue(outputValue));
					} else {
						String outputString = outputValue.toString();
						if(out.getMediaType().indexOf("json") > -1 || out.getMediaType().indexOf("javascript") > -1) {
							Object obj = null;
							try {
								obj = new JSONObject(outputString);
							} catch (Exception e) {
								obj = new JSONArray(outputString);
							}
							if(obj != null)
								outputString = XML.toString(obj, "json");
						}
						if(transformation != null && transformation.getTransformationMediaType().indexOf("xsl") > -1) {
							WADLRequest req = new WADLRequestImpl();
							String xslt = (String) req.request(transformation.getTransformationURI().toString(), "GET");
							
							outputString = XSLTEngine.transform(outputString, xslt, inputs);
							results.setValue(outputParam, env.parseLiteral(outputString));
						} else {
							results.setValue(outputParam, env.createDataValue(outputString));
						}
					}
				}
			}

			return CompletedFuture.createSuccessCompletionFuture(results);
		}
		catch(final Exception e)
		{
			return CompletedFuture.createExceptionCompletionFuture(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.mindswap.owls.grounding.AtomicGrounding#getGrounding()
	 */
	public WADLGrounding getGrounding() {
		return getGrounding(WADLGrounding.class);
	}

	/* (non-Javadoc)
	 * @see org.mindswap.owls.grounding.AtomicGrounding#getGroundingType()
	 */
	public String getGroundingType() { return WADLAtomicGrounding.GROUNDING_WADL; }

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLAtomicGrounding#setWADL(java.net.URI)
	 */
	public void setWADL(URI wadlLoc) {
		setProperty(OWLSRestful.wadlDocument, wadlLoc);
	}

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLAtomicGrounding#getWADL()
	 */
	public URI getWADL() {
		return getPropertyAsURI(OWLSRestful.wadlDocument);
	}

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLAtomicGrounding#setMethod(java.net.URI)
	 */
	public void setMethod(URI method) {
		WADLResourceMethodRef rmRef = getResourceMethodRef();
		if (rmRef == null)
		{
			//TODO Create method createWADLResourceMethodRef in OWLModel
			rmRef = WADLGroundingFactory.createWADLResourceMethodRef(null, getOntology());
			setResourceMethodRef(rmRef);
		}
		rmRef.setMethod(method);
	}

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLAtomicGrounding#getMethod()
	 */
	public URI getMethod() {
		return getResourceMethodRef().getMethod();
	}

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLAtomicGrounding#setResource(java.net.URI)
	 */
	public void setResource(URI resource) {
		WADLResourceMethodRef rmRef = getResourceMethodRef();
		if (rmRef == null)
		{
			//TODO Create method createWADLResourceMethodRef in OWLModel
			rmRef = WADLGroundingFactory.createWADLResourceMethodRef(null, getOntology());
			setResourceMethodRef(rmRef);
		}
		rmRef.setResource(resource);
	}

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLAtomicGrounding#getResource()
	 */
	public URI getResource() {
		return getResourceMethodRef().getResource();
	}

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLAtomicGrounding#setResourceMethodRef(br.ufg.inf.sws.rest.WADLResourceMethodRef)
	 */
	public void setResourceMethodRef(WADLResourceMethodRef rmRef) {
		setProperty(OWLSRestful.wadlResourceMethod, rmRef);
	}

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLAtomicGrounding#getResourceMethodRef()
	 */
	public WADLResourceMethodRef getResourceMethodRef() {
		return getPropertyAs(OWLSRestful.wadlResourceMethod, WADLResourceMethodRef.class);
	}

	/* (non-Javadoc)
	 * @see impl.owls.grounding.MessageMapAtomicGroundingImpl#createInputMessageMap()
	 */
	protected MessageMap<TransformationFileMap> createInputMessageMap() {
		//TODO Create method createWADLRequestParamMap in OWLModel
		return WADLGroundingFactory.createWADLRequestParamMap(null, getOntology());
	}

	/* (non-Javadoc)
	 * @see impl.owls.grounding.MessageMapAtomicGroundingImpl#createOutputMessageMap()
	 */
	protected MessageMap<TransformationFileMap> createOutputMessageMap() {
		//TODO Create method createWADLResponseParamMap in OWLModel
		return WADLGroundingFactory.createWADLResponseParamMap(null, getOntology());
	}

	/* (non-Javadoc)
	 * @see impl.owls.grounding.MessageMapAtomicGroundingImpl#inputMessageMapProperty()
	 */
	protected OWLObjectProperty inputMessageMapProperty() {
		return OWLSRestful.wadlRequestParam;
	}

	/* (non-Javadoc)
	 * @see impl.owls.grounding.MessageMapAtomicGroundingImpl#outputMessageMapProperty()
	 */
	protected OWLObjectProperty outputMessageMapProperty() {
		return OWLSRestful.wadlResponseParam;
	}

	/* (non-Javadoc)
	 * @see impl.owls.grounding.MessageMapAtomicGroundingImpl#messageMapType()
	 */
	protected Class<? extends MessageMap<TransformationFileMap>> messageMapType() {
		return WADLMessageParamMap.class;
	}
	
	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLAtomicGrounding#getWADLParameter(org.mindswap.owls.process.variable.Parameter)
	 */
	public URI getWADLParameter(final Parameter parameter) {
		URI uri = getWADLParameter(parameter, getInputMappings());
		if (uri == null) uri = getWADLParameter(parameter, getOutputMappings());
		return uri;
	}
	
	/**
	 * Method that search for a parameter in a list and returns its URI.
	 * @param parameter
	 * 			A parameter that which the URI is wanted.
	 * @param list
	 * 			A list of parameters.
	 * @return The URI of the given parameter.
	 */
	private URI getWADLParameter(final Parameter parameter,
			final OWLIndividualList<? extends MessageMap<TransformationFileMap>> list) {
		
		for (final MessageMap<TransformationFileMap> messageMap : list) {
			if (messageMap.getOWLSParameter().equals(parameter))
				return messageMap.getGroundingParameterAsURI();
		}
		return null;
	}

	/**
	 * Method that removes the WADLResourceMethodRef reference of the property wadlResourceMethod
	 */
	public void removeResourceMethodRef() {
		if (hasProperty(OWLSRestful.wadlResourceMethod)) {
			final WADLResourceMethodRef rmRef = getResourceMethodRef();
			rmRef.removeResource();
			rmRef.removeMethod();
			removeProperty(OWLSRestful.wadlResourceMethod, null);
			rmRef.delete();
		}
	}
	
	/**
	 * Method that removes all setted properties of the ontological class.
	 */
	private void removeAll() {
		removeProperty(OWLSRestful.wadlDocument, null);
		removeResourceMethodRef();

		removeProperty(OWLSRestful.wadlRequestParam, null);
		removeProperty(OWLSRestful.wadlResponseParam, null);

		removeMessageMaps(true);
		removeMessageMaps(false);
	}

	/* (non-Javadoc)
	 * @see impl.owl.WrappedIndividual#delete()
	 */
	public void delete() {
		removeAll();
		super.delete();
	}
	
	/**
	 * Method that initialize the WADLResource instance with the informations of resource and method. 
	 * @throws ExecutionException When the resource not found in the WADL description.
	 */
	private void initWADLResource() throws ExecutionException
	{
		if (wadlResource == null)
		{
			final String resource = getResource().toString();
			final String method = getMethod().toString();
			final WADLApplication a;
			try
			{
				a = WADLApplication.createApplication(getWADL());
			}
			catch (final Exception e)
			{
				throw new ServiceNotAvailableException(getProcess(), e);
			}
			wadlResource = a.getResource(resource, method);
			if (wadlResource == null)
				throw new ExecutionException("Resource " + resource + " not found in the WADL description");
		}
	}
}
