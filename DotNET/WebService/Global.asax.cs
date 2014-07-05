using System;
using System.Web.Routing;
using System.ServiceModel.Activation;


namespace WebService
{
    public class Global : System.Web.HttpApplication
    {

        protected void Application_Start(object sender, EventArgs e)
        {
            RegisterRoutes();
        }

        /// <summary>
        /// Register the URIs as Routes at the .NET-Application
        /// </summary>
        private void RegisterRoutes()
        {
            RouteTable.Routes.Add(new ServiceRoute("drug", new WebServiceHostFactory(), typeof(DrugResource)));
            RouteTable.Routes.Add(new ServiceRoute("statistic/drug", new WebServiceHostFactory(), typeof(DrugStatisticResource)));
            RouteTable.Routes.Add(new ServiceRoute("statistic/prescription", new WebServiceHostFactory(), typeof(PrescriptionStatisticResource)));
        }

    }
}