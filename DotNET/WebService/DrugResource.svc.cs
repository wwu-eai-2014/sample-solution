using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Activation;
using System.ServiceModel.Web;
using System.Text;
using Pharmacy.BusinessLayer.Logic;
using Pharmacy.BusinessLayer.Data;

namespace WebService
{
    [ServiceContract]
    public class DrugResource
    {
        [WebGet(UriTemplate="", ResponseFormat=WebMessageFormat.Json)]
        public ICollection<DrugDto> getAll() {
            return DrugService.GetAllDrugs()
                .Select(d => new DrugDto(d))
                .ToList();
        }

        [WebGet(UriTemplate = "{pznAsString}", ResponseFormat = WebMessageFormat.Json)]
        public DrugDto get(String pznAsString)
        {
            Int32 pzn = Int32.Parse(pznAsString);
            ValidateDrugExists(pzn);
            var d = DrugService.GetDrug(pzn);
            return new DrugDto(d);
        }

        public static void ValidateDrugExists(Int32 pzn)
        {
            try
            {
                DrugService.GetDrug(pzn);
            }
            catch
            {
                throw new WebFaultException(System.Net.HttpStatusCode.NotFound);
            }
        }

        [WebInvoke(UriTemplate="", Method="POST", RequestFormat=WebMessageFormat.Json, ResponseFormat=WebMessageFormat.Json)]
        public DrugDto create(DrugDto newDrug)
        {
            try
            {
                DrugService.CreateDrug(newDrug.pzn, newDrug.name, newDrug.description);
                return newDrug;
            }
            catch
            {
                throw new WebFaultException(System.Net.HttpStatusCode.Conflict);
            }
        }

        [WebInvoke(UriTemplate = "{pznAsString}", Method="PUT",
            RequestFormat=WebMessageFormat.Json, ResponseFormat=WebMessageFormat.Json)]
        public DrugDto update(String pznAsString, DrugDto drugDto)
        {
            Int32 pzn = Int32.Parse(pznAsString);
            ValidateDrugExists(pzn);
            ValidatePznCorrespondence(pzn, drugDto);

            var d = DrugService.GetDrug(pzn);
            d.Name = drugDto.name;
            d.Description = drugDto.description;
            DrugService.UpdateDrug(d);
            return drugDto;
        }

        private void ValidatePznCorrespondence(int pzn, DrugDto drug)
        {
            if (pzn != drug.pzn)
            {
                throw new WebFaultException(System.Net.HttpStatusCode.BadRequest);
            }
        }

    }

    [DataContract]
    public class DrugDto
    {
        public DrugDto(Drug drug)
        {
            pzn = drug.PZN;
            name = drug.Name;
            description = drug.Description;
        }

        [DataMember]
        public Int32 pzn;
        [DataMember]
        public String name;
        [DataMember]
        public String description;
    }
}
