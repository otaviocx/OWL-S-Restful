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

import org.mindswap.common.ServiceFinder;
import org.mindswap.owl.OWLModel;
import org.mindswap.owls.grounding.MessageMap;

import br.ufg.inf.sws.rest.impl.TransformationFileMap;

/**
 * A factory that use the methods of a provider for create references for the ontological classes.
 * @see {@link WADLGroundingProvider} 
 *
 * @author Otávio Calaça Xavier <otaviocx@gmail.com>
 *
 */
public class WADLGroundingFactory {
	/**
	 * The provider.
	 * @see {@link WADLGroundingProvider} 
	 */
	private static final WADLGroundingProvider provider = ServiceFinder.instance().loadImplementation(
			WADLGroundingProvider.class, WADLGroundingProvider.DEFAULT_REST_GROUNDING_PROVIDER);

	/**
	 * @see {@link WADLGroundingProvider#createWADLGrounding(URI, OWLModel)}
	 */
	public static final WADLGrounding createWADLGrounding(final URI uri, final OWLModel model) {
		return provider.createWADLGrounding(uri, model);
	}

	/**
	 * @see {@link WADLGroundingProvider#createWADLAtomicGrounding(URI, OWLModel)}
	 */
	public static final WADLAtomicGrounding createWADLAtomicGrounding(final URI uri, final OWLModel model) {
		return provider.createWADLAtomicGrounding(uri, model);
	}

	/**
	 * @see {@link WADLGroundingProvider#createWADLResourceMethodRef(URI, OWLModel)}
	 */
	public static final WADLResourceMethodRef createWADLResourceMethodRef(final URI uri, final OWLModel model) {
		return provider.createWADLResourceMethodRef(uri, model);
	}

	/**
	 * @see {@link WADLGroundingProvider#createWADLRequestParamMap(URI, OWLModel)}
	 */
	public static final MessageMap<TransformationFileMap> createWADLRequestParamMap(final URI uri, final OWLModel model) {
		return provider.createWADLRequestParamMap(uri, model);
	}

	/**
	 * @see {@link WADLGroundingProvider#createWADLResponseParamMap(URI, OWLModel)}
	 */
	public static final MessageMap<TransformationFileMap> createWADLResponseParamMap(final URI uri, final OWLModel model) {
		return provider.createWADLResponseParamMap(uri, model);
	}
}
