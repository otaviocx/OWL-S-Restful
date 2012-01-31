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

import impl.owl.GenericOWLConverter;

import java.net.URI;

import org.mindswap.owl.OWLModel;
import org.mindswap.owl.OWLObjectConverter;
import org.mindswap.owl.OWLObjectConverterRegistry;
import org.mindswap.owls.grounding.AtomicGrounding;
import org.mindswap.owls.grounding.Grounding;
import org.mindswap.owls.grounding.MessageMap;

import br.ufg.inf.sws.rest.WADLAtomicGrounding;
import br.ufg.inf.sws.rest.WADLGrounding;
import br.ufg.inf.sws.rest.WADLGroundingProvider;
import br.ufg.inf.sws.rest.WADLResourceMethodRef;
import br.ufg.inf.sws.rest.vocabulary.OWLSRestful;

/**
 * Default implementation of {@link WADLGroundingProvider}.
 * 
 * @author Otávio Calaça Xavier <otaviocx@gmail.com>
 *
 */
public class WADLGroundingProviderImpl implements WADLGroundingProvider {

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLGroundingProvider#createWADLGrounding(java.net.URI, org.mindswap.owl.OWLModel)
	 */
	public WADLGrounding createWADLGrounding(URI uri, OWLModel model) {
		return new WADLGroundingImpl(model.createInstance(OWLSRestful.WadlGrounding, uri));
	}

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLGroundingProvider#createWADLAtomicGrounding(java.net.URI, org.mindswap.owl.OWLModel)
	 */
	public WADLAtomicGrounding createWADLAtomicGrounding(URI uri, OWLModel model) {
		return new WADLAtomicGroundingImpl(model.createInstance(OWLSRestful.WadlAtomicProcessGrounding, uri));
	}

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLGroundingProvider#createWADLRequestParamMap(java.net.URI, org.mindswap.owl.OWLModel)
	 */
	public MessageMap<TransformationFileMap> createWADLRequestParamMap(URI uri, OWLModel model) {
		return new WADLMessageParamMap(model.createInstance(OWLSRestful.WadlRequestParamMap, uri));
	}

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLGroundingProvider#createWADLResponseParamMap(java.net.URI, org.mindswap.owl.OWLModel)
	 */
	public MessageMap<TransformationFileMap> createWADLResponseParamMap(URI uri, OWLModel model) {
		return new WADLMessageParamMap(model.createInstance(OWLSRestful.WadlResponseParamMap, uri));
	}

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLGroundingProvider#createWADLResourceMethodRef(java.net.URI, org.mindswap.owl.OWLModel)
	 */
	public WADLResourceMethodRef createWADLResourceMethodRef(URI uri, OWLModel model) {
		return new WADLResourceMethodRefImpl(model.createInstance(OWLSRestful.WadlResourceMethodRef, uri));
	}
	
	/* (non-Javadoc)
	 * @see org.mindswap.owl.OWLObjectConverterProvider#registerConverters(org.mindswap.owl.OWLObjectConverterRegistry)
	 */
	public void registerConverters(final OWLObjectConverterRegistry registry)
	{
		final OWLObjectConverter<WADLGrounding> gc = new GenericOWLConverter<WADLGrounding>(
				WADLGroundingImpl.class, OWLSRestful.WadlGrounding);

		final OWLObjectConverter<WADLAtomicGrounding> agc = new GenericOWLConverter<WADLAtomicGrounding>(
				WADLAtomicGroundingImpl.class, OWLSRestful.WadlAtomicProcessGrounding);

		final OWLObjectConverter<WADLMessageParamMap> immc =
			new GenericOWLConverter<WADLMessageParamMap>(WADLMessageParamMap.class, OWLSRestful.WadlRequestParamMap);
		final OWLObjectConverter<WADLMessageParamMap> ommc =
			new GenericOWLConverter<WADLMessageParamMap>(WADLMessageParamMap.class, OWLSRestful.WadlResponseParamMap);

		registry.registerConverter(WADLResourceMethodRef.class,
			new GenericOWLConverter<WADLResourceMethodRef>(WADLResourceMethodRefImpl.class, OWLSRestful.WadlResourceMethodRef));
		
		registry.registerConverter(TransformationFileMap.class,
				new GenericOWLConverter<TransformationFileMap>(TransformationFileMap.class, OWLSRestful.TransformationFileMap));

		registry.registerConverter(WADLGrounding.class, gc);
		registry.registerConverter(WADLAtomicGrounding.class, agc);

		registry.extendByConverter(Grounding.class, gc);
		registry.extendByConverter(AtomicGrounding.class, agc);

		registry.registerConverter(WADLMessageParamMap.class, immc);
		registry.extendByConverter(WADLMessageParamMap.class, ommc);
		
		registry.extendByConverter(MessageMap.class, immc);
		registry.extendByConverter(MessageMap.class, ommc);
		
	}
}
