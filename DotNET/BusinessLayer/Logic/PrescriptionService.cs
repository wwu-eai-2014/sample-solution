using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Pharmacy.BusinessLayer.Data;

namespace Pharmacy.BusinessLayer.Logic
{
    public static class PrescriptionService
    {
        public static Prescription GetPrescription(Int32 id)
        {
            using (PharmacyContainer db = new PharmacyContainer())
            {
                return GetPrescription(id, db);
            }
        }

        internal static Prescription GetPrescription(Int32 id, PharmacyContainer db)
        {
            Prescription result = (from p in db.PrescriptionSet.Include("Customer").Include("Items") where p.Id == id select p).FirstOrDefault();
            
            if (result == default(Prescription))
                throw new ArgumentException(String.Format("Prescription with id {0} not found", id));

            return result;
        }

        public static void Cancel(Int32 id)
        {
            using (PharmacyContainer db = new PharmacyContainer())
            {
                Prescription p = GetPrescription(id, db);
                foreach (Item i in p.Items) {
                    db.ItemSet.Remove(i);
                }
                db.PrescriptionSet.Remove(p);
                db.SaveChanges();
            }
        }
    }
}
