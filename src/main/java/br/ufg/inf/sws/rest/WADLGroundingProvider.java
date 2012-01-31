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
package br.ufg.inf.sws.rest;

import java.net.URI;

import org.mindswap.owl.OWLModel;
import org.mindswap.owl.OWLObjectConverterProvider;
import org.mindswap.owls.grounding.MessageMap;

import br.ufg.inf.sws.rest.impl.TransformationFileMap;

/**
 * Factory like encapsulation of methods that need to be implemented in order
 * to provide a RESTful grounding implementation.
 * <p>
 * The methods defined here directly correspond and are used by the methods
 * provided by {@link WADLGroundingFactory}.
 *
 * @author Otávio Calaça Xavier <otaviocx@gmail.com>
 * 
 */
public interface WADLGroundingProvider extends OWLObjectConverterProvider
{
	/**
	 * The default implementation of this interface. 
	 */
	public static final String DEFAULT_REST_GROUNDING_PROVIDER = "br.ufg.inf.sws.rest.impl.WADLGroundingProviderImpl";

	/**
	 * Method that creates a WADLGrounding instance from a given URI and the OWLModel.
	 * @param uri
	 * 			The URI for a instance of the ontological class WadlGrounding.
	 * @param model
	 * 			The model that contains the ontology that have the class of the given URI.
	 * @return A new instance of WADLGrounding that represents the given instance of ontological class WadlGrounding.
	 */
	public WADLGrounding createWADLGrounding(URI uri, OWLModel model);

	/**
	 * Method that creates a WADLAtomicGrounding instance from a given URI and the OWLModel.
	 * @param uri
	 * 			The URI for a instance of the ontological class WadlAtomicGrounding.
	 * @param model
	 * 			The model that contains the ontology that has the class of the given URI.
	 * @return A new instance of WADLAtomicGrounding that represents the given instance of ontological class WadlAtomicGrounding.
	 */
	public WADLAtomicGrounding createWADLAtomicGrounding(URI uri, OWLModel model);

	/**
	 * Method that creates a MessageMap instance from a given URI of a WadlRequestParamMap instance and the OWLModel.
	 * @param uri
	 * 			The URI for a instance of the ontological class WadlRequestParamMap.
	 * @param model
	 * 			The model that contains the ontology that have the class of the given URI.
	 * @return A new instance of MessageMap that represents the given instance of ontological class WadlRequestParamMap.
	 */
	public MessageMap<TransformationFileMap> createWADLRequestParamMap(final URI uri, final OWLModel model);

	/**
	 * Method that creates a MessageMap instance from a given URI of a WadlResponseParamMap instance and the OWLModel.
	 * @param uri
	 * 			The URI for a instance of the ontological class WadlResponseParamMap.
	 * @param model
	 * 			The model that contains the ontology that have the class of the given URI.
	 * @return A new instance of MessageMap that represents the given instance of ontological class WadlResponseParamMap.
	 */
	public MessageMap<TransformationFileMap> createWADLResponseParamMap(final URI uri, final OWLModel model);

	/**
	 * Method that creates a WADLResourceMethodRef instance from a given URI and the OWLModel.
	 * @param uri
	 * 			The URI for a instance of the ontological class WadlResourceMethodRef.
	 * @param model
	 * 			The model that contains the ontology that have the class of the given URI.
	 * @return A new instance of WADLResourceMethodRef that represents the given instance of ontological class WadlResourceMethodRef.
	 */
	public WADLResourceMethodRef createWADLResourceMethodRef(final URI uri, final OWLModel model);
}