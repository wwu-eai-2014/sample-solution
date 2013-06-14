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
                return GetOrder(id, db);
            }
        }

        internal static ReplenishmentOrder GetOrder(Int32 id, PharmacyContainer db)
        {
            ReplenishmentOrder result = (from o in db.ReplenishmentOrderSet where o.Id == id select o).FirstOrDefault();

            if (result == default(ReplenishmentOrder))
                throw new ArgumentException(String.Format("Order with ID {0} not found", id.ToString()));

            return result;
        }

        public static ICollection<Position> GetPositionsForOrder(Int32 id)
        {
            using (PharmacyContainer db = new PharmacyContainer())
            {
                return (from p in db.PositionSet.Include("Drug")
                            where p.Order.Id == id
                            select p).ToList();
            }
        }

        public static ICollection<Position> GetPendingPositionsForDrug(Int32 pzn)
        {
            using (PharmacyContainer db = new PharmacyContainer())
            {
                ICollection<Position> pendingPositions = (
                    from p in db.PositionSet.Include("Order")
                    where p.Order.State == OrderState.Open || 
                        p.Order.State == OrderState.Posting ||
                        p.Order.State == OrderState.Ordered
                    select p).ToList();

                return pendingPositions;
            }
        }

        public static void UpdateExpectedDeliveryDate(Int32 id, DateTime expectedDelivery)
        {
            using (PharmacyContainer db = new PharmacyContainer())
            {
                ReplenishmentOrder order = GetOrder(id, db);
                order.ExpectedDelivery = expectedDelivery;
                db.SaveChanges();
            }
        }

        public static void UpdateActualDeliveryDate(Int32 id, DateTime actualDelivery)
        {
            using (PharmacyContainer db = new PharmacyContainer())
            {
                ReplenishmentOrder order = GetOrder(id, db);
                order.ActualDelivery = actualDelivery;
                db.SaveChanges();
            }
        }

        public static void ProceedToNextState(Int32 id)
        {
            using (PharmacyContainer db = new PharmacyContainer())
            {
                ReplenishmentOrder order = GetOrder(id, db);
                order.State = order.State.Next();
                if (order.State == OrderState.Finished)
                {
                    foreach (Position p in order.Positions)
                    {
                        DrugService.Replenish(p.Drug.PZN, p.Quantity, order.ActualDelivery, db);
                    }
                }
                db.SaveChanges();
            }
        }

        public static void Cancel(Int32 id)
        {
            using (PharmacyContainer db = new PharmacyContainer())
            {
                ReplenishmentOrder order = GetOrder(id, db);
                order.State = order.State.Cancel();
                // migrate all positions to new or existing open order
                db.SaveChanges();
            }
        }
    }
}
