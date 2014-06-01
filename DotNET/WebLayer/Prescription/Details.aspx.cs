using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Pharmacy.BusinessLayer.Data;
using Pharmacy.BusinessLayer.Logic;

namespace WebLayer.Prescription
{
    public partial class Details : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        private void Page_Error(object sender, EventArgs e)
        {
            Session["LastError"] = Server.GetLastError();
            Server.ClearError();
            Server.Transfer("~/Error.aspx");
        }

        protected void NextState_Command(object sender, CommandEventArgs e)
        {
            PrescriptionService.ProceedToNextStage(GetPrescriptionId());
            RedirectToSelf();
        }

        private void RedirectToSelf()
        {
            Response.Redirect("Details.aspx?id=" + GetPrescriptionId());
        }

        private Int32 GetPrescriptionId()
        {
            return Int32.Parse(Request.Params["id"]);
        }

        protected void PreviousState_Command(object sender, CommandEventArgs e)
        {
            PrescriptionService.ReturnToPreviousState(GetPrescriptionId());
            RedirectToSelf();
        }

        protected void Cancel_Command(object sender, CommandEventArgs e)
        {
            PrescriptionService.Cancel(GetPrescriptionId());
            Response.Redirect("List.aspx");
        }

        protected void UpdatePrescription_Command(object sender, CommandEventArgs e)
        {
            if (!Page.IsValid)
                return;

            string issuer = ((TextBox)PrescriptionDetailsView.FindControl("IssuerBox")).Text;
            string issueDate = ((TextBox)PrescriptionDetailsView.FindControl("IssuedOnBox")).Text;
            string entryDate = ((TextBox)PrescriptionDetailsView.FindControl("EnteredOnBox")).Text;
            PrescriptionService.UpdatePrescription(GetPrescriptionId(), issuer, Util.ParseDate(issueDate), Util.ParseDateTime(entryDate));
            PrescriptionDetailsView.DataBind();
        }
    }
}