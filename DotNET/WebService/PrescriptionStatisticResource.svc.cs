using System;
using System.Collections.Generic;
using System.Data.Objects.SqlClient;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using Pharmacy.BusinessLayer.Data;
using Pharmacy.BusinessLayer.Logic;

namespace WebService
{
    [ServiceContract]
    public class PrescriptionStatisticResource
    {
        [WebGet(UriTemplate = "?start={start}&end={end}", ResponseFormat = WebMessageFormat.Json)]
        public PrescriptionStatisticDto getStatistic(string start, string end)
        {
            // use earliest time for start and latest for end
            DateTime startDate = DateTime.Parse(String.Format("{0} 00:00:00", start));
            DateTime endDate = DateTime.Parse(String.Format("{0} 23:59:59", end));

            var prescriptions = PrescriptionService.GetAllPrescriptionsEnteredBetween(startDate, endDate);
            var totalNumberOfPrescriptions = prescriptions.Count;
            var averageNumberOfItemsPerPrescription = (from p in prescriptions
                                                          where p.Items.Count > 0
                                                          select p.Items.Count).Average();
            var averageDuration = (int) (from p in prescriptions
                                   where p.State == PrescriptionState.Fulfilled
                                   let timespan = p.FulfilmentDate - p.EntryDate
                                   select timespan.Value.TotalSeconds).Average();
            return new PrescriptionStatisticDto
            {
                totalNumberOfPrescriptions = totalNumberOfPrescriptions,
                averageNumberOfItemsPerPrescription = averageNumberOfItemsPerPrescription,
                averageDuration = averageDuration
            };
        }
    }

    [DataContract]
    public class PrescriptionStatisticDto
    {

        [DataMember]
        public int totalNumberOfPrescriptions;
        [DataMember]
        public double averageNumberOfItemsPerPrescription;
        [DataMember]
        public int averageDuration;

    }
}
