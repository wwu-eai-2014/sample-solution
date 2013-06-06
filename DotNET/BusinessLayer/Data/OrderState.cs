using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Pharmacy.BusinessLayer.Data
{
    public enum OrderState
    {
        Open, Posting, Ordered, Finished, Cancelled
    }
}
