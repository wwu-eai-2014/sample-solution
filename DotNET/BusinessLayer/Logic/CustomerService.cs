using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Pharmacy.BusinessLayer.Data;

namespace Pharmacy.BusinessLayer.Logic
{
    public static class CustomerService
    {
        public static Customer CreateCustomer(string name, string telephoneNumber, string address)
        {
            Util.ConvertEmptyToNull(ref address);

            Customer customer = new Customer
            {
                Name = name,
                TelephoneNumber = telephoneNumber,
                Address = address
            };
            return CreateCustomer(customer);
        }

        private static Customer CreateCustomer(Customer newCustomer)
        {
            using (PharmacyContainer db = new PharmacyContainer())
            {
                var count = (from c in db.CustomerSet where c.Name == newCustomer.Name select c).Count();
                if (count > 0)
                {
                    throw new ArgumentException(String.Format("Customer with name {0} already exists!", newCustomer.Name));
                }
                db.CustomerSet.Add(newCustomer);
                db.SaveChanges();
                return newCustomer;
            }
        }
    }
}
