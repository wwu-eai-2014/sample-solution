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

        public static ReplenishmentOrder GetOrder(Int32 id)
        {
            using (PharmacyContainer db = new PharmacyContainer())
            {
                ReplenishmentOrder result = (from o in db.ReplenishmentOrderSet where o.Id == id select o).FirstOrDefault();

                if (result == default(ReplenishmentOrder))
                    throw new ArgumentException("Order with ID {0} not found", id.ToString());

                return result;
            }
        }
    }
}
