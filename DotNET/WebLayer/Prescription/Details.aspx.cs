﻿using System;
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

        protected void NextStateButton_Command(object sender, CommandEventArgs e)
        {
            if (!Page.IsValid)
                return;

            PrescriptionDetailsView.DataBind();
        }

        private Int32 GetPrescriptionId()
        {
            return Int32.Parse(Request.Params["id"]);
        }


        protected void Cancel_Command(object sender, CommandEventArgs e)
        {
            PrescriptionService.Cancel(GetPrescriptionId());
            // redirect to prescription list
            Server.Transfer("List.aspx");
        }
    }
}