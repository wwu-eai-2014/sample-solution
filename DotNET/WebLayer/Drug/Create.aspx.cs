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
            try
            {

            }
            catch (ArgumentException ex)
            {
                ResultLabel.Text = String.Format("Drug not created: {0}", ex.Message);
                ResultLabel.CssClass = "error";
            }
        }

    }
}