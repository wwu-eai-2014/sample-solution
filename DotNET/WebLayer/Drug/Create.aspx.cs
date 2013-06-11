using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace WebLayer.Drug
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
            try
            {
                // pzn is validated in form
                int pzn = Int32.Parse(PZNBox.Text);

                Pharmacy.BusinessLayer.Data.Drug result =
                    Pharmacy.BusinessLayer.Logic.DrugService.CreateDrug(pzn, NameBox.Text, DescriptionBox.Text);
                ResultLabel.Text = String.Format("Drug '{0}' created.", result.PZN);
                ResultLabel.CssClass = "success";
                PZNBox.Text = "";
                NameBox.Text = "";
                DescriptionBox.Text = "";
            }
            catch (ArgumentException ex)
            {
                ResultLabel.Text = String.Format("Drug not created: {0}", ex.Message);
                ResultLabel.CssClass = "error";
            }
        }

    }
}