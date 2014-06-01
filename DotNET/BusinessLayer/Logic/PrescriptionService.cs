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
                p.Items.ToList().ForEach(i => db.ItemSet.Remove(i));
                db.Entry(p).State = System.Data.EntityState.Deleted;
                db.SaveChanges();
            }
        }

        public static void UpdatePrescription(Int32 id, string issuer, DateTime issueDate, DateTime entryDate)
        {
            using (PharmacyContainer db = new PharmacyContainer())
            {
                Prescription p = GetPrescription(id, db);
                p.IssuingPhysician = issuer;
                p.IssueDate = issueDate;
                p.EntryDate = entryDate;
                db.SaveChanges();
            }
        }

        public static void ProceedToNextStage(Int32 id)
        {
            using (PharmacyContainer db = new PharmacyContainer())
            {
                Prescription p = GetPrescription(id, db);
                p.State = p.State.Next();
                db.SaveChanges();
            }
        }

        public static void ReturnToPreviousState(Int32 id)
        {
            using (PharmacyContainer db = new PharmacyContainer())
            {
                Prescription p = GetPrescription(id, db);
                p.State = p.State.Previous();
                db.SaveChanges();
            }
        }
    }
}
