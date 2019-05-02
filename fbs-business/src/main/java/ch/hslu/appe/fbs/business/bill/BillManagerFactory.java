package ch.hslu.appe.fbs.business.bill;

import ch.hslu.appe.fbs.business.authorisation.AuthorisationVerifier;
import ch.hslu.appe.fbs.business.authorisation.AuthorisationVerifierFactory;
import ch.hslu.appe.fbs.data.bill.BillPersistorFactory;
import ch.hslu.appe.fbs.data.reminder.ReminderPersistorFactory;

public final class BillManagerFactory {

    private BillManagerFactory() {
    }

    public static BillManager createBillManager() {
        return createBillManager(AuthorisationVerifierFactory.createAuthorisationVerifier());
    }

    public static BillManager createBillManager(final AuthorisationVerifier authorisationVerifier) {
        return new BillManagerImpl(authorisationVerifier,
                BillPersistorFactory.createBillPersistor(), ReminderPersistorFactory.createReminderPersistor());
    }
}
