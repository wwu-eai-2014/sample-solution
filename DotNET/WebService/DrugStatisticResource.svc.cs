using System;
using System.Collections.Generic;
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
    public class DrugStatisticResource
    {
        [WebGet(UriTemplate = "", ResponseFormat = WebMessageFormat.Json)]
        public ICollection<DrugStatisticDto> getAll()
        {
            return DrugService.GetAllDrugs()
                .Select(d => new DrugStatisticDto(d,
                    PrescriptionService.GetQuantityPendingForDrug(d.PZN),
                    PrescriptionService.GetQuantityUnfulfilledForDrug(d.PZN)))
                .ToList();;
        }

        [WebGet(UriTemplate = "{pznAsString}", ResponseFormat = WebMessageFormat.Json)]
        public DrugStatisticDto get(String pznAsString)
        {
            Int32 pzn = Int32.Parse(pznAsString);
            DrugResource.ValidateDrugExists(pzn);

            var d = DrugService.GetDrug(pzn);
            Int32 pendingPositions = PrescriptionService.GetQuantityPendingForDrug(pzn);
            Int32 unfilledItems = PrescriptionService.GetQuantityUnfulfilledForDrug(pzn);
            return new DrugStatisticDto(d, pendingPositions, unfilledItems);
        }
    }

    [DataContract]
    public class DrugStatisticDto
    {
        public DrugStatisticDto(Drug drug, Int32 pendingPositions, Int32 unfulfilledItems)
        {
            pzn = drug.PZN;
            stock = drug.Stock;
            minimumInventoryLevel = drug.MinimumInventoryLevel;
            optimalInventoryLevel = drug.OptimalInventoryLevel;
            this.pendingPositions = pendingPositions;
            this.unfulfilledItems = unfulfilledItems;
        }

        [DataMember]
        public Int32 pzn;
        [DataMember]
        public Int32 stock;
        [DataMember]
        public Int32 minimumInventoryLevel;
        [DataMember]
        public Int32 optimalInventoryLevel;
        [DataMember]
        public Int32 pendingPositions;
        [DataMember]
        public Int32 unfulfilledItems;
    }
}
