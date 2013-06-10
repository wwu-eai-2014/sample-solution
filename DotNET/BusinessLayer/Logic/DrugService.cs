using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Pharmacy.BusinessLayer.Data;

namespace Pharmacy.BusinessLayer.Logic
{
    public static class DrugService
    {
        public static ICollection<Drug> GetAllDrugs()
        {
            using (PharmacyContainer db = new PharmacyContainer())
                return db.DrugSet.ToList();
        }

        public static Drug GetDrug(Int32 pzn)
        {
            using (PharmacyContainer db = new PharmacyContainer())
            {
                Drug result = (from d in db.DrugSet where d.PZN == pzn select d).FirstOrDefault();

                if (result == default(Drug))
                    throw new ArgumentException("Drug with PZN {0} not found", pzn.ToString());

                return result;
            }
        }

        public static Drug UpdateDrug(Drug drug)
        {
            return UpdateDrug(drug, drug.Name, drug.Description, drug.MinimumInventoryLevel, drug.OptimalInventoryLevel);
        }

        public static Drug UpdateDrug(Drug drug, String Name, String Description, int MinimumInventoryLevel, int OptimalInventoryLevel)
        {
            using (PharmacyContainer db = new PharmacyContainer())
            {
                Drug attachedDrug = GetDrug(drug.PZN);
                // adjust state to enforce update
                db.Entry(attachedDrug).State = System.Data.EntityState.Modified;
                attachedDrug.Name = Name;
                attachedDrug.Description = Description;
                attachedDrug.MinimumInventoryLevel = MinimumInventoryLevel;
                attachedDrug.OptimalInventoryLevel = OptimalInventoryLevel;

                db.SaveChanges();
                return attachedDrug;
            }
        }
    }
}
