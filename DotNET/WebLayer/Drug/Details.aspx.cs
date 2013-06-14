using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Pharmacy.BusinessLayer.Data;
using Pharmacy.BusinessLayer.Logic;

namespace WebLayer.Drug
{
    public partial class Details : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (DateOfActionBox.Text == "")
            {
                DateOfActionBox.Text = String.Format("{0:dd.MM.yyyy HH:mm}", DateTime.Now);
            }
        }

        protected void WithdrawButton_Command(object sender, CommandEventArgs e)
        {
            if (!Page.IsValid)
            {
                return;
            }
            DrugService.Withdraw(GetPzn(), GetQuantity(), GetDateOfAction());
            UpdateData();
        }

        private Int32 GetPzn()
        {
            return Int32.Parse(Request.Params["pzn"]);
        }

        private Int32 GetQuantity()
        {
            return Int32.Parse(QuantityBox.Text);
        }

        private DateTime GetDateOfAction()
        {
            return parseDateTime(DateOfActionBox.Text);
        }

        private static DateTime parseDateTime(string dateAsString)
        {
            return DateTime.ParseExact(dateAsString, "dd.MM.yyyy HH:mm", CultureInfo.InvariantCulture);
        }

        private void UpdateData()
        {
            // update data
            DrugDetailsView.DataBind();
            PendingOrdersGridView.DataBind();
        }

        protected void RestockButton_Command(object sender, CommandEventArgs e)
        {
            if (!Page.IsValid)
            {
                return;
            }
            DrugService.Restock(GetPzn(), GetQuantity(), GetDateOfAction());
            UpdateData();
        }

        protected void ReplenishButton_Command(object sender, CommandEventArgs e)
        {
            if (!Page.IsValid)
            {
                return;
            }
            DrugService.InitiateReplenishment(GetPzn(), GetQuantity());
            UpdateData();
        }
    }
}