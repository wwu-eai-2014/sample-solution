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
    }
}
