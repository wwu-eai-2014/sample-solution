using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Pharmacy.BusinessLayer.Data;

namespace Pharmacy.BusinessLayer.Logic
{
    public static class OrderService
    {
        public static ICollection<ReplenishmentOrder> GetAllOrders()
        {
            using (PharmacyContainer db = new PharmacyContainer())
                return db.ReplenishmentOrderSet.ToList();
        }
    }
}
