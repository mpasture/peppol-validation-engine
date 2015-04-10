package com.helger.peppol.validation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import com.helger.commons.error.IResourceError;
import com.helger.commons.error.IResourceErrorGroup;
import com.helger.commons.io.resource.FileSystemResource;
import com.helger.commons.xml.serialize.DOMReader;
import com.helger.peppol.validation.domain.ExtendedTransactionKey;
import com.helger.peppol.validation.domain.TransactionKey;
import com.helger.peppol.validation.test.TestFile;

public class TradeShiftPeppolTest {

	private static String TEST_PATH = "C:\\Users\\Christophe\\Documents\\ubl - peppol\\tests";
	
	private List <TestFile> invoiceTestFiles = new ArrayList <TestFile> ();
	private List <TestFile> orderTestFiles = new ArrayList <TestFile> ();
	private List <TestFile> despatchAdviceTestFiles = new ArrayList <TestFile> ();
	private List <TestFile> mlrTestFiles = new ArrayList <TestFile> ();
	
	
	@Before
	public void setup() {
		File root = new File(TEST_PATH);
				
		if (root.isDirectory()) {
			for (File subDir : root.listFiles()) {
				if (subDir.isDirectory()) {
				
					ExtendedTransactionKey transactionKey = null;
					
					if ("Invoices".equals(subDir.getName())) {
						transactionKey = new ExtendedTransactionKey(TransactionKey.INVOICE_04_T10);
						for (File file : subDir.listFiles()) {
							
							invoiceTestFiles.add(new TestFile (new FileSystemResource(file.getAbsolutePath()),transactionKey,(Set <String>) null));
						}
					}
					
					if ("Orders".equals(subDir.getName())) {
						transactionKey = new ExtendedTransactionKey(TransactionKey.ORDER_03_T01);
						for (File file : subDir.listFiles()) {
							
							orderTestFiles.add(new TestFile (new FileSystemResource(file.getAbsolutePath()),transactionKey,(Set <String>) null));
						}
					}
					
					if ("DespatchAdvices".equals(subDir.getName())) {
						transactionKey = new ExtendedTransactionKey(TransactionKey.DESPATCH_ADVICE_30_T16);
						for (File file : subDir.listFiles()) {
							
							despatchAdviceTestFiles.add(new TestFile (new FileSystemResource(file.getAbsolutePath()),transactionKey,(Set <String>) null));
						}
					}
					
					if ("MLR".equals(subDir.getName())) {
						transactionKey = new ExtendedTransactionKey(TransactionKey.MLR_36_T71);
						for (File file : subDir.listFiles()) {
							
							mlrTestFiles.add(new TestFile (new FileSystemResource(file.getAbsolutePath()),transactionKey,(Set <String>) null));
						}
					}
					
					
				}
			}
		}
	}
	
	
	
	@Test
	public void tradeShiftToPeppolInvoiceMappingReport()  throws Exception {
		validate(invoiceTestFiles);
		 
	}
	
	@Test
	public void tradeShiftToPeppolOrdersMappingReport()  throws Exception {
		validate(orderTestFiles);
		 
	}
	
	@Test
	public void tradeShiftToPeppolMLRMappingReport()  throws Exception {
		validate(mlrTestFiles);
		 
	}
	
	@Test
	public void tradeShiftToPeppolDespatchAdviceMappingReport()  throws Exception {
		validate(despatchAdviceTestFiles);
		 
	}
	
	
	private void validate(List <TestFile> testFiles) throws Exception {
		 for (final TestFile aTestFile : testFiles)
		    {
		      assertTrue (aTestFile.getResource ().exists ());

		      // Read as generic XML
		      final Document aDoc = DOMReader.readXMLDOM (aTestFile.getResource ());
		      assertNotNull (aTestFile.getResource ().getPath (), aDoc);

		      // Build validator
		      final PeppolValidator aValidator = new PeppolValidator (new PeppolValidationConfiguration (aTestFile.getExtendedTransactionKey ()));

		      // Read as desired type
		      final IResourceErrorGroup aXSDErrors = aValidator.applyXSDValidation (aTestFile.getResource ());
		      Iterator<IResourceError> it = aXSDErrors.iterator();
		      while (it.hasNext())  {
		    	  System.out.println(it.next().getAsString(Locale.ENGLISH));
		      } 
		      
		      if (aXSDErrors.isEmpty()) {
		    	// Read as desired type
			      final IResourceErrorGroup aSCHErrors = aValidator.applySchematronValidation (aTestFile.getResource ());
			      it = aSCHErrors.iterator();
			      while (it.hasNext())  {
			    	  IResourceError error = it.next();		    	  
			    	  System.out.println(error.getAsString(Locale.ENGLISH));
			      }  
		      }
		      
		    }
	}
	
	
	
	
}
