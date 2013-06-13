using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace WebLayer.Drug
{
    public partial class Details : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void WithdrawButton_Command(object sender, CommandEventArgs e)
        {
            if (!Page.IsValid)
            {
                return;
            }

        }

        protected void RestockButton_Command(object sender, CommandEventArgs e)
        {
            if (!Page.IsValid)
            {
                return;
            }

        }

        protected void ReplenishButton_Command(object sender, CommandEventArgs e)
        {
            if (!Page.IsValid)
            {
                return;
            }

        }
    }
}