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

        public static Drug UpdateDrugData(Drug drug)
        {
            string newName = drug.Name;
            string newDescription = drug.Description;
            int newMinimumInventoryLevel = drug.MinimumInventoryLevel;
            int newOptimalInventoryLevel = drug.OptimalInventoryLevel;
            using (PharmacyContainer db = new PharmacyContainer())
            {
                Drug attachedDrug = GetDrug(drug.PZN);
                attachedDrug.Name = newName;
                attachedDrug.Description = newDescription;
                attachedDrug.MinimumInventoryLevel = newMinimumInventoryLevel;
                attachedDrug.OptimalInventoryLevel = newOptimalInventoryLevel;
                return attachedDrug;
            }
        }
    }
}
