using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Pharmacy.BusinessLayer.Data;
using Pharmacy.BusinessLayer.Logic;

namespace WebLayer.ReplenishmentOrder
{
    public partial class Details : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void NextStateButton_Command(object sender, CommandEventArgs e)
        {
            if (!Page.IsValid)
                return;
            // TODO proceed to next state
        }

        protected void Cancel_Command(object sender, CommandEventArgs e)
        {
            // TODO cancel order
            Server.Transfer("~/ReplenishmentOrder/List.aspx");
        }
    }
}