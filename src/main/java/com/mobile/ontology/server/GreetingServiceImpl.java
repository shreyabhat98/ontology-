package com.mobile.ontology.server;

import com.mobile.ontology.client.GreetingService;
import com.mobile.ontology.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
//@SuppressWarnings("serial")
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
    GreetingService {

	
	private String testOntology() throws OWLOntologyCreationException {
		
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        // Let's load an ontology from the web
      //  OWLDataFactory dataFactory=manager.getOWLDataFactory();
        IRI iri = IRI
                .create("file:/Users/shreya/Downloads/phone.owl");
        IRI url = IRI.create("http://www.semanticweb.org/abhinaya/ontologies/2017/5/phone#OWLClass_59603506_c711_4d4e_a933_14172f5dbaa5");
        OWLOntology pizzaOntology = manager
                .loadOntologyFromOntologyDocument(iri);
      /*  OWLClass icecream=dataFactory.getOWLClass(iri);
        ReasonerFactory factory = new ReasonerFactory();
        Configuration configuration=new Configuration();
        configuration.throwInconsistentOntologyException=false;*/
        
        // The factory can now be used to obtain an instance of HermiT as an OWLReasoner. 
       // OWLReasoner reasoner=factory.createReasoner(ontology, configuration);
        System.out.println("Loaded ontology: " + pizzaOntology);
     //   System.out.println(" class  "+pizzaOntology.getEntitiesInSignature(arg0, arg1));
        //to answer queries
     /*   OWLReasoner reasoner = createReasoner(pizzaOntology);
        // short form for iris 
        ShortFormProvider shortFormProvider = new SimpleShortFormProvider(); */
        return pizzaOntology.toString();
	}

	
  public String greetServer(String input) throws IllegalArgumentException {
    // Verify that the input is valid.
    if (!FieldVerifier.isValidName(input)) {
      // If the input is not valid, throw an IllegalArgumentException back to
      // the client.
      throw new IllegalArgumentException(
          "Name must be at least 4 characters long");
    }

    String serverInfo = getServletContext().getServerInfo();
    String userAgent = getThreadLocalRequest().getHeader("User-Agent");

    // Escape data from the client to avoid cross-site script vulnerabilities.
    input = escapeHtml(input);
    userAgent = escapeHtml(userAgent);
    String str = "test";
    try {
		str = testOntology();
	} catch (OWLOntologyCreationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    return  str;
    //+ input + "!<br><br>I am running " + serverInfo
    //    + ".<br><br>It looks like you are using:<br>" + userAgent ;
  }

  /**
   * Escape an html string. Escaping data received from the client helps to
   * prevent cross-site script vulnerabilities.
   *
   * @param html the html string to escape
   * @return the escaped string
   */
  private String escapeHtml(String html) {
    if (html == null) {
      return null;
    }
    return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
        ">", "&gt;");
  }
}


