﻿using System;
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

        public static ICollection<Item> GetItemsForPrescription(Int32 id)
        {
            using (PharmacyContainer db = new PharmacyContainer())
            {
                return (from i in db.ItemSet.Include("PrescribedDrug").Include("Prescription")
                        where i.Prescription.Id == id
                        select i).ToList();
            }
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

        public static void AddDrug(Int32 id, Int32 pzn)
        {
            using (PharmacyContainer db = new PharmacyContainer())
            {
                Prescription p = GetPrescription(id, db);
                Drug d = DrugService.GetDrug(pzn, db);
                
                if (p.Items.Any(i => i.PrescribedDrug.PZN == pzn))
                    throw new ArgumentException(String.Format("Drug with pzn {0} already contained in prescription with id {1}", pzn, id));

                p.Items.Add(new Item { Prescription = p, PrescribedDrug = d });
                db.SaveChanges();
            }
        }

        public static void RemoveItem(int itemId)
        {
            using (PharmacyContainer db = new PharmacyContainer())
            {
                Item item = (from i in db.ItemSet where i.Id == itemId select i).SingleOrDefault();

                if (item == default(Item))
                    throw new ArgumentException(String.Format("Item with id {0} not found", itemId));

                db.ItemSet.Remove(item);
                db.SaveChanges();
            }
        }
    }
}
