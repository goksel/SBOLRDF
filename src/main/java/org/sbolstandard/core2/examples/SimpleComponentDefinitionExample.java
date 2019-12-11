package org.sbolstandard.core2.examples;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import org.sbolstandard.core2.ComponentDefinition;
import org.sbolstandard.core2.SBOLDocument;
import org.sbolstandard.core2.SBOLWriter;
import org.sbolstandard.core2.Sequence;
import org.sbolstandard.core2.SequenceOntology;

/**
 * This example shows how to create simple {@link org.sbolstandard.core2.ComponentDefinition} entities. 
 * A ComponentDefinition entity is created for a promoter part, and its nucleotide sequence is associated using a Sequence entity.
 *
 */
public class SimpleComponentDefinitionExample {
	public static void main( String[] args ) throws Exception
    {
		String prURI="http://partsregistry.org/";
	
		SBOLDocument document = new SBOLDocument();		
		document.setDefaultURIprefix(prURI);
		document.setTypesInURIs(true);
	
		ComponentDefinition promoter = document.createComponentDefinition(
				"BBa_J23119",
				"",
				new HashSet<URI>(Arrays.asList(
						ComponentDefinition.DNA_REGION,
						URI.create("http://identifiers.org/chebi/CHEBI:4705")												
						)));
		promoter.addRole(SequenceOntology.PROMOTER);
		promoter.addRole(URI.create("http://identifiers.org/so/SO:0000613"));				
		promoter.setName("J23119 promoter");
		promoter.setDescription("Constitutive promoter");	
		promoter.addWasDerivedFrom(URI.create("http://partsregistry.org/Part:BBa_J23119"));
	  
		document.setDefaultURIprefix(prURI);	
		Sequence seq=document.createSequence(
				"BBa_J23119",
				"",
				 "ttgacagctagctcagtcctaggtataatgctagc", 
				URI.create("http://www.chem.qmul.ac.uk/iubmb/misc/naseq.html")
				);
		seq.addWasDerivedFrom(URI.create("http://parts.igem.org/Part:BBa_J23119:Design"));
		promoter.addSequence(seq.getIdentity());
			
		SBOLWriter.write(document,(System.out));		
		SBOLWriter.write(document, "RDFExamples/SimpleComponentDefinitionExample.sbol");
    }
}