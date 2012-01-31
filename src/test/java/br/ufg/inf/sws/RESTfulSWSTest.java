package br.ufg.inf.sws;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.mindswap.exceptions.ExecutionException;
import org.mindswap.owl.OWLFactory;
import org.mindswap.owl.OWLKnowledgeBase;
import org.mindswap.owl.OWLValue;
import org.mindswap.owls.OWLSFactory;
import org.mindswap.owls.process.execution.DefaultProcessMonitor;
import org.mindswap.owls.process.execution.ProcessExecutionEngine;
import org.mindswap.owls.process.variable.Input;
import org.mindswap.owls.process.variable.Output;
import org.mindswap.owls.service.Service;
import org.mindswap.owls.process.Process;
import org.mindswap.query.ValueMap;

public class RESTfulSWSTest {

	@Test
	public void request_resource() throws URISyntaxException, IOException, ExecutionException {
		ProcessExecutionEngine exec = OWLSFactory.createExecutionEngine();
		exec.addMonitor(new DefaultProcessMonitor());
		final OWLKnowledgeBase kb = OWLFactory.createKB();
		
		// Carregando ontologias que descrevem o serviço
		URI uri = new URI("http://solutio.in/owlsrestful/FBUserResource.owl");
		Service service = kb.readService(uri);
		Process process = service.getProcess();
		
		// Definindo entradas
		ValueMap<Input, OWLValue> inputs = new ValueMap<Input, OWLValue>();
		inputs.setValue(process.getInput("FBAccessToken"), kb.createDataValue("2227470867|2.AQB9wYj717TdxSod.3600.1316448000.0-1820391700|oPOwu5STVEcJlwpYB_kemwx0Bf4"));
		inputs.setValue(process.getInput("FBUserId"), kb.createDataValue("me"));
		
		// Criando conjunto de saídas e executando processo
		ValueMap<Output, OWLValue> outputs = new ValueMap<Output, OWLValue>();
		outputs = exec.execute(process, inputs, kb);
		
		// Exibindo a saída em String
		final OWLValue outputValue = outputs.getValue(process.getOutput());
		System.out.println(outputValue.toString());		
	}
}
