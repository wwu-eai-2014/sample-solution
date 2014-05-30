<%@ Page Title="" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true"
    CodeBehind="Details.aspx.cs" Inherits="WebLayer.Prescription.Details" %>
<%@ Import Namespace="Pharmacy.BusinessLayer.Data" %>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentHolder" runat="server">
    <form id="PrescriptionDetailsForm" runat="server">
        <asp:ObjectDataSource ID="PrescriptionDataSource" runat="server"
            SelectMethod="GetPrescription"
            TypeName="Pharmacy.BusinessLayer.Logic.PrescriptionService"
            DataObjectTypeName="Pharmacy.BusinessLayer.Data.Prescription">
            <SelectParameters>
                <asp:QueryStringParameter Name="Id" QueryStringField="id" Type="Int32" />
            </SelectParameters>
        </asp:ObjectDataSource>
        <asp:DetailsView ID="PrescriptionDetailsView" runat="server" AutoGenerateRows="false"
            BackColor="White" BorderColor="#CCCCCC" BorderStyle="None" BorderWidth="1px" CellPadding="4"
            ForeColor="Black" GridLines="Horizontal" Height="50px" 
            DataSourceID="PrescriptionDataSource">
            <Fields>
                <asp:BoundField DataField="Id" HeaderText="Prescription" ReadOnly="true" />
                <asp:TemplateField HeaderText="Customer">
                    <ItemTemplate>
                        <asp:HyperLink runat="server" ID="CustomerDetailsLink"
                            NavigateUrl='<%# String.Format("~/Customer/Details.aspx?id={0}", Eval("Customer.Id")) %>'
                            Text='<%# Eval("Customer.Name") %>' />
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:BoundField DataField="State" HeaderText="State" />
                <asp:BoundField DataField="IssuingPhysician" HeaderText="Issuer" />
                <asp:BoundField DataField="IssueDate" HeaderText="Issued on" />
                <asp:TemplateField>
                    <ItemTemplate>
                        <asp:Button ID="NextStateButton" runat="server" OnCommand="NextStateButton_Command"
                            Text='<%# "Proceed to " + ((PrescriptionState)Eval("State")).Next() %>'
                            Enabled='<%# ((PrescriptionState)Eval("State")).Proceedable((ICollection<Item>)Eval("Items")) %>'
                            Visible='<%# ((PrescriptionState)Eval("State")).Proceedable((ICollection<Item>)Eval("Items")) %>'
                        />
                        <asp:Button ID="CancelButton" runat="server" OnCommand="Cancel_Command" Text="Cancel"
                            Enabled='<%# ((PrescriptionState)Eval("State")).Cancellable() %>'
                            Visible='<%# ((PrescriptionState)Eval("State")).Cancellable() %>' />
                    </ItemTemplate>
                </asp:TemplateField>
            </Fields>
        </asp:DetailsView>
    </form>
</asp:Content>
