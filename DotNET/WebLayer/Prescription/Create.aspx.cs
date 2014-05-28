using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace WebLayer.Prescription
{
    public partial class Create : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void SubmitBtn_Click(object sender, EventArgs e)
        {
            if (!Page.IsValid)
                return;

            return;
            // Pharmacy.BusinessLayer.Logic.CustomerService.CreatePrescription(GetCustomerId(), IssuerBox.Text);   
        }

        private Int32 GetCustomerId()
        {
            return Int32.Parse(Request.Params["id"]);
        }
    }
}