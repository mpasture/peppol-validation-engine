/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.peppol.validation.artefact;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.peppol.validation.artefact.EStandardValidationArtefact;
import com.helger.schematron.pure.SchematronResourcePure;

/**
 * Test class for class {@link EStandardValidationArtefact}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class EStandardValidationArtefactTest
{
  @Test
  public void testBasic ()
  {
    for (final EStandardValidationArtefact e : EStandardValidationArtefact.values ())
    {
      assertNotNull (e.getSchematronResource ());
      assertTrue (e.getSchematronResource ().exists ());
      assertNotNull (e.getTransactionKey ());
      assertNotNull (e.getBIS ());
      assertNotNull (e.getTransaction ());
      assertNotNull (e.getUBLDocumentType ());
      assertFalse (e.isCountrySpecific ());
      assertNull (e.getCountryLocale ());
      assertNull (e.getCountryCode ());
      assertFalse (e.isSectorSpecific ());
    }
  }

  @Test
  public void testValidSchematrons ()
  {
    for (final EStandardValidationArtefact e : EStandardValidationArtefact.values ())
    {
      // Check that the passed Schematron is valid
      assertTrue (new SchematronResourcePure (e.getSchematronResource ()).isValidSchematron ());
    }
  }
}
