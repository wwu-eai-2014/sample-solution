using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Pharmacy.BusinessLayer.Data
{
    public partial class Drug
    {
        public string dummyMethod()
        {
            return "Hello world";
        }

        internal void Apply(InventoryEvent inventoryEvent)
        {
            Int32 inventoryDelta = inventoryEvent.GetInventoryDelta();
            ValidateEvent(inventoryDelta);
            Stock += inventoryDelta;
            Events.Add(inventoryEvent);
        }

        private void ValidateEvent(Int32 inventoryDelta)
        {
            if (Stock + inventoryDelta < 0)
            {
                throw new ArgumentException("Cannot withdraw below stock of " + Stock);
            }
        }
    }

    public abstract partial class InventoryEvent
    {
        public virtual Int32 GetInventoryDelta()
        {
            return Quantity;
        }
    }

    public partial class WithdrawEvent : InventoryEvent
    {

        public override Int32 GetInventoryDelta()
        {
            return -Quantity;
        }
    }

}
