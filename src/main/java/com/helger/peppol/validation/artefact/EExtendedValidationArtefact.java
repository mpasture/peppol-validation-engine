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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotations.Nonempty;
import com.helger.commons.annotations.ReturnsMutableCopy;
import com.helger.commons.io.IReadableResource;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.peppol.validation.domain.EBII2Transaction;
import com.helger.peppol.validation.domain.EPeppolBIS;
import com.helger.peppol.validation.domain.ExtendedKey;
import com.helger.peppol.validation.domain.ExtendedTransactionKey;
import com.helger.peppol.validation.domain.TransactionKey;
import com.helger.ubl.EUBL21DocumentType;

/**
 * This enumeration contains all the extended country specific OpenPEPPOL
 * Schematron artefacts I'm aware of. They are ordered ascending country, than
 * by BIS number, by transaction and finally by desired execution order.
 *
 * @author Philip Helger
 */
public enum EExtendedValidationArtefact implements IValidationArtefact
{
  INVOICE_AT_NAT ("atnat/ATNAT-UBL-T10.sch", new ExtendedTransactionKey (TransactionKey.INVOICE_04_T10, ExtendedKey.AT)),
  INVOICE_AT_GOV ("atgov/ATGOV-UBL-T10.sch", new ExtendedTransactionKey (TransactionKey.INVOICE_04_T10,
                                                                         ExtendedKey.AT_SECTOR)),

  BILLING_CREDIT_NOTE_AT_NAT ("atnat/ATNAT-UBL-T14.sch", new ExtendedTransactionKey (TransactionKey.BILLING_05_T14,
                                                                                     ExtendedKey.AT)),
  BILLING_CREDIT_NOTE_AT_GOV ("atgov/ATGOV-UBL-T14.sch", new ExtendedTransactionKey (TransactionKey.BILLING_05_T14,
                                                                                     ExtendedKey.AT_SECTOR));

  private final ClassPathResource m_aResource;
  private final ExtendedTransactionKey m_aExtendedTransactionKey;

  private EExtendedValidationArtefact (@Nonnull @Nonempty final String sPath,
                                       @Nonnull final ExtendedTransactionKey aExtendedTransactionKey)
  {
    m_aResource = new ClassPathResource ("/extended/" + sPath);
    m_aExtendedTransactionKey = aExtendedTransactionKey;
  }

  @Nonnull
  public IReadableResource getSchematronResource ()
  {
    return m_aResource;
  }

  @Nonnull
  public ExtendedTransactionKey getExtendedTransactionKey ()
  {
    return m_aExtendedTransactionKey;
  }

  @Nonnull
  public TransactionKey getTransactionKey ()
  {
    return m_aExtendedTransactionKey.getTransactionKey ();
  }

  @Nonnull
  public EPeppolBIS getBIS ()
  {
    return m_aExtendedTransactionKey.getBIS ();
  }

  @Nonnull
  public EBII2Transaction getTransaction ()
  {
    return m_aExtendedTransactionKey.getTransaction ();
  }

  @Nonnull
  public EUBL21DocumentType getUBLDocumentType ()
  {
    return m_aExtendedTransactionKey.getUBLDocumentType ();
  }

  public boolean isCountrySpecific ()
  {
    return m_aExtendedTransactionKey.isCountrySpecific ();
  }

  @Nonnull
  public Locale getCountryLocale ()
  {
    return m_aExtendedTransactionKey.getCountryLocale ();
  }

  @Nonnull
  @Nonempty
  public String getCountryCode ()
  {
    return m_aExtendedTransactionKey.getCountryCode ();
  }

  public boolean isSectorSpecific ()
  {
    return m_aExtendedTransactionKey.isSectorSpecific ();
  }

  /**
   * Get all validation artefacts matching the passed transaction key in the
   * correct execution order.
   *
   * @param aExtendedTransactionKey
   *        The extended transaction to search. May not be <code>null</code>.
   * @return A non-<code>null</code> list with all matching artefacts in the
   *         order they were defined. This list may be empty, if no matching
   *         artefact is present.
   */
  @Nonnull
  @ReturnsMutableCopy
  public static List <EExtendedValidationArtefact> getAllMatchingValidationArtefacts (@Nonnull final ExtendedTransactionKey aExtendedTransactionKey)
  {
    ValueEnforcer.notNull (aExtendedTransactionKey, "ExtendedTransactionKey");

    final List <EExtendedValidationArtefact> ret = new ArrayList <EExtendedValidationArtefact> ();
    for (final EExtendedValidationArtefact e : values ())
      if (e.getExtendedTransactionKey ().equals (aExtendedTransactionKey))
        ret.add (e);
    return ret;
  }
}
